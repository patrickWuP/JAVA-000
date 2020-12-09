package com.example.homework.mysqlsharding;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.*;

@Component
@Slf4j
public class MySqlShardingRunner implements CommandLineRunner {

    @Resource
    private DataSource dataSource;
    
    
    @Override
    public void run(String... args) throws Exception {
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

    private void showConnection() throws SQLException {
        log.info(dataSource.toString());
        Connection connection = dataSource.getConnection();
        log.info(connection.toString());
    }
}
