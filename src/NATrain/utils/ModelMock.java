package NATrain.utils;

import NATrain.controller.SignalState;
import NATrain.controller.SwitchState;
import NATrain.model.Model;
import NATrain.quads.EmptyQuad;
import NATrain.remoteControlDevice.TrackControlModule;
import NATrain.trackSideObjects.Signal;
import NATrain.trackSideObjects.Switch;
import NATrain.trackSideObjects.TrackSection;
import java.util.Arrays;
import java.util.HashSet;

public class ModelMock {

    public static void MockModel() {

        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 20; j++) {
                Model.getMainGrid()[j][i] = new EmptyQuad(i, j);
            }
        }

        Model.getSignals().put("S1", new Signal("S1", new HashSet<>(
                Arrays.asList(SignalState.GREEN,
                              SignalState.RED,
                              SignalState.YELLOW,
                              SignalState.BLINKED_YELLOW))));



        Model.getSignals().put("M1", new Signal("M1", new HashSet<>(
                                Arrays.asList(SignalState.WHITE,
                              SignalState.BLUE))));

        Switch oneSwitch = new Switch("1", SwitchState.PLUS);

        Model.getSwitches().put("1", oneSwitch);
        Model.getSwitches().put("3", new Switch("3", SwitchState.MINUS));

        TrackSection oneTrackSection = new TrackSection ("1-3SP");
        TrackSection twoTrackSection = new TrackSection ("SP");

        oneTrackSection.setChannel(1);
        Model.getTrackSections().put("1-3SP", oneTrackSection);
        Model.getTrackSections().put("SP", twoTrackSection);

        TrackControlModule trackControlModule = new TrackControlModule(0);
        trackControlModule.getChannels()[0] = oneTrackSection;
        oneTrackSection.setControlModule(trackControlModule);

        Model.getTrackControlModules().put(trackControlModule.getAddress(), trackControlModule);

        TrackControlModule trackControlModule1 = new TrackControlModule(1);
        trackControlModule.getChannels()[0] = twoTrackSection;
        twoTrackSection.setControlModule(trackControlModule1);

        Model.getTrackControlModules().put(trackControlModule1.getAddress(), trackControlModule1);
    }
}