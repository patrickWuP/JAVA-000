package com.example.demo.start;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
@Slf4j
public class HikariSaveRunner implements CommandLineRunner {

    @Resource
    private DataSource dataSource;
    
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    @Override
    public void run(String... args) throws Exception {
        log.info("begin to insert data...");
        long l = System.currentTimeMillis();
//        showConnection();
        ExecutorService executorService = Executors.newFixedThreadPool(16);
        for (int i = 0; i < 100; i++) {
            executorService.submit(() -> {
                saveData();
            });
        }
        executorService.shutdown();
        while(true){
            if(executorService.isTerminated()){
                break;
            }
        }
        
        log.info("execution time " + (l - System.currentTimeMillis()));
    }
    
    private void saveData() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = dataSource.getConnection();
            StringBuilder sql = new StringBuilder("insert into `order` (user_id,commodity_id,commodity_price,state,create_time,modify_time) values ");
            for (int i = 0; i < 10000; i++) {
                if (i != 0) {
                    sql.append(",");
                }
                sql.append("(")
                        .append(i + 1)
                        .append(",").append(i + 1)
                        .append(",").append(BigDecimal.TEN)
                        .append("," + 0)
                        .append(",'2020-01-01 22:00:00'")
                        .append(",'2020-01-01 22:00:00'")
                        .append(")");
            }
            PreparedStatement preparedStatement1 = connection.prepareStatement(sql.toString());
            /*String insertSQL = "insert into `order` (user_id,commodity_id,commodity_price,state,create_time,modify_time) values (?, ?, ?, ?, ?, ?)";
            preparedStatement = connection.prepareStatement(insertSQL);
            for (int i = 0; i < 10000; i++) {
                preparedStatement.setInt(1, (i + 1));
                preparedStatement.setInt(2, (i + 1));
                preparedStatement.setBigDecimal(3, BigDecimal.TEN);
                preparedStatement.setInt(4, 0);
                preparedStatement.setDate(5, new Date(System.currentTimeMillis()));
                preparedStatement.setDate(6, new Date(System.currentTimeMillis()));
                preparedStatement.addBatch();
            }
            preparedStatement.executeBatch();*/
            preparedStatement1.execute();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    private void showConnection() throws SQLException {
        log.info(dataSource.toString());
        Connection connection = dataSource.getConnection();
        log.info(connection.toString());
    }
}
