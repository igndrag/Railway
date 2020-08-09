package NATrain.remoteControlDevice;

public abstract class AbstractModule implements ControlModule{
    protected Integer address;

    @Override
    public Integer getAddress() {
        return address;
    }

    public AbstractModule(int address) {
        this.address = address;
    }
}
