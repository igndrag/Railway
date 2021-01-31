package NATrain.сontrolModules;

import javafx.collections.ObservableMap;

import java.util.Map;
import java.util.TreeSet;

public interface ControlModule {

    public static final int LIGHT_OF_COMMAND_CODE = 10;
    public static final int LIGHT_ON_COMMAND_CODE = 20;
    public static final int BLINKING_ON_COMMAND_CODE = 20;

    String getId();
    ControlModuleType getModuleType();
    Map<Integer,InputChannel> getInputChannels();
    Map<Integer, OutputChannel> getOutputChannels();
    void sendCommand (int channelNumber, String command);
    void globalRequest ();

    default int getInputsCount() {
        return getInputChannels().size();
    }

    default int getOutputsCount() {
        return getOutputChannels().size();
    }

    default String getObjectNames () {
        TreeSet<String> names = new TreeSet<>();
        getInputChannels().values().forEach(inputChannel -> names.add(inputChannel.getTracksideObject().getId()));
        getOutputChannels().values().forEach(outputChannel -> names.add(outputChannel.getTracksideObject().getId()));
        StringBuilder stringBuilder = new StringBuilder();
        names.forEach(name -> stringBuilder.append(name).append("; "));
        return stringBuilder.substring(0, stringBuilder.length() - 3);
    }
}
