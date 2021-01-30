package NATrain.utils;

import NATrain.trackSideObjects.SwitchState;
import NATrain.model.Model;
import NATrain.quads.EmptyQuad;
import NATrain.remoteControlModules.TrackControlModule;
import NATrain.trackSideObjects.Signal;
import NATrain.trackSideObjects.SignalType;
import NATrain.trackSideObjects.Switch;
import NATrain.trackSideObjects.TrackSection;
import NATrain.сontrolModules.ControlModule;
import NATrain.сontrolModules.UniversalModule;

public class ModelMock {

    public static void MockModel() {

        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 20; j++) {
                Model.getMainGrid()[j][i] = new EmptyQuad(i, j);
            }
        }

        Model.getSignals().put("S1", new Signal("S1", SignalType.STATION));
        Model.getSignals().put("M1", new Signal("M1", SignalType.TRIMMER));

        ControlModule controlModule = new UniversalModule("testModule");
        Model.getControlModules().add(controlModule);

        // topic name: NATrain/controlModules/testModule

        Switch oneSwitch = new Switch("1");
        Switch twoSwitch = new Switch("2");
        Switch threeSwitch = new Switch("3");
        threeSwitch.setParedSwitch(twoSwitch);
        twoSwitch.setParedSwitch(threeSwitch);
        threeSwitch.setNormalState(SwitchState.MINUS);


        Model.getSwitches().put("1", oneSwitch);
        Model.getSwitches().put("2", twoSwitch);
        Model.getSwitches().put("3", threeSwitch);
        Model.getSwitches().put("4", new Switch("4"));

        TrackSection oneTrackSection = new TrackSection ("1-3SP");
        TrackSection twoTrackSection = new TrackSection ("SP");
        controlModule.getInputChannels()[0] = oneTrackSection.getInputChannel();

        oneTrackSection.setChannel(0);
        Model.getTrackSections().put("1-3SP", oneTrackSection);
        Model.getTrackSections().put("SP", twoTrackSection);
        Model.getTrackSections().put("2-4SP", new TrackSection("2-4SP"));
        Model.getTrackSections().put("6-8SP", new TrackSection("6-8SP"));

        TrackControlModule trackControlModule = new TrackControlModule(0);
        trackControlModule.setTrackSideObjectOnChannel(oneTrackSection, 0);
        //oneTrackSection.setControlModule(trackControlModule);

        Model.getRemoteControlModules().put(0, trackControlModule);

        twoTrackSection.setChannel(1);
        TrackControlModule trackControlModule1 = new TrackControlModule(1);
        trackControlModule.setTrackSideObjectOnChannel(twoTrackSection, 1);
       // twoTrackSection.setControlModule(trackControlModule);

        Model.getRemoteControlModules().put(1, trackControlModule1);
    }
}