package com.mq.homework.activemq;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.jms.*;

@Component
public class ActivemqFactory implements CommandLineRunner {
    
    @Autowired
    private ActivemqConnectionPool activemqConnectionPool;
    
    public MessageConsumer getConsumer(Destination destination) {
        try {
            Connection connection = activemqConnectionPool.getConnection();
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            return session.createConsumer(destination);
        } catch (JMSException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public MessageProducer getProducer(Destination destination) {
        try {
            Connection connection = activemqConnectionPool.getConnection();
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            return session.createProducer(destination);
        } catch (JMSException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void run(String... args) throws Exception {
        System.out.println("ActivemqFactory completed");
    }
}
