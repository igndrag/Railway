package NATrain.connectionService;

import NATrain.trackSideObjects.RFIDTag;
import arduino.Arduino;
import com.fazecast.jSerialComm.SerialPort;

import java.io.*;
import java.util.Stack;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.locks.ReentrantLock;

public class ConnectionTester extends Thread {

    private static final int commandBufferSize = 10;

    private Arduino arduino;
    private static BufferedReader reader;
    private static BufferedWriter writer;
    private Stack<String> commandBuffer;
    boolean closed = false;

    public ConnectionTester(String portName) {
        super(portName + " connectionService");
        this.arduino = new Arduino(portName, 9600);
    }

    public boolean openConnection() {
        boolean connected = arduino.openConnection();
        System.out.println("Соединение установлено: " + connected);
        try {
            sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (connected) {
            SerialPort serialPort = arduino.getSerialPort();
            serialPort.setComPortTimeouts(100, 100, 100);
            InputStream inputStream = serialPort.getInputStream();
            reader = new BufferedReader(new InputStreamReader(inputStream));
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
            openConnection();
            while (!closed) {
              /*  if (RequestExecutor.getRequestPool().isEmpty()) {
                    mockRequestData();
                }
               */
                if (reader.ready()) {
                    String message = reader.readLine();
                    System.out.println(message);
                    if (message.equals("READY")) {
                        sendCommand("SEND");
                        sleep(50);
                        System.out.println(reader.readLine());
                        System.out.println(reader.readLine());
                        sendCommand("10245");
                        sleep(50);
                        System.out.println(reader.readLine());
                       /* while (reader.ready()) {
                            System.out.println(reader.readLine());
                        }

                        */
                    }
                }
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void mockRequestData() {
        RequestExecutor.getRequestPool().add("123456");
        RequestExecutor.getRequestPool().add("123457");
        RequestExecutor.getRequestPool().add("123458");
    }

    public static void main(String[] args) {
     //   ConnectionTester connectionTester = new ConnectionTester("COM5");
     //   connectionTester.start();
     //  RFIDTag tag = new RFIDTag("FF", "FF", "FF", "FF");
     //   System.out.println(tag.getDecUid());
    }
}
