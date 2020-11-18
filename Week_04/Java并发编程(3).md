####读写锁 

ReadWriteLock管理一组锁，一个读锁，一个写锁。

读锁可以在没有写锁的时候被多个线程同时持有，写锁是独占的。

所有读写锁的实现必须确保写操作对读操作的内存影响。每次只能有一个写线程，但是同时可以有多个线程并发地读数据。ReadWriteLock
适用于读多写少的并发情况。

####用锁的最佳实践

Doug Lea《Java并发编程：设计原则与模式》一书中，推荐的三个用锁的最佳实践，他们分别是：

1.永远只在更新对象的成员变量时加锁。

2.永远只在访问可变的成员变量时加锁。

3.永远不在调用其他对象的方法时加锁。

KK总结-最小使用锁：

1.降低锁范围：锁定代码的范围/作用域。

2.细分锁粒度：将一个大锁，拆分成多个小锁。

####无锁技术
核心实现原理：
1.volatile保证读写操作都可见(注意不保证原子);

2.使用CAS指令，作为乐观锁实现，通过自旋重试保证写入。

###解决分布式ID的方式
1.UUID

2.redis

3.雪花算法

4.分配id的集合，0 - 1000，下一个请求只能获取 1000 - 2000的id进行分配。

###什么是并发工具类
1、wait/notify

2、Lock/Condition

可以作为简单的协作机制。

更复杂的应用场景，比如

-我们需要控制实际并发访问资源的并发数量

-我们需要多个线程在某个时间同时开始运行

-我们需要指定数量线程达到某个状态再继续处理

所以我们需要并发工具类进行复杂场景的处理。

####AQS
AbstractQueueSynchronizer，既队列同步器。它是构建锁或者其他同步组件的基础（如Semaphore、CountDownLatch、ReentrantLock、ReentrantReadWriteLock），
是JUC并发包中的核心基础组件。

AbstractQueueSynchronizer：抽象队列式的同步器。

两种资源共享方式：独占|共享，子类负责实现公平 OR 非公平。

###Semaphore - 信号量
1.准入数量N

2.N=1则等价于独占锁

使用场景：同一时间控制并发线程数。

###CountdownLatch
场景：Master线程等待Worker线程把任务执行完

示例：等所有人干完手上的活，一起去吃饭。

###CyclicBarrier
场景：任务执行到一定阶段，等待其他任务对其。

示例：等待所有人都到达，再一起开吃。

####CountDownLatch 与 CyclicBarrie比较

减计数方式 | 加计数方式

计算为0时释放所有等待的线程 | 计数达到指定值释放所有等待线程

计数为0时，无法重置 | 计数达到指定值时，计数置为0重新开始

调用countDown()方法计数减一，调用await()方法只进行阻塞，对计数没任何影响 | 调用await()方法计数加1，若加1后的值不等于构造方法的值，则线程阻塞

不可重复利用 | 可重复利用

###java集合
线性集合

List ArrayList,LinkedList,Stack 
 
Set HashSet,TreeSet

Queue Dueue 


Map HashMap,TreeHashMap,LinkedHashMap
Dictionary Properties

####CopyOnWriteArrayList
核心改进原则：

1、写加锁，保证不会写混乱。

2、写在一个Copy副本上，而不是原始数据上。

读的是当前的数据，后面的写操作是在其副本上操作对其没有读影响。读的时候写入了数据阶段，会有读数据不一致的情况发生。保证了最终一致。

Object[] newElements = Arrays.copyOf(elements, len + 1);

System.arraycopy(elements, 0, newElements, 0, index);

System.arraycopy(elements, index + 1, newElements, index, numMoved);

使用迭代器的时候，直接拿当前的数组对象做为快照，此后的List元素变动，就跟这次迭代没关系了。

####HashMap

基本特点：空间换时间，哈希冲突不大的情况下查找数据性能很高

用途：存放指定key的对象，缓存对象

原理：使用hash原理，存k-v数据，初始容量16，扩容x2，负载因子0.75

JDK8以后，在链表长度到8 & 数组长度到64时，使用红黑树。

安全问题：

1、写冲突

2、读写问题，可能会死循环

3、keys()无序问题

####LinkedHashMap
基本特点：继承自HashMap,对Entry集合添加了一个双向链表

用途：保证有序，特别是Java8 stream操作的toMap时使用

原理：同LinkedList，包括插入顺序和访问顺序(将访问的节点挪到链表尾部)

安全问题：同HashMap

####ConcurrentHashMap

Java7 使用分段锁 多个Segment -> 一个Segment -> 对应HashMap的桶

分段锁 默认16个Segment，降低锁粒度。concurrentLevel = 16

Segment[] -> 分库

HashEntry[] -> 分表

Java8 取消分段锁 链表加入红黑树操作

Java7为实现并行访问，引入了Segment这一结构，实现了分段锁，理论上最大并发度与Segment个数相等。

Java8为进一步提高并发性，摒弃了分段锁的方案，而是直接使用一个大的数组。

####线程安全操作利器 - ThreadLocal
public ThreadLocal()  构造方法

protected T initialValue()  复写-设置初始默认值

void set(T value)  设置本线程对应的值

void remove()  清理本线程对应的值

T get()  获取本线程对应的值

线程本地变量

场景：每个线程一个副本

不改方法签名静默传参

及时进行清理

并行Stream

####线程间协作与通信
1.线程间共享：

static/实例变量(堆内存)

Lock

synchronized

2.线程间协作：

Thread#join()

Object#wait/notify/notifyAll

Future/Callable

CountdownLatch

CyclicBarrier