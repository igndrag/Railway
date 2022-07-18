package NATrain.model;

import NATrain.UI.AppConfigController;
import NATrain.UI.scenario.Scenario;
import NATrain.quads.custom.OnOffState;
import NATrain.routes.StationTrack;
import NATrain.routes.Route;
import NATrain.routes.Trackline;
import NATrain.routes.TracklineBlockSection;
import NATrain.trackSideObjects.*;
import NATrain.quads.*;
import NATrain.trackSideObjects.customObjects.*;
import NATrain.trackSideObjects.movableObjects.Locomotive;
import NATrain.trackSideObjects.movableObjects.Wagon;
import NATrain.trackSideObjects.signals.Signal;
import NATrain.trackSideObjects.signals.SignalState;
import NATrain.trackSideObjects.switches.Switch;
import NATrain.trackSideObjects.switches.SwitchState;
import NATrain.trackSideObjects.trackSections.TrackSection;
import NATrain.trackSideObjects.trackSections.TrackSectionState;
import NATrain.сontrolModules.ControlModule;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

public enum Model implements Serializable {

     INSTANCE;

    protected static Integer xSize = 20; //default size
    protected static Integer ySize = 20;

    private static Quad[][] mainGrid;

    private static Set<Route> routeTable;

    private static Map<String, Switch> switches;

    private static Map<String, Signal> signals;

    private static Map<String, TrackSection> trackSections;

    private static Map<String, ControlModule> controlModules;

    private static Map<String, StationTrack> stationTracks;

    private static Map<String, Locomotive> locomotives;

    private static Map<String, Wagon> wagons;

    private static Set<Trackline> tracklines;

    private static Map<Long, RFIDTag> tags;

    private static Map<String, Servo> servos;

    private static Map<String, Gate> gates;

    private static Map<String, PolarityChanger> polarityChangers;

    private static Map<String, RoadCrossing> roadCrossings;

    private static Map<String, OnOffObject> onOffObjects;

    public static Set<Scenario> scenarios = new HashSet<>();

    static {
        servos = new ConcurrentHashMap<>();
        servos.put(Servo.TEST_SERVO.getId(), Servo.TEST_SERVO);
        servos.put(Servo.TEST_SERVO_2.getId(), Servo.TEST_SERVO_2);
        gates = new ConcurrentHashMap<>();
        gates.put(Gate.TEST_GATE.getId(), Gate.TEST_GATE);

        polarityChangers = new ConcurrentHashMap<>();
        polarityChangers.put(PolarityChanger.TEST_POLARITY_CHANGER.getId(), PolarityChanger.TEST_POLARITY_CHANGER);
    }

    public static void initEmptyModel() {

        mainGrid = new Quad[ySize][xSize];

        routeTable = new CopyOnWriteArraySet<>();

        switches = new ConcurrentHashMap<>();

        signals = new ConcurrentHashMap<>();

        trackSections = new ConcurrentHashMap<>();

        controlModules = new ConcurrentHashMap<>();

        stationTracks = new ConcurrentHashMap<>();

        locomotives = new ConcurrentHashMap<>();

        wagons = new ConcurrentHashMap<>();

        tracklines = new CopyOnWriteArraySet<>();

        tags = new HashMap<>();

        servos = new ConcurrentHashMap<>();

        gates = new ConcurrentHashMap<>();

        polarityChangers = new ConcurrentHashMap<>();

        roadCrossings = new ConcurrentHashMap<>();

        onOffObjects = new ConcurrentHashMap<>();

        for (int y = 0; y < ySize; y++) {
            for (int x = 0; x < xSize; x++ ) {
                mainGrid[y][x] = new EmptyQuad(x,y);
            }
        }
    }

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

    public static Map<String, ControlModule> getControlModules() {
        return controlModules;
    }

    public static Map<String, StationTrack> getStationTracks() {
        return stationTracks;
    }

    public static Map<String, Locomotive> getLocomotives() {
        return locomotives;
    }

    public static Map<String, Wagon> getWagons() {return wagons;}

    public static Set<Trackline> getTracklines() {
        return tracklines;
    }

    public static Map<Long, RFIDTag> getTags() {
        return tags;
    }

    public static Map<String, Servo> getServos() {
        return servos;
    }

    public static Map<String, Gate> getGates() {
        return gates;
    }

    public static Map<String, PolarityChanger> getPolarityChangers() {
        return polarityChangers;
    }

    public static Map<String, RoadCrossing> getRoadCrossings() {
        return roadCrossings;
    }

    public static Map<String, OnOffObject> getOnOffObjects() {
        return onOffObjects;
    }

