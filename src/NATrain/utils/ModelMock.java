package NATrain.utils;

import NATrain.controller.SignalState;
import NATrain.controller.SwitchState;
import NATrain.model.Model;
import NATrain.quads.EmptyQuad;
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

        Model.getSwitches().put("1", new Switch("1", SwitchState.PLUS));
        Model.getSwitches().put("3", new Switch("3", SwitchState.MINUS));

        Model.getTrackSections().put("1-3SP",new TrackSection("1-3SP"));
        Model.getTrackSections().put("SP", new TrackSection("SP"));

    }
}