package NATrain.connectionService;

import NATrain.model.Model;
import NATrain.trackSideObjects.TrackSectionState;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

public class MQTTConnectionService {
    static String broker = "tcp://localhost:1883";
    static String topicName = "test";
    static int qos = 1;
    static String clientID = "NATrainsApp";
    static MqttClient mqttClient;
    static MemoryPersistence persistence = new MemoryPersistence();

    private static class MyMqttCallback implements MqttCallback {

        @Override
        public void connectionLost(Throwable throwable) {
            connect();
        }

        @Override
        public void messageArrived(String topic, MqttMessage message) throws Exception {
            System.out.println("message is : " + message);
            if (message.toString().equals("Occupied")) {
                Model.getTrackSections().get("SP").setVacancyState(TrackSectionState.OCCUPIED);
            }
            if (message.toString().equals("Free")) {
                Model.getTrackSections().get("SP").setVacancyState(TrackSectionState.FREE);
            }
        }

        @Override
        public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {

        }


    }

    public static void main(String[] args) {
        connect();
        publish("test", "Java MQTTClient started");
        subscribe("TrackSectionModule", new MyMqttCallback());
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

    public static void connect() {
        try {
            mqttClient = new MqttClient(broker, clientID, persistence);
            MqttConnectOptions connOpts = new MqttConnectOptions();
            connOpts.setCleanSession(true); //no persistent session
            connOpts.setKeepAliveInterval(1000);
            mqttClient.connect(connOpts); //connects the broker with connect options
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }
}

