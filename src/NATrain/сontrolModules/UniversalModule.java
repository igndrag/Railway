package NATrain.сontrolModules;

import java.util.concurrent.CopyOnWriteArrayList;

public class UniversalModule extends AbstractModule {

    public UniversalModule(String id) {
        super(id);
        this.inputChannels = new InputChannel[8];
        this.outputChannels = new OutputChannel[8];
    }

    @Override
    public void setChannelStatus(int ch, int statusCode) {

    }

    @Override
    public void sendChannelStatusResponse(int ch) {

    }

    @Override
    public void globalResponse() {

    }
}
