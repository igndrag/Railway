package NATrain.model;

import NATrain.UI.NavigatorFxController;
import NATrain.controller.Route;
import NATrain.quads.AbstractQuad;
import NATrain.quads.BaseQuad;
import NATrain.quads.Quad;
import NATrain.remoteControlDevice.ControlModule;
import NATrain.remoteControlDevice.TrackControlModule;
import NATrain.trackSideObjects.Signal;
import NATrain.trackSideObjects.Switch;
import NATrain.trackSideObjects.TrackSection;
import NATrain.trackSideObjects.TrackSideObject;
import NATrain.utils.SignalPare;
import javafx.collections.transformation.SortedList;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public enum Model implements Serializable {

    INSTANCE;

    private Quad[][] mainGrid = new Quad[20][20]; //default size yet

    private Map<Integer, ControlModule> controlModules = new HashMap<>();

    private Map<SignalPare, HashSet<Route>> routeTable = new HashMap<>();

    private Map<String, Switch> switches = new TreeMap();

    private Map<String, Signal> signals =  new TreeMap();

    private Map<String, TrackSection> trackSections =  new TreeMap();

    private Map<Integer, TrackControlModule> trackControlModules = new TreeMap<>();

    public Map<SignalPare, HashSet<Route>> getRouteTable() {
        return routeTable;
    }

    public static Map<Integer, TrackControlModule> getTrackControlModules() {
        return INSTANCE.trackControlModules;
    }

    public static Quad[][] getMainGrid() {
        return INSTANCE.mainGrid;
    }

    public static Map<String, Switch> getSwitches() {
        return INSTANCE.switches;
    }

    public static Map<String, Signal> getSignals() {
        return INSTANCE.signals;
    }

    public static Map<String, TrackSection> getTrackSections() {
        return INSTANCE.trackSections;
    }

    public void refreshAll() {
        Arrays.stream(mainGrid).flatMap(Arrays::stream).forEach(Quad::refresh);
    }


    public static void saveOnDisk() {
        try {
            ArrayList<Quad> notEmptyQuadDTOS = new ArrayList<>();
            File modelFile = new File(NavigatorFxController.modelURL);
            modelFile.createNewFile();
            FileOutputStream fileOutputStream = new FileOutputStream(modelFile);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(INSTANCE.mainGrid);
            fileOutputStream.flush();
            objectOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void loadFromDisk() {
        try {
            Path path = Paths.get(NavigatorFxController.modelURL);
            if (path.toFile().exists()) {
                File modelFile = new File(NavigatorFxController.modelURL);
                FileInputStream fileInputStream = new FileInputStream(modelFile);
                ObjectInputStream inputStream = new ObjectInputStream(fileInputStream);
                inputStream.close();

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
