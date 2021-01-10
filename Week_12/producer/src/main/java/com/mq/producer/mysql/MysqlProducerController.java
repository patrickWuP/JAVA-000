package com.mq.producer.mysql;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Controller;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;

@Controller
@Slf4j
public class MysqlProducerController implements CommandLineRunner {

    @Autowired
    private DataSource dataSource;
    
    @Override
    public void run(String... args) throws Exception {
        //往数据库里生产消息
        String insertSql = "insert into mq_order(user_id,commodity_id,commodity_price,state) values (?,?,?,?)";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(insertSql)) {
            
            while (true) {
                preparedStatement.setLong(1, 23);
                preparedStatement.setLong(2, 88);
                preparedStatement.setBigDecimal(3, new BigDecimal(10.0));
                preparedStatement.setInt(4, 0);

                preparedStatement.execute();
                Thread.sleep(30000);
            }
        } catch (Exception e) {
            log.error("produce data fail ", e);
        }
        
    }
}
