package NATrain.controller;


import NATrain.connectionService.ConnectionService;
import NATrain.model.MainModel;
import NATrain.remoteControlDevice.ControlModule;
import NATrain.view.View;

public class Controller {
    ConnectionService connectionService;
    MainModel mainModel;
    View view;

    public void setConnectionService(ConnectionService connectionService) {
        this.connectionService = connectionService;
    }

    public void setView(View view) {
        this.view = view;
    }

    public void setMainModel(MainModel mainModel) {
        this.mainModel = mainModel;
    }

    public void sendGlobalRequest () {

    }

    public ControlModule getModuleByAddress (int address) {
        return null;
    }


}
