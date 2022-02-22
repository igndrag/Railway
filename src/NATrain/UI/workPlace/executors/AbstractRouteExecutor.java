package NATrain.UI.workPlace.executors;

import NATrain.UI.workPlace.WorkPlaceController;
import NATrain.model.Model;
import NATrain.routes.Route;
import NATrain.routes.RouteDirection;
import NATrain.routes.RouteType;
import NATrain.routes.StationTrack;
import NATrain.trackSideObjects.*;
import NATrain.trackSideObjects.movableObjects.Locomotive;
import NATrain.trackSideObjects.signals.Signal;
import NATrain.trackSideObjects.switches.Switch;
import NATrain.trackSideObjects.switches.SwitchState;
import NATrain.trackSideObjects.trackSections.TrackSection;
import NATrain.trackSideObjects.trackSections.TrackSectionState;
import NATrain.utils.Sound;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.atomic.AtomicInteger;

import static NATrain.trackSideObjects.trackSections.TrackSectionState.OCCUPIED;
import static java.util.concurrent.TimeUnit.*;

public abstract class AbstractRouteExecutor implements RouteExecutor {

    private final int STRONG_LOCK_CANCELLATION_PAUSE = 1; //180
    private final int SOFT_LOCK_CANCELLATION_PAUSE = 1; //30
    RouteStatus routeStatus = RouteStatus.CREATED;
    Route route;
    private static WorkPlaceController workPlaceController;
    private Boolean strongLock = false;
    private final TrackSection departureSection;
    private final TrackSection destinationSection; //In departure route this is TVDS1
    private final Map<TrackSection, PropertyChangeListener> trackSectionUnlockerMap = new HashMap<>();
    private final Map<TracksideObject, PropertyChangeListener> signalStateUpdaterMap = new HashMap<>();
    private final ConcurrentLinkedDeque<TrackSection> occupationalOrder;
    private static final int delayForChangeSwitchPositions = 2;
    private static final int tryToInterlockCount = 3;

    private DepartureTrackSectionListener departureTrackSectionListener;

    public static void setWorkPlaceController(WorkPlaceController workPlaceController) {
        AbstractRouteExecutor.workPlaceController = workPlaceController;
    }

    public AbstractRouteExecutor(Route route) {
        this.route = route;
        occupationalOrder = new ConcurrentLinkedDeque<>(route.getOccupationalOrder()); // create copy for processing
        switch (route.getRouteType()) {
            case ARRIVAL:
               // occupationalOrder.add(route.getDestinationTrackSection());
                break;
            case SHUNTING:
                if (occupationalOrder.getFirst() == route.getDepartureTrackSection()) { // if shunting route from usual track section
                    occupationalOrder.pollFirst();                                      // we don't need to interlock this section in occupational order
                }

                if (occupationalOrder.getLast() == route.getDestinationTrackSection()) { // if shunting route to occupied usual track section
                    occupationalOrder.pollLast();                                        // we don't need to interlock this section in occupational order
                }
                break;
        }

        this.departureSection = route.getDepartureTrackSection();
        this.destinationSection = route.getDestinationTrackSection();
    }

    public RouteStatus getRouteStatus() {
        return routeStatus;
    }

    @Override
    public Route getRoute() {
        return route;
    }

    private class SignalStateUpdater implements PropertyChangeListener {

        @Override
        public void propertyChange(PropertyChangeEvent evt) {
            autoselectSignalState();
        }
    }

    private class TrackSectionUnlocker implements PropertyChangeListener {

        TrackSection previousSection;
        TrackSection thisSection;
        TrackSection nextSection;

        public TrackSectionUnlocker(TrackSection previousSection, TrackSection thisSection, TrackSection nextSection) {
            this.previousSection = previousSection;
            this.thisSection = thisSection;
            this.nextSection = nextSection;
        }

