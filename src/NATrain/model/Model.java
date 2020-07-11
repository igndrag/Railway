package NATrain.model;

import NATrain.controller.Route;
import NATrain.quads.BaseQuad;
import NATrain.quads.Quad;
import NATrain.remoteControlDevice.ControlModule;
import NATrain.trackSideObjects.Signal;
import NATrain.trackSideObjects.Switch;
import NATrain.trackSideObjects.TrackSection;
import NATrain.utils.SignalPare;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

public class Model implements Serializable {

    private static Quad[][] mainGrid = new Quad[20][20]; //default size

    private static HashMap<Integer, ControlModule> controlModules = new HashMap<>();

    private static HashMap<SignalPare, HashSet<Route>> routeTable = new HashMap<>();

    private static HashSet<Switch> switches = new HashSet<>();

    private static HashSet<TrackSection> trackSections = new HashSet<>();

    private static HashSet<Signal> signals = new HashSet<>();

    public static HashMap<Integer, ControlModule> getControlModules() {
        return controlModules;
    }

    public static HashMap<SignalPare, HashSet<Route>> getRouteTable() {
        return routeTable;
    }

    public static HashSet<Switch> getSwitches() {
        return switches;
    }

    public static HashSet<TrackSection> getTrackSections() {
        return trackSections;
    }

    public static HashSet<Signal> getSignals() {
        return signals;
    }

    public static Quad[][] getMainGrid() {
        return mainGrid;
    }

    public void refreshAll() {
        Arrays.stream(mainGrid).flatMap(Arrays::stream).forEach(Quad::refresh);
    }
}
