package NATrain.remoteControlModules;

import NATrain.connectionService.RequestExecutor;
import NATrain.trackSideObjects.TracksideObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractModule implements ControlModule, Serializable {
    static final long serialVersionUID = 1L;

    protected String IPAddress;

    protected Integer address;
    protected TracksideObject[] channels;

    protected static final int UNDEFINED_RESPONSE_STATUS_CODE = 0;
    protected static final int NOT_CHANGED_RESPONSE_STATUS_CODE = 1;
    protected static final int WRONG_REQUEST_RESPONSE_STATUS_CODE = 2;
    protected static final int FAIL_OR_TIMEOUT_RESPONSE_STATUS_CODE = 3;


    public AbstractModule(){};

    public TracksideObject[] getChannels() {
        return channels;
    }

    public String getIPAddress() {
        return IPAddress;
    }

    public void setIPAddress(String IPAddress) {
        this.IPAddress = IPAddress;
    }

    @Override
    public Integer getAddress() {
        return address;
    }

    @Override
    public void setTrackSideObjectOnChannel(TracksideObject trackSideObject, int channel) {
        try {
            channels[channel] = trackSideObject;
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void deleteTrackSideObjectFromChannel(Integer channel) {
        if (channel != null)
        channels[channel] = null;
    }

    @Override
    public void sendCommand(int channel, Command command) {
        String commandString = String.format("%02d%02d%02d", command.getCode(), address, channel);
        System.out.println(commandString + " was added to requestPool");
        RequestExecutor.getRequestPool().add(commandString);
    }

    public AbstractModule(int address) {
        this.address = address;
    }

    public boolean hasNotConfiguredChannels() {
        for (TracksideObject channel : channels) {
            if (channel == null) return true;
        }
        return false;
    }

    public List<Integer> getNotConfiguredChannels() {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < channels.length; i++) {
            if (channels[i] == null)
                list.add(i);
        }
        return list;
    }
}
