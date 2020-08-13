package NATrain.controller;

import NATrain.trackSideObjects.Signal;
import NATrain.trackSideObjects.Switch;
import NATrain.trackSideObjects.TrackSection;
import NATrain.utils.SignalPare;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;

public class Route implements Serializable {
    Signal from;
    Signal to;
    SignalPare signalPare;
    SignalState signalState;
    Boolean routeAfterSignal;
    HashMap <Switch, SwitchState> switchSafetyRules;
    HashSet <TrackSection> trackSectionSafetyRules;

}
