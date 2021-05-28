package NATrain.connectionService;

import NATrain.model.Model;
import NATrain.сontrolModules.ControlModule;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

public class MQTTConnectionService {
    private static MQTTConnectionService service;
    static String broker = "tcp://localhost:1883";
    static String topicName = "NATrain";
    static int qos = 1;
    static String clientID = "NATrainsApp";
    static MqttClient mqttClient;
    static MemoryPersistence persistence = new MemoryPersistence();

    public static MqttClient getClient() {
        return mqttClient;
    }

    public static MQTTConnectionService getService() {
        return service;
    }

    private static class MyMqttCallback implements MqttCallback {
        private ControlModule controlModule;

        public MyMqttCallback(ControlModule controlModule) {
            this.controlModule = controlModule;
        }

        @Override
        public void connectionLost(Throwable throwable) {
            connect();
        }

        @Override
        public void messageArrived(String topic, MqttMessage message) throws Exception {
           // RESPONSE INPUTNUMBER1:INPUTSTATUS1 INPUTNUMBER2:INPUTSTATUS2 ...
           String[] inputStatuses = message.toString().split(" ");
            for (String inputStatus : inputStatuses) {
                String[] statusParts = inputStatus.split(":");
                int portNumber = Integer.parseInt(statusParts[0]);
                if (statusParts[1].length() < 2) {
                    int inputStateCode = Integer.parseInt(statusParts[1]);
                    controlModule.getInputChannels().get(portNumber).setActualState(inputStateCode);
                } else {
                    long decUid = Long.parseLong(statusParts[1]);
                    controlModule.getInputChannels().get(portNumber).moveTag(decUid);
                }
                }
        }

        @Override
        public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {

        }
    }

    public static void subscribe(String topic, MqttCallback mqttCallback) {
        try {
            mqttClient.subscribe(topic); //subscribing to the topic name  test/topic
            mqttClient.setCallback(mqttCallback);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    public static void publish(String topic, String message) {
        MqttMessage mqttMessage = new MqttMessage(message.getBytes());
        mqttMessage.setQos(qos);     //sets qos level 1
        mqttMessage.setRetained(false); //sets retained message
        MqttTopic mqttTopic = mqttClient.getTopic(topic);
        try {
            mqttTopic.publish(mqttMessage);    // publishes the message to the topic(test/topic)
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    public static void disconnect() {
        try {
            mqttClient.disconnect();
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }
    public static void connect() {
        try {
            mqttClient = new MqttClient(broker, clientID, persistence);
            MqttConnectOptions connOpts = new MqttConnectOptions();
            connOpts.setCleanSession(true); //no persistent session
            connOpts.setKeepAliveInterval(1000);
            mqttClient.connect(connOpts); //connects the broker with connect options
            publish(topicName, "NATrainApp connected");
            Model.getControlModules().values().forEach(controlModule -> {
                String topicForSubscribe = topicName + "/controlModules/responses/" + controlModule.getId();
                subscribe(topicForSubscribe, new MyMqttCallback(controlModule));
            });
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }
}

