package NATrain.сontrolModules;

import javafx.collections.ObservableList;

import java.io.Serializable;
import java.util.List;
import java.util.Observable;
import java.util.concurrent.CopyOnWriteArrayList;

public abstract class AbstractModule implements ControlModule, Serializable {
    static final long serialVersionUID = 1L;
    
    protected ControlModuleType moduleType;
    protected String id;
    protected List<InputChannel> inputChannels;
    protected List<OutputChannel> outputChannels;
    protected static final String GLOBAL_REQUEST_COMMAND_CODE = "10";

    public AbstractModule(String id) {
        this.id = id;
    }

    @Override
    public List<InputChannel> getInputChannels() {
        return inputChannels;
    }

    @Override
    public List<OutputChannel> getOutputChannels() {
        return outputChannels;
    }

    @Override
    public String getId() {
        return id;
    }
}
