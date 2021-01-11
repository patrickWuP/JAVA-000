package com.mq.homework.activemq.mqtt;

import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class TestPublish {

    static volatile boolean keepRunning = true;
    static MqttAsyncClient v3Client = null;
    static final Thread mainThread = Thread.currentThread();
    
    public static void main(String[] args) throws MqttException {
        // Create Client
        v3Client = new MqttAsyncClient("tcp://47.105.111.86:1883", "clientwp", new MemoryPersistence());
        v3Client.setCallback(new MqttCallbackImpl());

        // Connect to Server
        logMessage(String.format("Connecting to MQTT Broker: %s, Client ID: %s", v3Client.getServerURI(),
                v3Client.getClientId()), true);
//        MqttConnectOptions options = new MqttConnectOptions();
        IMqttToken connectToken = v3Client.connect();
        connectToken.waitForCompletion(5000);

        // Subscribe to a topic
        logMessage(String.format("Subscribing to %s, with QoS %d", "clientwp_read", 1), true);
        IMqttToken subToken = v3Client.subscribe("clientwp_read", 1);
        subToken.waitForCompletion(5000);
        
        addShutdownHook();
        InputStreamReader isReader = new InputStreamReader(System.in);
        BufferedReader bufReader = new BufferedReader(isReader);

        while (keepRunning) {
            String inputStr = null;
            try {
                if ((inputStr = bufReader.readLine()) != null) {
                    publishMessage(inputStr.getBytes(), 1,
                            true, "clientzs_read");
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        disconnectClient();
        closeClientAndExit();
    }

    private static void logMessage(String message, boolean isDebug) {
        System.out.println(message);
    }

    public static void addShutdownHook() {
        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                keepRunning = false;
            }
        });
    }

    private static void publishMessage(byte[] payload, int qos, boolean retain, String topic)
            throws MqttPersistenceException, MqttException {
        MqttMessage v3Message = new MqttMessage(payload);
        v3Message.setQos(qos);
        v3Message.setRetained(retain);
        IMqttDeliveryToken deliveryToken = v3Client.publish(topic, v3Message);
        deliveryToken.waitForCompletion(5000);
    }

    private static void disconnectClient() throws MqttException {
        // Disconnect
        logMessage("Disconnecting from server.", true);
        IMqttToken disconnectToken = v3Client.disconnect();
        disconnectToken.waitForCompletion(5000);
    }

    private static void closeClientAndExit() {
        // Close the client
        logMessage("Closing Connection.", true);
        try {
            v3Client.close();
            logMessage("Client Closed.", true);
            System.exit(0);
            mainThread.join();
        } catch (MqttException | InterruptedException e) {
            // End the Application
            System.exit(1);
        }

    }
}