    public static void refreshAll() {
        Arrays.stream(mainGrid).flatMap(Arrays::stream).parallel().forEach(Quad::refresh);
    }

    public static void setGridLinesVisible(Boolean show) {
        Arrays.stream(mainGrid).flatMap(Arrays::stream).forEach(quad -> quad.setGridLineVisible(show));
    }

    public static void saveOnDisk() {
        try {
            ArrayList<QuadDTO> notEmptyQuadDTOs = new ArrayList<>();
            ArrayList<BlockingQuadDTO> notEmptyTrackQuadDTOs = new ArrayList<>();

            File modelFile = new File(AppConfigController.getModelURL());

            if (!modelFile.exists()) {
                modelFile.createNewFile();
            }

            FileOutputStream fileOutputStream = new FileOutputStream(modelFile);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(xSize);
            objectOutputStream.writeObject(ySize);
            objectOutputStream.writeObject(trackSections);
            objectOutputStream.writeObject(signals);
            objectOutputStream.writeObject(switches);
            objectOutputStream.writeObject(controlModules);
            objectOutputStream.writeObject(stationTracks);
            objectOutputStream.writeObject(locomotives);
            objectOutputStream.writeObject(wagons);
            objectOutputStream.writeObject(routeTable);
            objectOutputStream.writeObject(servos);
            objectOutputStream.writeObject(gates);
            objectOutputStream.writeObject(polarityChangers);
            objectOutputStream.writeObject(roadCrossings);
            objectOutputStream.writeObject(onOffObjects);
            tracklines.forEach(track -> { // change EMPTY_SIGNALs to null fow writing
                track.getBlockSections().forEach(blockSection -> {
                    if (blockSection.getNormalDirectionSignal() == Signal.EMPTY_SIGNAL) {
                        blockSection.setNormalDirectionSignal(null);
                    }
                    if (blockSection.getReversedDirectionSignal() == Signal.EMPTY_SIGNAL) {
                        blockSection.setReversedDirectionSignal(null);
                    }
                });
            });
            objectOutputStream.writeObject(tracklines);
            tracklines.forEach(track -> { // return EMPTY_SIGNALs back
                track.getBlockSections().forEach(blockSection -> {
                    if (blockSection.getNormalDirectionSignal() == null) {
                        blockSection.setNormalDirectionSignal(Signal.EMPTY_SIGNAL);
                    }
                    if (blockSection.getReversedDirectionSignal() == null) {
                        blockSection.setReversedDirectionSignal(Signal.EMPTY_SIGNAL);
                    }
                });
            });

            objectOutputStream.writeObject(tags);

            Arrays.stream(mainGrid).flatMap(Arrays::stream).forEach(quad -> {
                if (quad.getType() != QuadType.EMPTY_QUAD) {
                    if (quad instanceof BaseQuad) {
                        notEmptyQuadDTOs.add(QuadDTO.castToDTO(quad));
                    } else if (quad instanceof BlockingBaseQuad) {
                        notEmptyTrackQuadDTOs.add(BlockingQuadDTO.castToDTO(quad));
                    }
                }
            });
            objectOutputStream.writeObject(notEmptyQuadDTOs);
            objectOutputStream.writeObject(notEmptyTrackQuadDTOs);
            fileOutputStream.flush();
            objectOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void loadFromDisk() {
        try {
            Path path = Paths.get(AppConfigController.getModelURL());
            if (path.toFile().exists() && path.toFile().length() > 100) {
                File modelFile = new File(AppConfigController.getModelURL());
                FileInputStream fileInputStream = new FileInputStream(modelFile);
                ObjectInputStream inputStream = new ObjectInputStream(fileInputStream);
                xSize = (Integer) inputStream.readObject();
                ySize = (Integer) inputStream.readObject();
                mainGrid = new Quad[ySize][xSize];
                trackSections = (Map<String, TrackSection>) inputStream.readObject();
                trackSections.values().forEach(TracksideObject::addPropertyChangeSupport);
                signals = (Map<String, Signal>) inputStream.readObject();
                signals.values().forEach(TracksideObject::addPropertyChangeSupport);
                switches = (Map<String, Switch>) inputStream.readObject();
                switches.values().forEach(TracksideObject::addPropertyChangeSupport);
                controlModules = (Map<String ,ControlModule>) inputStream.readObject();
                stationTracks = (Map<String, StationTrack>) inputStream.readObject();
                stationTracks.values().stream().forEach(TracksideObject::addPropertyChangeSupport);
                locomotives = (Map<String, Locomotive>) inputStream.readObject();
                locomotives.values().forEach(TracksideObject::addPropertyChangeSupport);
                wagons = (Map<String, Wagon>) inputStream.readObject();
                routeTable = (CopyOnWriteArraySet<Route>) inputStream.readObject();
                servos = (Map<String, Servo>) inputStream.readObject();
                servos.values().forEach(TracksideObject::addPropertyChangeSupport);
                gates = (Map<String, Gate>) inputStream.readObject();
                gates.values().forEach(TracksideObject::addPropertyChangeSupport);
                polarityChangers = (Map<String, PolarityChanger>) inputStream.readObject();
                polarityChangers.values().forEach(TracksideObject::addPropertyChangeSupport);
                roadCrossings = (Map<String, RoadCrossing>) inputStream.readObject();
                roadCrossings.values().forEach(TracksideObject::addPropertyChangeSupport);
                onOffObjects = (Map<String, OnOffObject>) inputStream.readObject();
                onOffObjects.values().forEach(TracksideObject::addPropertyChangeSupport);
                tracklines = (Set<Trackline>) inputStream.readObject();
                tags = (Map<Long, RFIDTag>) inputStream.readObject();
                tags.values().forEach(RFIDTag::addPropertyChangeSupport);
                trackSections.values().forEach(trackSection -> trackSection.setVacancyState(TrackSectionState.UNDEFINED));
                signals.values().forEach(signal -> signal.setSignalState(SignalState.UNDEFINED));
                switches.values().forEach(aSwitch -> aSwitch.setSwitchState(SwitchState.UNDEFINED));
                stationTracks.values().forEach(track -> track.setVacancyState(TrackSectionState.UNDEFINED));

                tracklines.forEach(track -> {
                    track.init();
                    track.getBlockSections().forEach(blockSection -> {
                        blockSection.addPropertyChangeSupport();
                        blockSection.setVacancyState(TrackSectionState.UNDEFINED);
                        Signal signal = blockSection.getNormalDirectionSignal();
                        if (signal == null) {
                            blockSection.setNormalDirectionSignal(Signal.EMPTY_SIGNAL);
                        } else {
                            signal.addPropertyChangeSupport();
                            signal.setSignalState(SignalState.UNDEFINED);
                        }
                        signal = blockSection.getReversedDirectionSignal();
                        if (signal == null) {
                            blockSection.setReversedDirectionSignal(Signal.EMPTY_SIGNAL);
                        } else {
                            signal.addPropertyChangeSupport();
                            signal.setSignalState(SignalState.UNDEFINED);
                        }
                    });
                });

                ArrayList<QuadDTO> notEmptyQuadDTOS = (ArrayList<QuadDTO>) inputStream.readObject();
                ArrayList<BlockingQuadDTO> notEmptyTrackQuadDTOS = (ArrayList<BlockingQuadDTO>) inputStream.readObject();
                inputStream.close();

                notEmptyQuadDTOS.forEach(quadDTO -> {
                    getMainGrid()[quadDTO.getY()][quadDTO.getX()] = QuadDTO.castToQuad(quadDTO);
                });
                notEmptyTrackQuadDTOS.forEach(trackQuadDTO -> {
                    getMainGrid()[trackQuadDTO.getY()][trackQuadDTO.getX()] = BlockingQuadDTO.castToQuad(trackQuadDTO);
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

    public void getEvenStationSignals() {

    }

    public void getOddStationSignals() {

    }

    public static void allObjectsToDefault() {
        tracklines.forEach(track -> {
            track.getBlockSections().forEach(blockSection -> {
                Signal normalDirectionSignal = blockSection.getNormalDirectionSignal();
                if (normalDirectionSignal != null && normalDirectionSignal != Signal.EMPTY_SIGNAL) {
                    normalDirectionSignal.setSignalState(SignalState.UNDEFINED);
                }
                Signal reversedDirectionSignal = blockSection.getReversedDirectionSignal();
                if (reversedDirectionSignal != null && reversedDirectionSignal != Signal.EMPTY_SIGNAL) {
                    reversedDirectionSignal.setSignalState(SignalState.UNDEFINED);
                }
                if (blockSection != TracklineBlockSection.EMPTY_BLOCK_SECTION) {
                    blockSection.setVacancyState(TrackSectionState.UNDEFINED);
                }
            });
        });

        Model.getSignals().values().forEach(signal -> signal.setSignalState(SignalState.UNDEFINED));
        Model.getTrackSections().values().forEach(trackSection -> trackSection.setVacancyState(TrackSectionState.UNDEFINED));
        Model.getStationTracks().values().forEach(stationTrack -> stationTrack.setVacancyState(TrackSectionState.UNDEFINED));
        Model.getOnOffObjects().values().forEach(onOffObject -> {
            onOffObject.off();
            onOffObject.setState(OnOffState.UNDEFINED);
        });
    }
}
