package NATrain.сontrolModules;

import NATrain.trackSideObjects.TracksideObject;
import NATrain.trackSideObjects.signals.Signal;

import java.util.Map;
import java.util.TreeSet;

public interface ControlModule {

    String getId();

    ControlModuleType getModuleType();

    Map<Integer, InputChannel> getInputChannels();

    Map<Integer, OutputChannel> getOutputChannels();

    void sendCommand(int channelNumber, String command);

    void globalRequest();

    default int getInputsCount() {
        return getInputChannels().size();
    }

    default int getOutputsCount() {
        return getOutputChannels().size();
    }

    default String getObjectNames() {
        TreeSet<String> trackSectionNames = new TreeSet<>();
        TreeSet<String> signalNames = new TreeSet<>();
        TreeSet<String> switchNames = new TreeSet<>();
        getInputChannels().values().forEach(inputChannel -> {
            switch (inputChannel.getChannelType()) {
                case SWITCH_MINUS:
                case SWITCH_PLUS:
                    switchNames.add(inputChannel.getTracksideObject().getId());
                    break;
                case TRACK_SECTION:
                    trackSectionNames.add(inputChannel.getTracksideObject().getId());
                    break;
            }
        });
        getOutputChannels().values().forEach(outputChannel -> {
            switch (outputChannel.getChannelType()) {
                case SWITCH_TO_PLUS:
                case SWITCH_TO_MINUS:
                    switchNames.add(outputChannel.getTracksideObject().getId());
                    break;
                case SIGNAL_LAMP_OUTPUT:
                    signalNames.add(outputChannel.getTracksideObject().getId());
                    break;
            }
        });
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Sections: ");
        trackSectionNames.forEach(name -> stringBuilder.append(name).append("; "));
        stringBuilder.append("Signals: ");
        signalNames.forEach(name -> stringBuilder.append(name).append("; "));
        stringBuilder.append("Switches: ");
        switchNames.forEach(name -> stringBuilder.append(name).append("; "));
        return stringBuilder.toString();
    }

    default String getConfiguredChannels(TracksideObject tracksideObject) {
        StringBuilder resultString = new StringBuilder();
        if (!(tracksideObject instanceof Signal)) {
            resultString.append(" IN: ");
            getInputChannels().values().forEach(inputChannel -> {
                if (inputChannel.getTracksideObject() == tracksideObject) {
                    resultString.append(inputChannel.getChNumber());
                    resultString.append("; ");
                }
            });
        }
        resultString.append(" OUT: ");
        getOutputChannels().values().forEach(outputChannel -> {
            if (outputChannel.getTracksideObject() == tracksideObject) {
                resultString.append(outputChannel.getChNumber());
                resultString.append("; ");
            }
        });
        resultString.append(" ");
        return resultString.toString();
    }
}
