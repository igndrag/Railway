package NATrain.UI.workPlace.executors;

import NATrain.UI.workPlace.WorkPlaceController;
import NATrain.remoteControlModules.Command;
import NATrain.remoteControlModules.SwitchControlModule;
import NATrain.routes.Route;
import NATrain.routes.RouteType;
import NATrain.trackSideObjects.Switch;
import NATrain.trackSideObjects.SwitchState;
import NATrain.trackSideObjects.TrackSection;
import NATrain.trackSideObjects.TrackSectionState;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class RouteExecutor extends Thread {

    Lock lock = new ReentrantLock();
    RouteStatus routeStatus = RouteStatus.CREATED;
    Route route;
    private static WorkPlaceController workPlaceController;
    private Boolean strongLock = false;
    private Boolean fixOccupation = false;
    private Boolean fixDeallocation = false;
    private TrackSection firstSection;
    private TrackSection lastSection;
    private TrackSection cursor;
    private Map<TrackSection, TrackSectionUnlocker> trackSectionStateListenerMap;
    private ConcurrentLinkedDeque<TrackSection> occupationalOrder;

    public static void setWorkPlaceController(WorkPlaceController workPlaceController) {
        RouteExecutor.workPlaceController = workPlaceController;
    }

    public RouteExecutor (Route route) {
        this.route = route;
        occupationalOrder = new ConcurrentLinkedDeque<>(route.getOccupationalOrder()); // create copy for processing
        this.firstSection = occupationalOrder.pollFirst();
        this.lastSection = occupationalOrder.pollLast();
        cursor = firstSection;
    }

    @Override
    public void run() {
        super.run();


    }

    private class TrackSectionUnlocker implements PropertyChangeListener {
        @Override
        public void propertyChange(PropertyChangeEvent event) {
            strongLock = true; // if something changed in tracks states apply strong lock

            if (cursor == firstSection) {
                if (firstSection.getVacancyState() == TrackSectionState.OCCUPIED) { //weak interlocking, deallocation doesn't check
                    fixDeallocation = false;
                    fixOccupation = true;

                } else if (firstSection.getVacancyState() == TrackSectionState.FREE && fixOccupation) {
                    fixOccupation = false; //prepare to next step
                    fixDeallocation = true;
                    shiftCursor();
                }
            } else if (cursor.getVacancyState() == TrackSectionState.OCCUPIED && fixDeallocation) {
                fixDeallocation = false;
                fixOccupation = true;
            } else if (cursor.getVacancyState() == TrackSectionState.FREE && fixOccupation) {
                fixOccupation = false;
                fixDeallocation = true;
                shiftCursor();
            }
        }

    }

    private boolean checkTrackSections() {
        if (firstSection.getVacancyState() == TrackSectionState.OCCUPIED)
            strongLock = true;
        for (TrackSection trackSection : occupationalOrder) {
            if (trackSection.getVacancyState() == TrackSectionState.OCCUPIED || trackSection.isInterlocked())
                workPlaceController.log("   Track Sections check failed. " + trackSection.getId() + " is occupied or interlocked in route.");
                return false;
        }
        if (route.getRouteType() != RouteType.SHUNTING && lastSection.getVacancyState() == TrackSectionState.OCCUPIED) {
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
                    }
                       else {
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
            if (aSwitch.getSwitchState() != switchState && aSwitch.isChangePositionAvailable()){
                SwitchControlModule switchControlModule = (SwitchControlModule) aSwitch.getControlModule();
                    if (switchState == SwitchState.PLUS) {
                        switchControlModule.sendCommand(aSwitch.getChannel(), Command.SET_SWITCH_TO_PLUS);
                    }
                    else
                        switchControlModule.sendCommand(aSwitch.getChannel(), Command.SET_SWITCH_TO_MINUS);
            }
        }
    }

    private void interlock() {
        occupationalOrder.forEach(trackSection -> trackSection.setInterlocked(true));
        workPlaceController.log(route.getDescription() + " is ready. Sections are interlocked.");
    }

    private void cancel() {
        routeStatus = RouteStatus.CANCELLATION;
        try {
            if (strongLock)
                TimeUnit.SECONDS.sleep(180);
            else
                TimeUnit.SECONDS.sleep(30);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        firstSection.setInterlocked(false);
        lastSection.setInterlocked(false);
        occupationalOrder.forEach(trackSection -> trackSection.setInterlocked(false));
        removeUnusedListeners();
        routeStatus = RouteStatus.CANCELLED;
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

    private void shiftCursor() {
        cursor.removePropertyChangeListener(trackSectionStateListenerMap.get(cursor));
        cursor.setInterlocked(false);
        trackSectionStateListenerMap.remove(cursor);
        if (!occupationalOrder.isEmpty()) {
            cursor = occupationalOrder.pollFirst();
        } else  if (cursor != lastSection) {
            cursor = lastSection;
        } else {
            cursor = null;
        }
    }

    private void createTrackSectionStateListeners() {
        //TODO
    }

    private void removeUnusedListeners(){
        occupationalOrder.forEach(trackSection -> {
            trackSection.removePropertyChangeListener(trackSectionStateListenerMap.get(trackSection));
        });
        occupationalOrder.clear();//TODO
    }

    private boolean tryToSetRoute() {
        if (checkTrackSections() && checkSwitchesAbilityToSet()) {

            setSwitchPositions();
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (checkTrackSections() && checkSwitchPositions()) {
                interlock();
                return true;
            }
        }
        return false;
    }


}