        @Override
        public void propertyChange(PropertyChangeEvent event) {
            if (event.getPropertyName().equals("occupationalProperty")) {
                strongLock = true; // if something changed in tracks states apply strong lock
                if (routeStatus == RouteStatus.READY && thisSection != departureSection) {
                    routeStatus = RouteStatus.IN_PROCESS;
                    workPlaceController.refreshRouteStatusTable();
                    removeSignalUpdaters();
                }

                if (thisSection.isInterlocked()) {
                    if (event.getOldValue() == TrackSectionState.FREE && event.getNewValue() == OCCUPIED) {
                        thisSection.fixOccupation();
                    }
                    if (thisSection.isOccupationFixed() && event.getOldValue() == OCCUPIED && event.getNewValue() == TrackSectionState.FREE) {
                        thisSection.fixDeallocation();
                    }
                }
                    if (    thisSection != destinationSection &&
                            previousSection.notInterlocked()
                            && thisSection.isOccupationFixed()
                            && (thisSection.isDeallocationFixed())
                            && (nextSection.isOccupationFixed() || nextSection == destinationSection)) {
                        //   || (route.getRouteType() == RouteType.ARRIVAL && nextSection == destinationSection && destinationSection.getVacancyState() == OCCUPIED)) ||
                        //   || (route.getRouteType() != RouteType.DEPARTURE && thisSection == destinationSection && !previousSection.isInterlocked()) ) {
                        thisSection.setInterlocked(false);
                        if (route.getRouteType() != RouteType.DEPARTURE && nextSection == destinationSection && nextSection.getVacancyState() == OCCUPIED) {
                            nextSection.setInterlocked(false);
                        }
                        thisSection.removePropertyChangeListener(this);
                        trackSectionUnlockerMap.remove(thisSection);
                        if (trackSectionUnlockerMap.isEmpty()) {
                            routeStatus = RouteStatus.COMPLETED;
                            if (route.getRouteType() == RouteType.ARRIVAL) {
                                route.getRouteCompletedSound().playAfterAttentionSound();
                            }
                            removeSignalUpdaters();
                            workPlaceController.refreshRouteStatusTable();
                        }
                    }
            }
            autoselectSignalState();
        }

    }

    private boolean checkTrackSections() {
        if (departureSection.getVacancyState() == OCCUPIED)
            strongLock = true;
        for (TrackSection trackSection : occupationalOrder) {
            if (trackSection.getVacancyState() == OCCUPIED || trackSection.isInterlocked()) {
                workPlaceController.log("   Trackline Sections check failed. " + trackSection.getId() + " is occupied or interlocked in route.");
                return false;
            }
        }
        if (route.getRouteType() != RouteType.SHUNTING && destinationSection.getVacancyState() == OCCUPIED) {
            workPlaceController.log("   Trackline Sections check failed. Last section isn't free in not shunting route.");
            return false;
        }
        workPlaceController.log("   Trackline Sections checked.");
        return true;
    }

    private boolean checkSwitchesAbilityToSet() {
        for (Map.Entry<Switch, SwitchState> entry : route.getSwitchStatePositions().entrySet()) {
            Switch aSwitch = entry.getKey();
            SwitchState switchState = entry.getValue();

            if (aSwitch.getSwitchState() == switchState || aSwitch.isChangePositionAvailable()) {
                if (aSwitch.isPared()) {
                    //all right check pared switch
                    Switch bSwitch = aSwitch.getParedSwitch();
                    if (bSwitch.getSwitchState() == switchState || bSwitch.isChangePositionAvailable()) {
                        continue;// all right, do nothing
                    } else {
                        workPlaceController.log("    Switch ability to set check failed. Unavailable to set " + bSwitch.getId() + " pared switch in right position.");
                        return false;
                    }
                }
            } else {
                workPlaceController.log("   Switch ability to set check failed. Unavailable to set " + aSwitch.getId() + " switch in right position.");
                return false;
            }
        }
        workPlaceController.log("   Switch ability to set in route checked.");
        return true;
    }

