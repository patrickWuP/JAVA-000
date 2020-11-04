为什么HTTP是四次挥手，不能是三次

为什么HTTP挥手阶段客户端需要等待2MSL才真正关闭连接

SMP Architecture  
NUMA Architecture

线程与进程的区别是什么？

linux中线程和进程都是文件描述符，本质都是内核线程。
一个端口能被多个进程复用，fork。

####java多线程
 JVM在当前线程全为守护线程时，会将JVM进行关闭。
 
 Thread#start():创建新线程
 
 Thread#run():本线程调用
 
 Thread类
     
     | 重要属性/方法                            | 说明    | 
     | --------                               | -----:  | 
     | volatile String name                   | 线程名称-诊断分析使用      |  
     | boolean daemon = false;                | 后台守护线程标志 - 决定JVM优雅关闭     |  
     | Runnable target                        | 任务(只能通过构造函数传入)     |  
     | synchronized void start();             | [协作]启动新线程并自动执行    |  
     | void join()                            | [协作]等待某个线程执行完毕(来汇合)    |  
     | static native Thread currentThread();  | 静态方法：获取当前线程信息    |  
     | static native void sleep(long millis); | 静态方法：线程睡眠并让出CPU时间片    |  
     
 wait & notify
 
      | Object#方法                            | 说明    | 
      | --------                               | -----:  | 
      | void wait()                            | 放弃锁 + 等待0ms + 尝试获取锁;      |  
      | void wait(long timeout, int nanos)     | 放弃锁 + wait + 到时间自动唤醒/中途唤醒(精度：nanos > 0则timeout++)     |  
      | native void wait(long timeout)         | 放弃锁 + wait + 到时间自动唤醒/中途唤醒(唤醒之后需要自动获取锁)     |  
      | native void notify()                   | 发送信号通知一个等待线程    |  
      | native void notifyAll()                | 发送信号通知所有等待线程    |  
      
 辨析：
 Thread.sleep：释放CPU
 
 Object#wait：释放锁 + CPU