package NATrain.сontrolModules;

import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.concurrent.CopyOnWriteArrayList;

public abstract class AbstractModule implements ControlModule, Serializable {
    static final long serialVersionUID = 1L;
    
    protected ControlModuleType moduleType;
    protected String id;

    protected int inputChannelCount;
    protected int outputChannelCount;
    protected Map<Integer, InputChannel> inputChannels;
    protected Map<Integer, OutputChannel> outputChannels;
    protected static final String GLOBAL_REQUEST_COMMAND_CODE = "99";

    public AbstractModule(String id, int inputsCount, int outputsCount) {
        this.id = id;
        this.inputChannelCount = inputsCount;
        this.outputChannelCount = outputsCount;
    }


    @Override
    public int getInputsCount() {
        return inputChannelCount;
    }

    @Override
    public int getOutputsCount() {
        return outputChannelCount;
    }

    @Override
    public ControlModuleType getModuleType() {
        return moduleType;
    }

    @Override
    public Map<Integer, InputChannel> getInputChannels() {
        return inputChannels;
    }

    @Override
    public Map<Integer, OutputChannel> getOutputChannels() {
        return outputChannels;
    }

    @Override
    public String getId() {
        return id;
    }
}
