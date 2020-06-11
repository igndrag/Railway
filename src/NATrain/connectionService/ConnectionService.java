package NATrain.connectionService;

import arduino.Arduino;
import com.fazecast.jSerialComm.SerialPort;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Stack;

public class ConnectionService extends Thread{
        private Arduino arduino;
        private BufferedReader reader;
        private Stack<String> commandBuffer;
    public ConnectionService(String portName) {
        super(portName + " connectionService");
        this.arduino = new Arduino(portName, 9600);
        this.commandBuffer = new Stack<>();
        this.setDaemon(true);
    }

        public void openConnection () {
        boolean connected = arduino.openConnection();
        System.out.println("Соединение установлено: " + connected);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        SerialPort serialPort = arduino.getSerialPort();
        InputStream inputStream = serialPort.getInputStream();
        this.reader = new BufferedReader(new InputStreamReader(inputStream));
    }

    public void sendCommand (String command) {
        arduino.serialWrite(command);
    }

        public void closeConnection () {
            arduino.closeConnection();
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    public void run() {
        Boolean closed = false;
        openConnection();
        try {
            while (!closed) {
                if (reader.ready()) {
                    String message = reader.readLine();
                    if (message.equals("close")) {
                        closed = true;
                        closeConnection();
                    } else {
                        commandBuffer.add(message);
                        System.out.println(message);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Stack<String> getCommandBuffer() {
        return commandBuffer;
    }
}
