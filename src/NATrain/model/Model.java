package NATrain.model;

import NATrain.UI.NavigatorFxController;
import NATrain.routes.Route;
import NATrain.trackSideObjects.*;
import NATrain.quads.*;
import NATrain.remoteControlModules.ControlModule;

import java.beans.PropertyChangeSupport;
import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

public enum Model implements Serializable {

    INSTANCE;

    private static String modelURL;

    protected static Integer xSize = 20; //default size
    protected static Integer ySize = 20;

    private static Quad[][] mainGrid = new Quad[20][20];

    private static Set<Route> routeTable = new CopyOnWriteArraySet<>();

    private static Map<String, Switch> switches = new ConcurrentHashMap<>();

    private static Map<String, Signal> signals = new ConcurrentHashMap<>();

    private static Map<String, TrackSection> trackSections =  new ConcurrentHashMap<>();

    private static Map<Integer, ControlModule> controlModules = new ConcurrentHashMap<>();

    public static Quad[][] getMainGrid() {
        return mainGrid;
    }

    public static Set<Route> getRouteTable() {
        return routeTable;
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

    public static Map<Integer, ControlModule> getControlModules() {
        return controlModules;
    }

    public static String getModelURL() {
        return modelURL;
    }

    public static void setModelURL(String modelURL) {
        Model.modelURL = modelURL;
    }

    public static void refreshAll() {
        Arrays.stream(mainGrid).flatMap(Arrays::stream).forEach(Quad::refresh);
    }

    public static void setGridLinesVisible(Boolean show) {
        Arrays.stream(mainGrid).flatMap(Arrays::stream).forEach(quad -> quad.setGridLineVisible(show));
    }

    public static void saveOnDisk() {
        try {
            ArrayList<QuadDTO> notEmptyQuadDTOS = new ArrayList<>();
            File modelFile = new File(modelURL);
            modelFile.createNewFile();
            FileOutputStream fileOutputStream = new FileOutputStream(modelFile);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(xSize);
            objectOutputStream.writeObject(ySize);
            objectOutputStream.writeObject(trackSections);
            objectOutputStream.writeObject(signals);
            objectOutputStream.writeObject(switches);
            objectOutputStream.writeObject(controlModules);
            objectOutputStream.writeObject(routeTable);

            Arrays.stream(mainGrid).flatMap(Arrays::stream).forEach(quad -> {
                if (quad.getType() != QuadType.EMPTY_QUAD) {
                    notEmptyQuadDTOS.add(QuadDTO.castToDTO(quad));
                }
            });
            objectOutputStream.writeObject(notEmptyQuadDTOS);
            fileOutputStream.flush();
            objectOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void loadFromDisk() {
        try {
            Path path = Paths.get(modelURL);
            if (path.toFile().exists()) {
                File modelFile = new File(modelURL);
                FileInputStream fileInputStream = new FileInputStream(modelFile);
                ObjectInputStream inputStream = new ObjectInputStream(fileInputStream);
                xSize = (Integer) inputStream.readObject();
                ySize  = (Integer) inputStream.readObject();
                mainGrid = new Quad[ySize][xSize];
                trackSections = (Map<String, TrackSection>) inputStream.readObject();
                trackSections.values().forEach(TracksideObject::addPropertyChangeSupport);
                trackSections.values().forEach(trackSection -> trackSection.setVacancyState(TrackSectionState.UNDEFINED));
                signals = (Map<String, Signal>) inputStream.readObject();
                signals.values().forEach(TracksideObject::addPropertyChangeSupport);
                signals.values().forEach(signal -> signal.setSignalState(SignalState.UNDEFINED));
                switches = (Map<String, Switch>) inputStream.readObject();
                switches.values().forEach(TracksideObject::addPropertyChangeSupport);
                switches.values().forEach(aSwitch -> aSwitch.setSwitchState(SwitchState.UNDEFINED));
                controlModules = (Map<Integer, ControlModule>) inputStream.readObject();
                routeTable = (CopyOnWriteArraySet<Route>) inputStream.readObject();

                ArrayList<QuadDTO> notEmptyQuadDTOS = (ArrayList<QuadDTO>) inputStream.readObject();
                inputStream.close();

                notEmptyQuadDTOS.forEach(quadDTO -> {
                    getMainGrid()[quadDTO.getY()][quadDTO.getX()] = QuadDTO.castToQuad(quadDTO);
                });
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        //fill null elements or refresh loaded quads view
        for (int y = 0; y < ySize; y++) {
            for (int x = 0; x < xSize; x++) {
                if (mainGrid[y][x] == null)
                    mainGrid[y][x] = new EmptyQuad(x, y);
                else
                    mainGrid[y][x].refresh();
            }
        }
    }
}
