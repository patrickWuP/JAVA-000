package com.mq.homework.activemq.mqtt;

public interface MqttConstant {
    
    String MQTT_URL = "tcp://47.105.111.86:1883";
    
    int WAIT_TIME = 500;
    /**
     * QOS0： “至多一次”，消息发布完全依赖底层 TCP/IP 网络。会发生消息丢失或重复。这一级别可用于如下情况，环境传感器数据，丢失一次读记录无所谓，因为不久后还会有第二次发送。
     *
     * QOS1： “至少一次”，确保消息到达，但消息重复可能会发生。
     *
     * QOS2： “只有一次”，确保消息到达一次。这一级别可用于如下情况，在计费系统中，消息重复或丢失会导致不正确的结果。
     */
    int QOS_0 = 0;
    int QOS_1 = 0;
    int QOS_2 = 0;
}
