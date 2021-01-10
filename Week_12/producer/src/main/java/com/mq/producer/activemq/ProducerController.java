package com.mq.producer.activemq;

import com.mq.homework.activemq.ActivemqFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTextMessage;
import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import javax.jms.JMSException;
import javax.jms.MessageProducer;

@Controller
public class ProducerController implements CommandLineRunner {
    
    @Resource
    private ActivemqFactory activemqFactory;
    
    @Override
    public void run(String... args) throws Exception {
        //创建queue
        MessageProducer producer = activemqFactory.getProducer(new ActiveMQQueue("wp.queue"));
        //创建topic
//        MessageProducer producer = activemqFactory.getProducer(new ActiveMQTopic("wp.topic"));
        try {
            for (int i = 0; i < 100; i++) {
                ActiveMQTextMessage activeMQTextMessage = new ActiveMQTextMessage();
                activeMQTextMessage.setText("sss " + i);
                producer.send(activeMQTextMessage);
            }
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

}
