package NATrain.model;

import NATrain.routes.StationTrack;
import NATrain.routes.Route;
import NATrain.routes.Track;
import NATrain.routes.TrackBlockSection;
import NATrain.trackSideObjects.*;
import NATrain.quads.*;
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

    private static String modelURL;

    protected static Integer xSize = 20; //default size
    protected static Integer ySize = 20;

    private static Quad[][] mainGrid = new Quad[20][20];

    private static Set<Route> routeTable = new CopyOnWriteArraySet<>();

    private static Map<String, Switch> switches = new ConcurrentHashMap<>();

    private static Map<String, Signal> signals = new ConcurrentHashMap<>();

    private static Map<String, TrackSection> trackSections = new ConcurrentHashMap<>();

    private static Map<String, ControlModule> controlModules = new ConcurrentHashMap<>();

    private static Map<String, StationTrack> stationTracks = new ConcurrentHashMap<>();

    private static Map<String, Locomotive> locomotives = new ConcurrentHashMap<>();

    private static Map<String, Wagon> wagons = new ConcurrentHashMap<>();

    private static Set<Track> tracks = new CopyOnWriteArraySet<>();

    private static Map<Long, RFIDTag> tags = new HashMap<>();

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

    public static Set<Track> getTracks() {
        return tracks;
    }

    public static Map<Long, RFIDTag> getTags() {
        return tags;
    }

    public static String getModelURL() {
        return modelURL;
    }

    public static void setModelURL(String modelURL) {
        Model.modelURL = modelURL;
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
            objectOutputStream.writeObject(stationTracks);
            objectOutputStream.writeObject(locomotives);
            objectOutputStream.writeObject(wagons);
            objectOutputStream.writeObject(routeTable);
            tracks.forEach(track -> { // change EMPTY_SIGNALs to null fow writing
                track.getBlockSections().forEach(blockSection -> {
                    if (blockSection.getNormalDirectionSignal() == Signal.EMPTY_SIGNAL) {
                        blockSection.setNormalDirectionSignal(null);
                    }
                    if (blockSection.getReversedDirectionSignal() == Signal.EMPTY_SIGNAL) {
                        blockSection.setReversedDirectionSignal(null);
                    }
                });
            });
            objectOutputStream.writeObject(tracks);
            tracks.forEach(track -> { // return EMPTY_SIGNALs back
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
            Path path = Paths.get(modelURL);
            if (path.toFile().exists()) {
                File modelFile = new File(modelURL);
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
                stationTracks.values().forEach(TracksideObject::addPropertyChangeSupport);
                locomotives = (Map<String, Locomotive>) inputStream.readObject();
                locomotives.values().forEach(TracksideObject::addPropertyChangeSupport);
                wagons = (Map<String, Wagon>) inputStream.readObject();
                routeTable = (CopyOnWriteArraySet<Route>) inputStream.readObject();
                tracks = (Set<Track>) inputStream.readObject();
                tags = (Map<Long, RFIDTag>) inputStream.readObject();
                trackSections.values().forEach(trackSection -> trackSection.setVacancyState(TrackSectionState.UNDEFINED));
                signals.values().forEach(signal -> signal.setSignalState(SignalState.UNDEFINED));
                switches.values().forEach(aSwitch -> aSwitch.setSwitchState(SwitchState.UNDEFINED));
                stationTracks.values().forEach(track -> track.setVacancyState(TrackSectionState.UNDEFINED));


                tracks.forEach(track -> {
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
        tracks.forEach(track -> {
            track.getBlockSections().forEach(blockSection -> {
                Signal normalDirectionSignal = blockSection.getNormalDirectionSignal();
                if (normalDirectionSignal != null && normalDirectionSignal != Signal.EMPTY_SIGNAL) {
                    normalDirectionSignal.setSignalState(SignalState.UNDEFINED);
                }
                Signal reversedDirectionSignal = blockSection.getReversedDirectionSignal();
                if (reversedDirectionSignal != null && reversedDirectionSignal != Signal.EMPTY_SIGNAL) {
                    reversedDirectionSignal.setSignalState(SignalState.UNDEFINED);
                }
                if (blockSection != TrackBlockSection.EMPTY_BLOCK_SECTION) {
                    blockSection.setVacancyState(TrackSectionState.UNDEFINED);
                }
            });
        });

        Model.getSignals().values().forEach(signal -> signal.setSignalState(SignalState.UNDEFINED));
        Model.getTrackSections().values().forEach(trackSection -> trackSection.setVacancyState(TrackSectionState.UNDEFINED));
        Model.getStationTracks().values().forEach(stationTrack -> stationTrack.setVacancyState(TrackSectionState.UNDEFINED));
    }
}
