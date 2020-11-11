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
 
 Object#wait：释放锁 + 释放CPU
 
 ###Thread的状态改变操作
 1.Thread.sleep(long millis)，一定是当前线程调用此方法，当前线程进入TIMED_WAITING状态，但不释放对象锁，mills后线程自动
 苏醒进入就绪状态。作用：给其他线程执行机会的最佳方式。不释放锁，释放CPU
 
 2.Thread.yield(),一定是当前线程调用此方法，当前线程放弃获取的CPU时间片，但不释放资源，由运行状态变为就绪状态，让OS再次选择线程。
 作用：让相同优先级的线程轮流执行，但并不保证一定会轮流执行。实际中无法保证yield()达到让步目的，因为让步的线程还有可能被线程
 调用程序再次选中。Thread.yield()不会导致阻塞。该方法与sleep()类似，只是不能由用户指定暂停多长时间。作用线程装填 Runing -> Runnable
 
 3.t.join()/t.join(long millis)，当前线程里调用其他线程t的join方法，当前线程进入WAITING/TIMED_WAITING状态，当前线程不会
 释放已持有的对象锁。线程t执行完毕或者millis时间到，当前线程进入就绪状态。但是会释放t线程中所占用的锁。
 
 4.obj.wait()，当前线程调用对象的wait()方法，当前线程释放对象锁，进入等待队列。依靠notify()/notifyAll()唤醒或者wait(long timeout)timeout时间到自动唤醒。
 
 5.obj.notify()唤醒在此对象监视器上等待的单个线程，选择是任意性的。notifyAll()唤醒在此对象监视
 
 ###Thread的中断与异常处理
 1.线程内部自己处理异常，不溢出到外层。
 
 2.如果线程被Object.wait,Thread.join和Thread.sleep三种方法之一阻塞，此时调用该线程的interrupt()方法，那么该线程将抛出一个InterruptedException中断异常
 (该线程必须事先预备好处理此异常)，从而提早地终结被阻塞状态。如果线程没有被阻塞，这时调用interrupt()将不起作用，直到执行到wait()，sleep()，join()时，才马上会
 抛出InterruptedException。
 
 3.如果是计算密集型
 
 System.in.read();  点击回车后程序才进行退出
 
 thread1.join();   **thread1上的锁就被释放掉了**
 
 等待(WAITING)：需要别人通过模拟器信号机制进行唤醒
 
 超时等待(TIMED_WAITING)：有调度器自动进行唤醒

 阻塞(BLOCKED)：通过锁进行处理。被动:遇到锁、被通知。
 
 RWB:Runable | Running | Wait | Timed_Wait | Blocked
 
 从具体了解抽象容易，从抽象了解具体比较困难
 
 ###并发相关的性质
 可见性：对于可见性，java提供了volatile关键字来保证可见性。
 
 当一个共享变量被volatile修饰时，它会保证修改的值会立即被更新到主内存，当有其他线程需要读取时，它会去内存中读取新值。添加内存屏障
 
 另外，通过synchronized和Lock也能够保证可见性，synchronized和Lock能保证同一时刻只有一个线程获取锁然后执行同步代码，并且在释放锁之前会将对变量的修改刷新到主存当中。
 
 volatile（并不能保证原子性）
 
 1.每次读取都强制从主内存刷数据。
 
 2.适用场景：单个线程写；多个线程读。
 
 3.原则：能不用就不用，不确定的时候也不用。
 
 4.替代方案：Atomic原子操作类。
 
 有序性：Java允许编译器和处理器对指令进行重排序，但是重排序过程不会影响到单线程程序的执行，却会影响到多线程并发执行的正确性。可以通过
 volatile关键字来保证一定的"有序性"（synchronized和Lock也可以）
 
 happens-before原则（现行发生原则）：
 
 1.程序次序规则：一个线程内，按照代码先后顺序。
 
 2.锁定规则：一个unLock操作先行发生于后面对用一个锁的lock操作。
 
 3.Volatile变量规则：对一个变量的读写操作先行发生于后面对这个变量的读操作。先行发生，后面指时间上的先后顺序才好理解。
 
 4.传递规则：如果操作A先行发生于操作B，而操作B又先行发生于操作C，则可以得出A先于C。
 
 5.线程启动规则：Thread对象的start()方法先行发生于此线程的每一个动作。
 
 6.线程中断规则：对线程interrupt()方法的调用先行发生于被中断线程的代码检测到中断事件的发生。
 
 7.线程终结规则：线程中所有的操作都先行发生于线程的终止检测，我们可以通过Thread.join()方法结束、Thread.isAlive()的返回值手段检测到线程已经终止执行。
 
 8.对象终结规则：一个对象的初始化完成先行发生于他的finalize()方法的开始。

###synchronized的实现（synchronized的锁都是放到对象上的）
1.使用对象头标记字（Object monitor）

2.Synchronized方法优化

3.偏向锁：BiaseLock

###final

final claxx XXX：不允许继承

final 方法：不允许Override

final 局部变量：不允许修改

final 实例属性：构造函数/初始化/<init>之后不允许变更；
               只能赋值一次
               安全发布：构造函数结束返回时，final域最新的值被保证对其他线程可见
               
final static 属性：<clinit>静态块执行后不允许变更；只能赋值一次        

思考：final声明的引用类型与原生类型在处理时有什么区别？
Java里的常量替换。

规范：方法参数都为final修饰的。 