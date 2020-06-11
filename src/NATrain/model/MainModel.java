package NATrain.model;

import NATrain.controller.Route;
import NATrain.trackSideObjects.Signal;
import NATrain.trackSideObjects.Switch;
import NATrain.trackSideObjects.TrackSection;
import NATrain.utils.SignalPare;

import java.util.HashMap;
import java.util.HashSet;

public class MainModel {
    HashMap<SignalPare, Route> routeTable = new HashMap<>();
    HashSet<Switch> switches = new HashSet<>();
    HashSet<TrackSection> trackSections = new HashSet<>();
    HashSet<Signal> signals = new HashSet<>();
}
