package com.mq.homework.activemq.mqtt;

import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import static com.mq.homework.activemq.mqtt.MqttConstant.WAIT_TIME;

public class MyMqttClient {
    
    private MqttAsyncClient mqttAsyncClient;
    
    public MyMqttClient(String Url, String ClientId) throws MqttException { 
        mqttAsyncClient = new MqttAsyncClient(Url, ClientId, new MemoryPersistence());
        mqttAsyncClient.setCallback(new MqttCallbackImpl());
        
        IMqttToken connect = mqttAsyncClient.connect();
        connect.waitForCompletion(WAIT_TIME);
    }
    
    public void subscribe(String topic, int qos) throws MqttException {
        IMqttToken subscribe = mqttAsyncClient.subscribe(topic, qos);
        subscribe.waitForCompletion(WAIT_TIME);
    }
    
    public void publishMessage(String message, int qos, boolean retain, String topic) throws MqttException {
        MqttMessage mqttMessage = new MqttMessage(message.getBytes());
        mqttMessage.setQos(qos);
        mqttMessage.setRetained(retain);
        IMqttDeliveryToken publish = mqttAsyncClient.publish(topic, mqttMessage);
        publish.waitForCompletion(WAIT_TIME);
    }
    
    public void disconnectClient() throws MqttException {
        IMqttToken disconnect = mqttAsyncClient.disconnect();
        disconnect.waitForCompletion(WAIT_TIME);
    }
    
    public void closeClient() throws MqttException {
        mqttAsyncClient.close();
    }
}
