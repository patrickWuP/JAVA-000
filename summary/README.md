课程总结：
学习完整个课程，就有个感受，如果秦老师曾是我的技术导师该多好，会给你引导到正确的目标上，而不是自己东一榔头，西一棒槌的学习技术，

钱和时间都花了效果却不好；重视技术知识细节的学习却忽略的学习方法的打磨，学习效率一般。

先说下从整个课程自己领悟的学习方法，看完老师的视频并不等于你掌握了老师讲的知识：

看的书，写的代码不少，为什么会有自己感觉知道的很多知识点，但一深入思考，就没有半点思绪。例如在书上看到SPI的介绍通读了一遍，就感觉自己已经掌握了SPI了，但如果有人问什么是SPI、为什么有SPI、什么时候用SPI、怎么实现SPI，一定会哑口无言，因为当时读的时候没有带着这些问题去思考。

那么阅读时，应该带着目标性去阅读。
定制正确的目标【尽量避免自以为掌握了很多知识】，多少人认为读完一本书，就认为掌握了这本书所有的知识？人天生容易高估自己，切忌不要以读完一本书为目标，否则潜意识会自认为掌握了这本书的知识，不愿意再去阅读这本书或者带着很弱的意愿去读，效果都不好。但当你读完了一本书或一个章节或一段知识点，你自动去思考获得了什么：

1.输出什么。

2.脑海里记住了什么。

可是读完再去回想，不如先自己提出问题，带着问题去阅读。按照流程 问题 -> 阅读 -> 解惑 -> 输出知识。在阅读时由会产生新的问题，可以弄一个checklist，依次按照上面的流程递归执行。

对于输出的内容，推荐使用思维导图，第一可以加深知识的记忆，第二由于留下了不错的资料，方便以后快速的复习。

从技术方面讲，课程质量是符合预期的，课外作业也是很好的进阶方式，留给自己更多的提升空间。以下是自己的一些感悟，实际操作应该如老师所说，先把每个知识点详细梳理一遍
再删减留下核心部分。这个就留给自己慢慢丰富。

###JVM

JVM + 字节码系统无关 是Java程序系统无关、Java语言无关的关键(能按照规范生成class的语言)

GC: 
    
    年轻代：标记复制算法[serialNew、ParNew、Parallel Scavenge]
    
    老年代：标记整理算法[oldSerial、parallelOld]、标记清除算法（标记整理算法兜底）[CMS]
    
新一代的GC：G1、ZGC、Shenandoah

GC排查

###JUC

由于CPU与内存执行速度差距太大，想要充分利用CPU的性能于是诞生了多进程，多线程产生的原因也是如此。

在引入多线程后，竞争资源的安全性和正确性无法保障，于是需要引入相关技术保证资源的完整性、一致性。

synchronized：系统支持的关键字锁，无需手动释放锁。

ReentrantLock：使用AQS实现的锁，支持更多的api，配置更加灵活的锁，需要手动释放锁。

COW技术：CopyOnWriteArrayList 写时上锁，全量更新数据；读备份数据。适合读多写少的场景，提升请求效率。
        
Redis 的RDB 和 AOF 也使用到 COW 技术，读时新旧引用指向同一份数据，写的时候才复制一份新的数据。

###Spring
核心模块：core、beans、context、aop
###MySQL
垂直分库分表：业务拆分，大表拆分小表

水平分库分表：大数据量的表拆分多个子表，分散数据，水平分库分表效果类似。
###Redis
多线程工作，单线程操作内存：
###MQ
系统解耦利器。

个人觉得比较像垂直细分领域的数据库。
