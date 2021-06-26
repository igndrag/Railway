package NATrain.UI.tracksideObjectRedactor.TSORedactors;

import NATrain.UI.AppConfigController;
import NATrain.trackSideObjects.TagType;
import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortDataListener;
import com.fazecast.jSerialComm.SerialPortEvent;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class TagReaderController {

    @FXML
    private Button closeButton;

    private TagType tagType;

    private SerialReader reader;

    private LocomotiveRedactorController controller;

    public void init(LocomotiveRedactorController controller, TagType tagType) {
        this.controller = controller;
        this.tagType = tagType;

        closeButton.setOnAction(event -> {
            closeReader();
        });
        this.reader = new SerialReader();
        reader.start();
    }

    protected void closeReader() {
        reader.interrupt();
        ((Stage) closeButton.getScene().getWindow()).close();
    }

    public class SerialReader extends Thread {

        SerialPort serialPort;

        public SerialReader() {
            super("TagReader");
            serialPort = SerialPort.getCommPort("COM" + AppConfigController.comPortNumber);
            //serialPort = SerialPort.getCommPort("COM5");
            serialPort.openPort();
            serialPort.setComPortTimeouts(SerialPort.TIMEOUT_READ_BLOCKING, 0, 0);
            System.out.println("Connected to " + serialPort.getDescriptivePortName());
        }

        @Override
        public void run() {
            super.run();
            serialPort.addDataListener(new SerialPortDataListener() {
                @Override
                public int getListeningEvents() {
                    return SerialPort.LISTENING_EVENT_DATA_AVAILABLE;
                }

                @Override
                public void serialEvent(SerialPortEvent event) {
                    if (event.getEventType() != SerialPort.LISTENING_EVENT_DATA_AVAILABLE)
                        return;
                    try {
                        Thread.sleep(50);
                        byte[] newData = new byte[serialPort.bytesAvailable()];
                        int numRead = serialPort.readBytes(newData, newData.length);
                        String uid = new String(newData).toUpperCase();
                        //System.out.println(uid);
                        if (uid.length() > 8) {
                            Platform.runLater(() -> {
                                switch (tagType) {
                                    case FRONT_TAG:
                                        controller.setFrontUid(uid);
                                        break;
                                    case REAR_TAG:
                                        controller.setRearUid(uid);
                                        break;
                                }
                                closeReader();
                            });
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }

        @Override
        public void interrupt() {
            serialPort.closePort();
            super.interrupt();
        }
    }
}