package com.wp.homework;

import java.util.concurrent.*;

/**
 * 思考有多少种方式，在 main 函数启动一个新线程，运行一个方法，拿到这个方法的返回值后，退出主线程？
 */
public class HomeWork7 {
    
    static Object result = null;
    
    public static void main(String[] args) throws Exception {
        //1.外部变量 + Runnable实现
        /*Runnable task = new Runnable() {
            @Override
            public void run() {
                result = "I am first way";
            }
        };
        Thread thread = new Thread(task);
        thread.start();
        
        thread.join();

        System.out.println("结果是: " + result);*/
        
        //2.使用callable + FutureTask + Thread实现创建新线程并获取返回值
        /*Callable callable = new Callable() {
            @Override
            public Object call() throws Exception {
                Thread.sleep(1000);
                return "I am second way";
            }
        };
        FutureTask futureTask = new FutureTask(callable);
        Thread thread = new Thread(futureTask);
        thread.start();
        System.out.println("Main Thread");
        Object o = futureTask.get();
        System.out.println("结果是: " + o);*/

        ExecutorService executorService = Executors.newSingleThreadExecutor();
        //3.线程池 + Runnable
        /*Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                result = "I am third way";
            }
        };
        Future<?> submit = executorService.submit(runnable);//使用execute无法保证新线程执行完毕，无法获取新线程中的值
        System.out.println("Main Thread");
        submit.get();
        System.out.println("结果是: " + result);
        executorService.shutdown();*/
        
        //4.线程池 + Callable
        Callable callable = new Callable() {
            @Override
            public Object call() throws Exception {
                Thread.sleep(1000);
                return "I am Fourth way";
            }
        };
        Future submit = executorService.submit(callable);
        System.out.println("Main Thread");
        Object o = submit.get();
        System.out.println("结果是: " + o);
        executorService.shutdown();
    }
    
}
