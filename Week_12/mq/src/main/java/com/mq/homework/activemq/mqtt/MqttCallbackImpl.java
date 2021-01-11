package com.mq.homework.activemq.mqtt;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class MqttCallbackImpl implements MqttCallback {

    @Override
    public void connectionLost(Throwable cause) {
        
    }

    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        String messageContent = new String(message.getPayload());
        System.out.println("topic: " + topic + " from: " + message.getId() + " content: " + messageContent);
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {

    }
}
