package NATrain.UI.workPlace.executors;

import NATrain.UI.workPlace.WorkPlaceController;
import NATrain.remoteControlModules.Command;
import NATrain.remoteControlModules.SwitchControlModule;
import NATrain.routes.Route;
import NATrain.routes.RouteType;
import NATrain.trackSideObjects.*;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.TimeUnit;

public abstract class AbstractRouteExecutor implements RouteExecutor {

    RouteStatus routeStatus = RouteStatus.CREATED;
    Route route;
    private static WorkPlaceController workPlaceController;
    private Boolean strongLock = false;
    private final TrackSection departureSection;
    private final TrackSection destinationSection; //в отправлении - это участок удаления
    private final Map<TrackSection, PropertyChangeListener> trackSectionUnlockerMap = new HashMap<>();
    private final Map<TrackSection, PropertyChangeListener> signalStateUpdaterMap = new HashMap<>();
    private final ConcurrentLinkedDeque<TrackSection> occupationalOrder;
    private TrackSection cursor;

    public static void setWorkPlaceController(WorkPlaceController workPlaceController) {
        AbstractRouteExecutor.workPlaceController = workPlaceController;
    }

    public AbstractRouteExecutor(Route route) {
        this.route = route;
        occupationalOrder = new ConcurrentLinkedDeque<>(route.getOccupationalOrder()); // create copy for processing
        this.departureSection = route.getDepartureTrackSection();
        this.destinationSection = route.getDestinationTrackSection();
    }

    public RouteStatus getRouteStatus() {
        return routeStatus;
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
                    routeStatus = RouteStatus.IN_PROCESS; //проверить все, кроме первого !!
                    workPlaceController.refreshRouteStatusTable();
                    removeSignalUpdaters();
                }
                autoselectSignalState(); // realize it in extended classes

                if (thisSection.isInterlocked()) {
                    if (event.getOldValue() == TrackSectionState.FREE && event.getNewValue() == TrackSectionState.OCCUPIED) {
                        thisSection.fixOccupation();
                    }
                    if (thisSection.isOccupationFixed() && event.getOldValue() == TrackSectionState.OCCUPIED && event.getNewValue() == TrackSectionState.FREE) {
                        thisSection.fixDeallocation();
                    }
                }

                if (route.getRouteType() == RouteType.DEPARTURE) {
                    if (previousSection.notInterlocked()
                            && thisSection.isOccupationFixed()
                            && thisSection.isDeallocationFixed()
                            && nextSection.isOccupationFixed()) {
                        thisSection.setInterlocked(false);
                        thisSection.removePropertyChangeListener(this);
                        trackSectionUnlockerMap.remove(thisSection);
                        if (trackSectionUnlockerMap.isEmpty()) {
                            routeStatus = RouteStatus.COMPLETED;
                            workPlaceController.refreshRouteStatusTable();
                        }
                    }
                }
            }
        }
    }

    private boolean checkTrackSections() {
        if (departureSection.getVacancyState() == TrackSectionState.OCCUPIED)
            strongLock = true;
        for (TrackSection trackSection : occupationalOrder) {
            if (trackSection.getVacancyState() == TrackSectionState.OCCUPIED || trackSection.isInterlocked()) {
                workPlaceController.log("   Track Sections check failed. " + trackSection.getId() + " is occupied or interlocked in route.");
                return false;
            }
        }
        if (route.getRouteType() != RouteType.SHUNTING && destinationSection.getVacancyState() == TrackSectionState.OCCUPIED) {
            workPlaceController.log("   Track Sections check failed. Last section isn't free in not shunting route.");
            return false;
        }
        workPlaceController.log("   Track Sections checked.");
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
                SwitchControlModule switchControlModule = (SwitchControlModule) aSwitch.getControlModule();
                if (switchState == SwitchState.PLUS) {
                    switchControlModule.sendCommand(aSwitch.getChannel(), Command.SET_SWITCH_TO_PLUS);
                    aSwitch.setSwitchState(SwitchState.PLUS); //TODO delete it when realize hardware
                } else {
                    switchControlModule.sendCommand(aSwitch.getChannel(), Command.SET_SWITCH_TO_MINUS);
                    aSwitch.setSwitchState(SwitchState.MINUS); //TODO delete it when realize hardware
                }
            }
        }
    }

    private void interlock() {
        occupationalOrder.forEach(trackSection -> trackSection.setInterlocked(true));
        workPlaceController.log(route.getDescription() + " is ready. Sections are interlocked.");
        routeStatus = RouteStatus.READY;
        workPlaceController.refreshRouteStatusTable();
    }

    private void cancel() {
        routeStatus = RouteStatus.CANCELLATION;
        workPlaceController.refreshRouteStatusTable();
        try {
            if (strongLock)
                TimeUnit.SECONDS.sleep(180);
            else
                TimeUnit.SECONDS.sleep(30);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        departureSection.setInterlocked(false);
        destinationSection.setInterlocked(false);
        route.getOccupationalOrder().forEach(trackSection -> trackSection.setInterlocked(false));
        removeUnusedUnlockers();
        removeSignalUpdaters();
        routeStatus = RouteStatus.CANCELLED;
        workPlaceController.refreshRouteStatusTable();
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

    private void createTrackSectionStateListeners() {
        TrackSection firstSection = occupationalOrder.pollFirst();
        TrackSectionUnlocker firstTrackSectionUnlocker = new TrackSectionUnlocker(TrackSection.EMPTY_TRACK_SECTION, firstSection, occupationalOrder.getFirst());
        trackSectionUnlockerMap.put(firstSection, firstTrackSectionUnlocker);
        firstSection.addPropertyChangeListener(firstTrackSectionUnlocker);
        cursor = firstSection;
        occupationalOrder.forEach(trackSection -> {
            if (trackSection.isInterlocked()) {
                TrackSectionUnlocker trackSectionUnlocker = new TrackSectionUnlocker(cursor, trackSection, occupationalOrder.getFirst());
                trackSectionUnlockerMap.put(trackSection, trackSectionUnlocker);
                trackSection.addPropertyChangeListener(trackSectionUnlocker);
            }
        });
        occupationalOrder.clear();
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
        if (checkTrackSections() && checkSwitchesAbilityToSet()) {
            setSwitchPositions();
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (checkTrackSections() && checkSwitchPositions()) {
                interlock();
                routeStatus = RouteStatus.READY;
                autoselectSignalState();
                createTrackSectionStateListeners();
            }
        }
    }

    protected boolean isAllSectionsFree() {
        for (TrackSection trackSection : route.getOccupationalOrder()) {
            if (trackSection.getVacancyState() == TrackSectionState.OCCUPIED)
                return false;
        }
        return true;
    }

    public String getRouteDescription() {
        return route.getDescription();
    }

}