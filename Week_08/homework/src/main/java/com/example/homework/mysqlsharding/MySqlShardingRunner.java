package com.example.homework.mysqlsharding;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
@Slf4j
public class MySqlShardingRunner implements CommandLineRunner {

    @Resource
    private DataSource dataSource;
    
    
    @Override
    public void run(String... args) throws Exception {
        homework3();
    }

    /**
     * 2.（必做）设计对前面的订单表数据进行水平分库分表，拆分 2 个库，每个库 16 张表。并在新结构在演示常见的增删改查操作。代码、sql 和配置文件，上传到 Github。
     */
    private void homework2() throws SQLException {
        Connection connection = dataSource.getConnection();
        log.info(connection.toString());
        //查询
        String querySql = "select * from t_order";
        PreparedStatement queryPs = connection.prepareStatement(querySql);
        ResultSet resultSet = queryPs.executeQuery();
        while (resultSet.next()) {
            log.info(resultSet.getLong(1) + ":" + resultSet.getInt(2) + ":" + resultSet.getInt(3)
                    + ":" + String.valueOf(resultSet.getBigDecimal(4)) + ":" + resultSet.getInt(5)
                    + ":" + resultSet.getDate(6) + ":" + resultSet.getDate(7));
        }

        //删除
        String deleteSql = "delete from t_order where id = 8";

        PreparedStatement deletePs = connection.prepareStatement(deleteSql);
        deletePs.executeUpdate();

        //插入
        String insertSql = "insert into t_order(id,user_id,commodity_id,commodity_price,state,create_time,modify_time) values (?,?,?,?,?,?,?)";

        PreparedStatement insertPs = connection.prepareStatement(insertSql);

        insertPs.setLong(1, 8L);
        insertPs.setInt(2, 3);
        insertPs.setInt(3,8);
        insertPs.setBigDecimal(4, BigDecimal.TEN);
        insertPs.setInt(5,0);
        insertPs.setDate(6,new Date(System.currentTimeMillis()));
        insertPs.setDate(7,new Date(System.currentTimeMillis()));

        insertPs.executeUpdate();

        //修改
        String updateSql = "update t_order set state = ? where id = ?";
        PreparedStatement updatePs = connection.prepareStatement(updateSql);
        updatePs.setInt(1, 1);
        updatePs.setLong(2, 8L);
        updatePs.executeUpdate();
        
    }

    /**
     * 3.（选做）模拟 1000 万的订单单表数据，迁移到上面作业 2 的分库分表中。
     */
    private void homework3() {
        long l = System.currentTimeMillis();
        ExecutorService executorService = Executors.newFixedThreadPool(16);
        for (int i = 0; i < 100; i++) {
            executorService.submit(this::saveData);
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
        String insertSQL = "insert into t_order (user_id,commodity_id,commodity_price,state,create_time,modify_time) values (?, ?, ?, ?, ?, ?)";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(insertSQL)) {
            for (int i = 0; i < 10000; i++) {
                preparedStatement.setInt(1, (i + 1));
                preparedStatement.setInt(2, (i + 1));
                preparedStatement.setBigDecimal(3, BigDecimal.TEN);
                preparedStatement.setInt(4, 0);
                preparedStatement.setDate(5, new Date(System.currentTimeMillis()));
                preparedStatement.setDate(6, new Date(System.currentTimeMillis()));
                preparedStatement.addBatch();
            }
            preparedStatement.executeBatch();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void showConnection() throws SQLException {
        log.info(dataSource.toString());
        Connection connection = dataSource.getConnection();
        log.info(connection.toString());
    }
}
