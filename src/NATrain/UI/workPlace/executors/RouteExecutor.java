package NATrain.UI.workPlace.executors;

import NATrain.UI.workPlace.WorkPlaceController;
import NATrain.model.Model;
import NATrain.remoteControlModules.Command;
import NATrain.remoteControlModules.SwitchControlModule;
import NATrain.routes.Route;
import NATrain.trackSideObjects.Switch;
import NATrain.trackSideObjects.SwitchState;
import NATrain.trackSideObjects.TrackSection;
import NATrain.trackSideObjects.TrackSectionState;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class RouteExecutor extends Thread {

    Lock lock = new ReentrantLock();
    RouteStatus routeStatus = RouteStatus.CREATED;
    Route route;
    private static WorkPlaceController workPlaceController;

    public static void setWorkPlaceController(WorkPlaceController workPlaceController) {
        RouteExecutor.workPlaceController = workPlaceController;
    }

    public RouteExecutor (Route route) {
        this.route = route;
    }

    @Override
    public void run() {
        super.run();
        routeStatus = RouteStatus.IN_PROCESS;
    }

    private class StateListener implements PropertyChangeListener {
        @Override
        public void propertyChange(PropertyChangeEvent event) {
            lock.unlock();
        }

    }

    private boolean checkTrackSections() {
        for (TrackSection trackSection : route.getOccupationalOrder()) {
            if (trackSection.getVacancyState() == TrackSectionState.OCCUPIED || trackSection.isInterlocked())
                workPlaceController.log("Track Sections check failed. " + trackSection.getId() + " is occupied or interlocked in route.");
                return false;
        }
        workPlaceController.log("Track Sections checked.");
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
                           workPlaceController.log("Switch positions check failed. Unavailable to set " + bSwitch.getId() + " pared switch in right position.");
                           return false;
                    }
                }
            } else {
                workPlaceController.log("Switch positions check failed. Unavailable to set " + aSwitch.getId() + " switch in right position.");
                return false;
            }
        }
        workPlaceController.log("Switch ability to set in route checked.");
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
        route.getOccupationalOrder().forEach(trackSection -> trackSection.setInterlocked(true));
    }


}
