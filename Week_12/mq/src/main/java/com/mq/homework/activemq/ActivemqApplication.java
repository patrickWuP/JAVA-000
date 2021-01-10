package com.mq.homework.activemq;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTextMessage;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import java.util.concurrent.atomic.AtomicInteger;

public class ActivemqApplication {

    public static void main(String[] args) {
        Destination activeMQTopic = new ActiveMQQueue("test.queue1");
//        Destination activeMQTopic = new ActiveMQTopic("test.topic1");
        
        testDestination(activeMQTopic);
    }
    
    private static void testDestination(Destination destination) {
        try {
            // 创建连接和会话
            ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory("tcp://47.105.111.86:61616");
//            ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory("mqtt://47.105.111.86:1883");
            Connection connection = factory.createConnection();
            connection.start();
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            //创建消费者
            MessageConsumer consumer1 = session.createConsumer(destination);
//            MessageConsumer consumer2 = session.createConsumer(destination);
            
            final AtomicInteger count = new AtomicInteger(0);

            /*MessageListener messageListener = new MessageListener() {
                @Override
                public void onMessage(Message message) {
                    System.out.println(count.incrementAndGet() + " => receive from " + destination.toString() + ": " + message);
                }
            };*/
            //推送获取消息，两者不可同时配置
            /*consumer1.setMessageListener((message) -> 
                System.out.println(count.incrementAndGet() + " consumer1 => receive from " + destination.toString() + ": " + message)
            );*/
            
            /*consumer2.setMessageListener((message) ->
                    System.out.println(count.incrementAndGet() + " consumer2 => receive from " + destination.toString() + ": " + message)
            );*/
            
//            consumer1.receive();
            //创建生产者，
            /*MessageProducer producer = session.createProducer(destination);
            int index = 0;
            while (index++ < 100) {
                TextMessage textMessage = session.createTextMessage(index + " message.");
                producer.send(textMessage);
            }*/

            //拉取消息方式
            int num = 0;
            while (num++ < 100) {
                ActiveMQTextMessage receive = (ActiveMQTextMessage)consumer1.receive();
                System.out.println("consumer1.receive =>" + receive.getText());
            }
            Thread.sleep(2000);
            session.close();
            connection.close();
            
        } catch (Exception e) {
            e.printStackTrace();
        } 
    }
    
}
