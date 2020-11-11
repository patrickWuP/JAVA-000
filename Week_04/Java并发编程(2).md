submit 方法的异常可以在主线程中catch到。

execute 方法执行任务是捕捉不到异常的。  
通过案例发现execute也是能捕捉到异常的。


###ThreadPoolExecutor执行任务的逻辑

1.判断corePoolSize[创建线程]

2.加入workQueue

3.判断maximumPoolSize[创建线程]

4.执行拒绝策略处理器

因为我们的cpu的内核数是相当有限的。

假如是计算密集型的，直接扩充线程数，没有意义，反而多了上下文的开销。

假如是IO密集型的，首先填满核心线程，用queue缓一缓后面的任务，因为前面的任务可能会阻塞等待。如果queue都填满了前面还是没有处理，这时可以新加线程消化堆积的任务。

这种设计把两种的线程使用场景都很好的进行兼容了。


缓冲队列

BlockingQueue是双缓冲队列。

###创建固定线程池的经验
不是越大越好，太小肯定也不好；
 假设核心数为N，
 
 1.如果是CPU密集型应用，则线程池大小设置为N或N+1;
 2.如果是IO密集型应用，则线程池大小设置为2N或2N+2;
 
 线程之间相互协调
 
 异步的结果怎么去拿
 
 总之从单线程上升到多线程，因此产生了许多问题
 
 ###拒绝策略
 
 1.ThreadPoolExecutor.AbortPolicy：丢弃任务并抛出 ProjectedExecutionException异常。
 
 2.ThreadPoolExecutor.DiscardPolicy：丢弃任务，但是不抛出异常。
 
 3.ThreadPoolExecutor.DiscardOldestPolicy：丢弃队列最前面的任务，然后重新提交被拒绝的任务。
 
 4.ThreadPoolExecutor.CallerRunsPolicy：由调用线程（提交任务的线程）处理该任务。
 
 ###创建线程池方法
 1.newSingleThreadExecutor
 创建一个单线程的线程池。这个线程池只有一个线程在工作，也就是相当于单线程串行执行所有任务。如果这个唯一的线程因为异常结束，
 那么会有一个新的线程来替代它。此线程池保证所有任务的执行顺序按照任务的提交顺序执行。
 
 2.newFixedThreadPool 创建固定大小的线程池。每次提交一个任务就创建一个线程，直到线程达到线程池的最大大小。线程池的大小
 一旦达到最大值就会保持不变，如果某个线程因为执行异常而结束，那么线程池会补充一个新线程。
 
 3.newCachedThreadPool 创建一个可缓存的线程池。如果线程池的大小超过了处理任务所需的线程，那么就会回收部分空闲(60秒不执行任务)
 的线程，当任务数增加时，此线程池又可以智能的添加新线程来处理任务。此线程池不会对线程池大小做限制，线程池大小完全依赖于操作系统(或者说是JVM)
 能够创建的最大线程大小。
 
 4.newScheduledThreadPool 创建一个大小无限的线程池。此线程池支持定时以及周期性执行任务的需求。
 
 ###Atomic工具类

为什么有并发原子类，由于并发时计数不正确导致需要并发原子类。
1.原子类工具包：java.util.concurrent.atomic

2.无锁计数的底层实现原理

Unsafe API Compare-And-Swap 

CPU硬件指令支持：CAS指令
 
 ####锁与无锁之争
 并发低的情况下两者区别不大
 
 并发高的情况下无锁效率更好
 
 并发特别高的情况下，线程的竞争的无锁会导致，线程空旋占用CPU，此时有锁使线程排队执行效果更佳。
 
 个人总结：选择锁与无锁是一种权衡的过程，决定使用哪种方式，是由那一面产生更少的资源浪费。
 
 加锁导致的线程的上下文切换 大于 线程空旋时，既无锁方式更佳。
 
 线程空旋浪费 大于 加锁导致的线程的上下文切换，既加锁方式更佳。
 
 LongAdder对AtomicLong的改进 无锁的分段请求，增强其并发能力。
 
 LockSupport.park() 能够被LockSupport.unpark();和 t1.interrupt();唤醒。
 
 
 
 ##个人感悟：
 
 我发现老师讲的有画面感的知识，记忆更加深刻，
 例如以前记忆：
 
 public static ExecutorService newFixedThreadPool(int nThreads)
 
 public static ExecutorService newCachedThreadPool()
 
 public static ScheduledExecutorService newSingleThreadScheduledExecutor()
 
 也能大概记得，但一时想不起为啥要这么做，老师描述是同样饺子的不同的馅，由于关联起包饺子时多个盆里放着不同的馅，记忆深刻，回想起来也能快速反应起为什么有这几种线程池。
 
 
 如何衡量多线程程序的性能？