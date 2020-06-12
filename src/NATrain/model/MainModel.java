package NATrain.model;

import NATrain.controller.Route;
import NATrain.quads.QuadImpl;
import NATrain.remoteControlDevice.ControlModule;
import NATrain.trackSideObjects.Signal;
import NATrain.trackSideObjects.Switch;
import NATrain.trackSideObjects.TrackSection;
import NATrain.utils.SignalPare;

import java.util.HashMap;
import java.util.HashSet;

public class MainModel {

    private static HashMap<String, QuadImpl> notEmptyQuads = new HashMap<>();

    private static HashMap<Integer, ControlModule> controlModules = new HashMap<>();

    HashMap<SignalPare, Route> routeTable = new HashMap<>();
    HashSet<Switch> switches = new HashSet<>();
    HashSet<TrackSection> trackSections = new HashSet<>();
    HashSet<Signal> signals = new HashSet<>();

    public static HashMap<String, QuadImpl> getNotEmptyQuads() {
        return notEmptyQuads;
    }

    public static HashMap<Integer, ControlModule> getControlModules() {
        return controlModules;
    }
}
