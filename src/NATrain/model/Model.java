package NATrain.model;

import NATrain.controller.Route;
import NATrain.quads.AbstractQuad;
import NATrain.quads.BaseQuad;
import NATrain.quads.Quad;
import NATrain.remoteControlDevice.ControlModule;
import NATrain.trackSideObjects.Signal;
import NATrain.trackSideObjects.Switch;
import NATrain.trackSideObjects.TrackSection;
import NATrain.trackSideObjects.TrackSideObject;
import NATrain.utils.SignalPare;
import javafx.collections.transformation.SortedList;

import java.io.Serializable;
import java.util.*;

public class Model implements Serializable {

    private static Quad[][] mainGrid = new Quad[20][20]; //default size

    private static Map<Integer, ControlModule> controlModules = new HashMap<>();

    private static Map<SignalPare, HashSet<Route>> routeTable = new HashMap<>();

    private static Map<String, Switch> switches = new TreeMap();

    private static Map<String, Signal> signals =  new TreeMap();

    private static Map<String, TrackSection> trackSections =  new TreeMap();

    public static Map<Integer, ControlModule> getControlModules() {
        return controlModules;
    }

    public static Map<SignalPare, HashSet<Route>> getRouteTable() {
        return routeTable;
    }


    public static Quad[][] getMainGrid() {
        return mainGrid;
    }

    public static Map<String, Switch> getSwitches() {
        return switches;
    }

    public static Map<String, Signal> getSignals() {
        return signals;
    }

    public static Map<String, TrackSection> getTrackSections() {
        return trackSections;
    }

    public void refreshAll() {
        Arrays.stream(mainGrid).flatMap(Arrays::stream).forEach(Quad::refresh);
    }
}
