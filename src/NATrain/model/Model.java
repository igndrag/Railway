package NATrain.model;

import NATrain.UI.NavigatorFxController;
import NATrain.controller.Route;
import NATrain.quads.*;
import NATrain.remoteControlModules.ControlModule;
import NATrain.remoteControlModules.SignalControlModule;
import NATrain.remoteControlModules.SwitchControlModule;
import NATrain.remoteControlModules.TrackControlModule;
import NATrain.trackSideObjects.Signal;
import NATrain.trackSideObjects.Switch;
import NATrain.trackSideObjects.TrackSection;
import NATrain.utils.SignalPare;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public enum Model implements Serializable {

    INSTANCE;

    protected static Integer xSize = 20; //default size
    protected static Integer ySize = 20;

    private static Quad[][] mainGrid = new Quad[20][20];

    private static Map<SignalPare, HashSet<Route>> routeTable = new HashMap<>();

    private static Map<String, Switch> switches = new HashMap<>();

    private static Map<String, Signal> signals = new HashMap<>();

    private static Map<String, TrackSection> trackSections =  new HashMap<>();

    private static Map<Integer, ControlModule> controlModules = new HashMap();


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

    public static Map<Integer, ControlModule> getControlModules() {
        return controlModules;
    }

    public static void refreshAll() {
        Arrays.stream(mainGrid).flatMap(Arrays::stream).forEach(Quad::refresh);
    }

    public static void saveOnDisk() {
        try {
            ArrayList<QuadDTO> notEmptyQuadDTOS = new ArrayList<>();
            File modelFile = new File(NavigatorFxController.modelURL);
            modelFile.createNewFile();
            FileOutputStream fileOutputStream = new FileOutputStream(modelFile);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(xSize);
            objectOutputStream.writeObject(ySize);
            objectOutputStream.writeObject(trackSections);
            objectOutputStream.writeObject(signals);
            objectOutputStream.writeObject(switches);
            objectOutputStream.writeObject(controlModules);

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
            NavigatorFxController.constructorMode = true;
            Path path = Paths.get(NavigatorFxController.modelURL);
            if (path.toFile().exists()) {
                File modelFile = new File(NavigatorFxController.modelURL);
                FileInputStream fileInputStream = new FileInputStream(modelFile);
                ObjectInputStream inputStream = new ObjectInputStream(fileInputStream);
                xSize = (Integer) inputStream.readObject();
                ySize  = (Integer) inputStream.readObject();
                mainGrid = new Quad[ySize][xSize];
                trackSections = (Map<String, TrackSection>) inputStream.readObject();
                signals = (Map<String, Signal>) inputStream.readObject();
                switches = (Map<String, Switch>) inputStream.readObject();
                controlModules = (Map<Integer, ControlModule>) inputStream.readObject();

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
