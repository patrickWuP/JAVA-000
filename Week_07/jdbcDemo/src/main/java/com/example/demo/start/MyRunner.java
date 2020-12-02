package com.example.demo.start;

import com.example.demo.dao.OrderDao;
import com.example.demo.domain.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Component
public class MyRunner implements CommandLineRunner {
    
    @Autowired
    OrderDao orderDao;
    
    private static ExecutorService executorService = Executors.newFixedThreadPool(10);
    
    @Override
    public void run(String... args) throws Exception {
        long start = System.currentTimeMillis();
        List<Future> list = new ArrayList<>();
        int sum = 100;
        //创建有十个线程的线程池，一个线程处理10000条记录
        for (int i = 0; i < 100; i++) {
            final int begin = (i * 10000) + 1;
            final int end = (i + 1) * 10000;
            Runnable runnable = () -> {
//                System.out.println("begin:" + begin + "end:" + end);
                List<Order> orders = createOrders(begin, end);
                orderDao.batchSave(orders);
            };
            Future<?> submit = executorService.submit(runnable);
            list.add(submit);
        }
        for (Future task : list) {
            try {
                task.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
        System.out.println("耗时：" + (System.currentTimeMillis() - start));
    }

    private List<Order> createOrders(int start, int end) {
        List<Order> list = new ArrayList<>();

        for (int i = start; i < end; i++) {
            Order order = new Order();
            order.setId(i);
            order.setUserId(1 + (int) (Math.random()*10000));
            order.setCommodityId(1 + (int) (Math.random()*2000));
            order.setCommodityPrice(BigDecimal.valueOf ((long) (Math.random()*10), 2));
            order.setState(0);
            Date date = randDate();
            order.setCreateTime(date);
            order.setModifyTime(date);
            list.add(order);
        }

        return list;
    }

    private Date randDate() {
        Calendar instance = Calendar.getInstance();
        instance.set(2020, 0, 1);
        instance.add(Calendar.DATE, (int) (Math.random() * 365));
        return instance.getTime();
    }
}
