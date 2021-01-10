package com.mq.consumer.mysql;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Controller;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Controller
@Slf4j
public class MysqlConsumerController implements CommandLineRunner {

    @Autowired
    DataSource dataSource;
    
    @Override
    public void run(String... args) throws Exception {
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
        scheduledExecutorService.scheduleAtFixedRate(this::consumerOrder, 100, 100,
                TimeUnit.MILLISECONDS);
    }
    
    private void consumerOrder() {
        String selectSql = "select id,user_id,commodity_id,commodity_price,state,create_time,modify_time from mq_order where state = ?";

        String updateSql = "update mq_order set state = 1 where id = ? and state = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement selectStatement = connection.prepareStatement(selectSql);
             PreparedStatement updateStatement = connection.prepareStatement(updateSql)
        ){
            selectStatement.setInt(1, 0);
            boolean execute = selectStatement.execute();
            ResultSet resultSet = selectStatement.getResultSet();

            List<Map<String, Object>> list = new ArrayList<>();
            while (resultSet.next()) {
                Map<String, Object> data = new HashMap<>();
                data.put("id", resultSet.getLong(1));
                data.put("userId", resultSet.getLong(2));
                data.put("commodityId", resultSet.getLong(3));
                data.put("commodityPrice", resultSet.getBigDecimal(4));
                data.put("state", resultSet.getInt(5));
                data.put("createTime", resultSet.getDate(6));
                data.put("modifyTime", resultSet.getDate(7));
                list.add(data);
            }

            System.out.println(list);
            //依次修改其状态为1
            for (Map<String, Object> up : list) {
                log.info("order " + up);
                updateStatement.setLong(1, (long)up.get("id"));
                updateStatement.setInt(2, 0);
                int i = updateStatement.executeUpdate();
                if (i > 0) {
                    log.info("modify order :" + up.get("id"));
                }
            }

        } catch (Exception e) {
            log.error("consumer order fail ");
        }
    }
}
