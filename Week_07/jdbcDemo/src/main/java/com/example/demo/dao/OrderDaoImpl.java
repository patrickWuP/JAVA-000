package com.example.demo.dao;

import com.example.demo.domain.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Component
public class OrderDaoImpl implements OrderDao {
    
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void batchSave(List<Order> list) {
        String sql = "insert into `order` (id,user_id,commodity_id,commodity_price,state,create_time,modify_time) values (?, ?, ?, ?, ?, ?, ?)";
        
        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
                Order order = list.get(i);
                preparedStatement.setInt(1, order.getId());
                preparedStatement.setInt(2, order.getUserId());
                preparedStatement.setInt(3, order.getCommodityId());
                preparedStatement.setBigDecimal(4, order.getCommodityPrice());
                preparedStatement.setInt(5, order.getState());
                preparedStatement.setDate(6, new Date(order.getCreateTime().getTime()));
                preparedStatement.setDate(7, new Date(order.getModifyTime().getTime()));
            }

            @Override
            public int getBatchSize() {
                return list.size();
            }
        });
    }

    @Override
    public void save(Order order) {
        String sql = "insert into `order` values (?, ?, ?, ?, ?, ?, ?)";
        
        jdbcTemplate.update(sql, new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement preparedStatement) throws SQLException {
                preparedStatement.setInt(1, 1);
                preparedStatement.setInt(2, 2);
                preparedStatement.setInt(3, 3);
                preparedStatement.setBigDecimal(4, BigDecimal.TEN);
                preparedStatement.setInt(5, 0);
                preparedStatement.setDate(6, new Date(System.currentTimeMillis()));
                preparedStatement.setDate(7, new Date(System.currentTimeMillis()));
            }
        });
    }
}
