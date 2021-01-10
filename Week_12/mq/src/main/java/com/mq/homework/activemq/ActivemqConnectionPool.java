package com.mq.homework.activemq;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import javax.jms.Connection;
import javax.jms.JMSException;

@Component
@PropertySource("classpath:mq.properties")
public class ActivemqConnectionPool {

    @Value("${active.mq.url}")
    private String activeMqUrl;
    
    @Bean
    private ActiveMQConnectionFactory createFactory() {
        return new ActiveMQConnectionFactory(activeMqUrl);
    }

    @Autowired
    ActiveMQConnectionFactory factory;
    
    public Connection getConnection() {
        try {
            Connection connection = factory.createConnection();
            connection.start();
            return connection;
        } catch (JMSException e) {
            e.printStackTrace();
        }
        return null;
    }
    
}