    private void setSwitchPositions() {
        for (Map.Entry<Switch, SwitchState> entry : route.getSwitchStatePositions().entrySet()) {
            Switch aSwitch = entry.getKey();
            SwitchState switchState = entry.getValue();
            if (aSwitch.getSwitchState() != switchState && aSwitch.isChangePositionAvailable()) {
                if (switchState == SwitchState.PLUS) {
                    aSwitch.sendOutputCommand(SwitchState.PLUS);
                    aSwitch.setSwitchState(SwitchState.PLUS); //TODO delete it when realize hardware
                } else {
                    aSwitch.sendOutputCommand(SwitchState.MINUS);
                    aSwitch.setSwitchState(SwitchState.MINUS); //TODO delete it when realize hardware
                }
            }
        }
    }

    private void interlock() {
        occupationalOrder.forEach(trackSection -> trackSection.setInterlocked(true));
        if (route.getRouteType() != RouteType.DEPARTURE) {
            destinationSection.setInterlocked(true);
        }
        workPlaceController.log(route.getDescription() + " is ready. Sections are interlocked.");
        routeStatus = RouteStatus.READY;
        if (route.getDepartureTrackSection().getVacancyState() == OCCUPIED) {
            route.getRouteReadySound().playAfterAttentionSound();
        } else {
            departureTrackSectionListener = new DepartureTrackSectionListener();
            route.getDepartureTrackSection().addPropertyChangeListener(departureTrackSectionListener);
        }
        workPlaceController.refreshRouteStatusTable();
    }

    public void cancelRoute() {
        int safetyPause = strongLock ? STRONG_LOCK_CANCELLATION_PAUSE : SOFT_LOCK_CANCELLATION_PAUSE;
        routeStatus = RouteStatus.CANCELLATION;
        workPlaceController.refreshRouteStatusTable();
        workPlaceController.log(String.format("Route: %s is cancelling with %ds safety pause.", route.getDescription(), safetyPause));

        ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
        service.scheduleWithFixedDelay(
                () -> {
                    if (departureTrackSectionListener != null) {
                        departureSection.removePropertyChangeListener(departureTrackSectionListener);
                    }
                    departureSection.setInterlocked(false);
                    destinationSection.setInterlocked(false);
                    route.getOccupationalOrder().forEach(trackSection -> trackSection.setInterlocked(false));
                    removeUnusedUnlockers();
                    removeSignalUpdaters();
                    routeStatus = RouteStatus.CANCELLED;
                    route.getSignal().close();
                    workPlaceController.refreshRouteStatusTable();
                    workPlaceController.log(String.format("Route: %s has been canceled.", route.getDescription()));
                    service.shutdown();
                },
                safetyPause, safetyPause, SECONDS);
    }

    private boolean checkSwitchPositions() {
        for (Map.Entry<Switch, SwitchState> entry : route.getSwitchStatePositions().entrySet()) {
            Switch aSwitch = entry.getKey();
            SwitchState switchState = entry.getValue();
            if (aSwitch.getSwitchState() != switchState)
                return false;
        }
        workPlaceController.log("   Switch positions checked.");
        return true;
    }

