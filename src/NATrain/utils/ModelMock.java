package NATrain.utils;

import NATrain.controller.SignalState;
import NATrain.controller.SwitchState;
import NATrain.model.Model;
import NATrain.quads.EmptyQuad;
import NATrain.quads.Quad;
import NATrain.trackSideObjects.Signal;
import NATrain.trackSideObjects.Switch;
import NATrain.trackSideObjects.TrackSection;
import NATrain.view.View;
import javafx.geometry.Insets;
import javafx.scene.layout.Pane;

import java.util.Arrays;
import java.util.HashSet;

public class ModelMock {

    public static void MockModel() {

        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 20; j++) {
                Model.getMainGrid()[j][i] = new EmptyQuad(i, j);
            }
        }

        Model.getSignals().add(new Signal("S1", new HashSet<>(
                Arrays.asList(SignalState.GREEN,
                              SignalState.RED,
                              SignalState.YELLOW,
                              SignalState.BLINKED_YELLOW))));

        Model.getSignals().add(new Signal("M1", new HashSet<>(
                Arrays.asList(SignalState.WHITE,
                              SignalState.BLUE))));

        Model.getSwitches().add(new Switch("1", SwitchState.PLUS));
        Model.getSwitches().add(new Switch("3", SwitchState.MINUS));

        Model.getTrackSections().add(new TrackSection("1-3SP"));
        Model.getTrackSections().add(new TrackSection("SP"));

    }
}