package com.mq.consumer.activemq;

import com.mq.homework.activemq.ActivemqFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Controller;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;

@Controller
public class ConsumerController implements MessageListener, CommandLineRunner {
    
    @Autowired
    private ActivemqFactory activemqFactory;

    /*@Value("${active.mq.url}")
    private String activeMqUrl;*/

    @Override
    public void onMessage(Message message) {
        System.out.println("ConsumerController => " + message);
    }

    @Override
    public void run(String... args) throws Exception {
        MessageConsumer consumer = activemqFactory.getConsumer(new ActiveMQQueue("wp.queue"));
//        MessageConsumer consumer = activemqFactory.getConsumer(new ActiveMQTopic("wp.topic"));
        try {
            consumer.setMessageListener(this);
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