    private void createListeners() {
        TrackSection firstSection = occupationalOrder.pollFirst();
        assert firstSection != null;
        TrackSection secondSection;
        if (occupationalOrder.isEmpty()) { // for too short route
            secondSection = destinationSection;
        } else {
            secondSection = occupationalOrder.getFirst();
        }
        TrackSectionUnlocker firstTrackSectionUnlocker = new TrackSectionUnlocker(TrackSection.EMPTY_TRACK_SECTION, firstSection, secondSection);
        trackSectionUnlockerMap.put(firstSection, firstTrackSectionUnlocker);
        firstSection.addPropertyChangeListener(firstTrackSectionUnlocker);
        TrackSection cursor = firstSection;
        while (!occupationalOrder.isEmpty()) {
            TrackSection trackSection = occupationalOrder.pollFirst();
            TrackSection nextSection;
            if (occupationalOrder.isEmpty()) {
                nextSection = destinationSection;
                //nextSection = TrackSection.EMPTY_TRACK_SECTION;
            } else {
                nextSection = occupationalOrder.getFirst();
            }
            TrackSectionUnlocker trackSectionUnlocker = new TrackSectionUnlocker(cursor, trackSection, nextSection);
            trackSectionUnlockerMap.put(trackSection, trackSectionUnlocker);
            trackSection.addPropertyChangeListener(trackSectionUnlocker);
            cursor = trackSection;
        }

        if (route.getTVDS1() != null) {
            SignalStateUpdater TVDS1signalStateUpdater = new SignalStateUpdater();
            signalStateUpdaterMap.put(route.getTVDS1(), TVDS1signalStateUpdater);
            route.getTVDS1().addPropertyChangeListener(TVDS1signalStateUpdater);
        }
        if (route.getTVDS2() != null) {
            SignalStateUpdater TVDS2signalStateUpdater = new SignalStateUpdater();
            signalStateUpdaterMap.put(route.getTVDS1(), TVDS2signalStateUpdater);
            route.getTVDS2().addPropertyChangeListener(TVDS2signalStateUpdater);
        }
        if (route.getRouteType() == RouteType.ARRIVAL) {
            SignalStateUpdater fromNextSignalUpdater = new SignalStateUpdater();
            Signal nextSignal;
            StationTrack stationTrack = (StationTrack) route.getDestinationTrackSection();
            if (route.getRouteDirection() == RouteDirection.EVEN) {
                nextSignal = stationTrack.getEvenSignal();
            } else {
                nextSignal = stationTrack.getOddSignal();
            }
            nextSignal.addPropertyChangeListener(fromNextSignalUpdater);
            signalStateUpdaterMap.put(nextSignal, fromNextSignalUpdater);
        }

    }

    private void removeUnusedUnlockers() {
        trackSectionUnlockerMap.forEach(TracksideObject::removePropertyChangeListener);
        trackSectionUnlockerMap.clear();
    }

    private void removeSignalUpdaters() {
        signalStateUpdaterMap.forEach(TracksideObject::removePropertyChangeListener);
        signalStateUpdaterMap.clear();
    }

    @Override
    public void executeRoute() {
        setSwitchPositions();
        ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
        AtomicInteger cyclesCount = new AtomicInteger();
        service.scheduleWithFixedDelay(
                () -> {
                    cyclesCount.getAndIncrement();
                    if (checkTrackSections() && checkSwitchPositions()) {
                        interlock();
                        routeStatus = RouteStatus.READY;
                        autoselectSignalState();
                        createListeners();
                        if (route.getRouteType() != RouteType.SHUNTING) {
                            TrackSection departureSection = route.getDepartureTrackSection();
                            Optional<Locomotive> locomotive = Model.getLocomotives().values().stream().filter(
                                    loco -> (loco.getFrontTag().getTagLocation() == departureSection
                                            && loco.getForwardDirection() == route.getRouteDirection())).findFirst();
                            if (locomotive.isPresent() && locomotive.get().getAutopilot() != null) {
                                locomotive.get().getAutopilot().executeRoute(route);
                            }
                        }
                        service.shutdown();
                    }
                    if (cyclesCount.get() > tryToInterlockCount) {
                        service.shutdown();
                    }
                },
                0, delayForChangeSwitchPositions, SECONDS);
    }

    protected boolean isAllSectionsFree() {
        for (TrackSection trackSection : route.getOccupationalOrder()) {
            if (trackSection.getVacancyState() == OCCUPIED)
                if (route.getRouteType() == RouteType.SHUNTING && trackSection == route.getDepartureTrackSection()) {
                    continue;
                } else {
                    return false;
                }
        }
        return true;
    }

    private class DepartureTrackSectionListener implements PropertyChangeListener {
        @Override
        public void propertyChange(PropertyChangeEvent evt) {
            if (evt.getNewValue() == OCCUPIED) {
               ((TrackSection) evt.getSource()).removePropertyChangeListener(this);
               route.getRouteReadySound().playAfterAttentionSound();
            }
        }
    }

    public String getRouteDescription() {
        return route.getDescription();
    }

}
