package NATrain.сontrolModules;

import java.util.concurrent.CopyOnWriteArrayList;

public abstract class AbstractModule implements ControlModule {
    protected String id;
    protected InputChannel[] inputChannels;
    protected OutputChannel[] outputChannels;

    public AbstractModule(String id) {
        this.id = id;
    }

    @Override
    public InputChannel[] getInputChannels() {
        return inputChannels;
    }

    @Override
    public OutputChannel[] getOutputChannels() {
        return outputChannels;
    }

    @Override
    public String getId() {
        return id;
    }
}
