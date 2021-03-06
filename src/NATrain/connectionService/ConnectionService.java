package NATrain.connectionService;

import arduino.Arduino;
import com.fazecast.jSerialComm.SerialPort;
import com.sun.deploy.util.StringUtils;
import com.sun.xml.internal.ws.util.StreamUtils;

import java.io.*;
import java.util.Stack;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.locks.ReentrantLock;

public class ConnectionService extends Thread {

    private static final int commandBufferSize = 10;

    private Arduino arduino;
    private BufferedReader reader;
    private BufferedWriter writer;
    private Stack<String> commandBuffer;
    boolean closed = false;

    public ConnectionService(String portName) {
        super(portName + " connectionService");
        this.arduino = new Arduino(portName, 9600);
        this.setDaemon(true);
    }

    public boolean openConnection() {
        boolean connected = arduino.openConnection();
        System.out.println("Соединение установлено: " + connected);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (connected) {
            SerialPort serialPort = arduino.getSerialPort();
            InputStream inputStream = serialPort.getInputStream();
            this.reader = new BufferedReader(new InputStreamReader(inputStream));
        }
        return connected;
    }

    public void sendCommand(String command) {
        arduino.serialWrite(command + '\n');
    }

    public void closeConnection() {
        closed = true;
        arduino.closeConnection();
        try {
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void run() {
        try {
            ReentrantLock lock = new ReentrantLock();
            Timer timeout = new Timer();
            TimerTask timerTask = new TimerTask() {
                @Override
                public void run() {
                    lock.lock();
                }
            };
            openConnection();
            while (!closed) {
                if (reader.ready()) {
                    String message = reader.readLine();
                    System.out.println(message);
                    if (message.equals("READY")) {
                        int requestMessageSize = RequestExecutor.getRequestPool().size() % commandBufferSize;
                        sleep(50);
                        sendCommand("SEND");
                        sendCommand(Integer.toString(requestMessageSize));
                        //sleep(5);
                        for (int i = 0; i < requestMessageSize; i++) {
                            sendCommand(RequestExecutor.getRequestPool().poll());
                            //sleep(5);
                        }
                        sendCommand("LISTEN");
                        String listenResponse = reader.readLine();
                        try {
                            //int messageSize = Integer.parseInt(listenResponse); //TODO uncomment when hardware ready
                            int responseMessageSize = 0;
                            for (int i = 0; i < responseMessageSize; i++) {
                                RequestExecutor.getResponsePool().add(reader.readLine());
                            }
                        } catch (NumberFormatException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
