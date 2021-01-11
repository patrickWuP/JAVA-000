package com.mq.homework.activemq.mqtt;

import org.eclipse.paho.client.mqttv3.MqttException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import static com.mq.homework.activemq.mqtt.MqttConstant.MQTT_URL;
import static com.mq.homework.activemq.mqtt.MqttConstant.QOS_1;

public class CatWindow {
    
    private static final String CLIENT_ID = "cat";
    
    static final String TOPIC = CLIENT_ID + "_topic";

    public static void main(String[] args) throws MqttException {
        //create client 
        MyMqttClient myMqttClient = new MyMqttClient(MQTT_URL, CLIENT_ID);
        
        //订阅自己用于接收信息的topic
        myMqttClient.subscribe(TOPIC, QOS_1);
        
        //发送消息给对应的topic

        InputStreamReader inputStreamReader = new InputStreamReader(System.in);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        
        while (true) {
            String inputStr;

            try {
                if ((inputStr = bufferedReader.readLine()) != null) {
                    myMqttClient.publishMessage(inputStr, QOS_1, true, DogWindow.TOPIC);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        
    }

    
}
