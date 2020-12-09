数据迁移的方式：全量

-全量数据导出和导入

1、业务系统停机

2、数据库迁移，校验一致性

3、然后业务系统升级，接入新数据库。

一次性加入索引，比每条记录加入索引要快的多。

直接复制的话，可以dump后全量导入
（如果是）异构数据，需要用程序来处理

优点：

缺点：停机时间太长

数据迁移的方式：全量 + 增量
-依赖于数据本身的时间戳

1、先同步数据到最近的某个时间戳

2、然后在发布升级时停机维护

3、再同步最后一段时间（通常是一天）的变化数据。

4、然后升级业务系统，接入新数据库。

优点：极大的降低了停机的时间

缺点：

数据迁移的方式：binlog + 全量 + 增量

-通过主库或者从库的binlog来解析和重新构造数据，实现复制。

-一般需要中间件等工具的支持。

可以实现多线程，断点续传，全量历史和增量数据同步。

继而可以做到：
1、实现自定义复杂异构数据结构；

2、实现自动扩容和缩容，比如分库分表到单库单表，单库单表到分库分表，分4个库表到分64个库表。

优点：几乎可以做到不停机切换，按照实际情况

缺点：

同构和异构的数据库，其实还需要测试进行功能验证，异构库情况更加复杂，测试可以提前介入进行测试验证。

数据库中间件ShardingSphere

迁移工具ShardingSphere-scaling

1、支持数据全量和增量同步。

2、支持断点续传和多线程数据同步。

3、支持数据库异构复制和动态扩容。

4、具有UI界面，可视化配置。

为什么需要分布式事务

由于对数据库进行了高可用处理，分库分表，读写分离等，造成多个库结构，无法通过一个事务进行管理。

多个微服务同一个库，也需要分布式事务，他们的事务无法被一个事务进行统一管理

什么叫分布式事务

业务系统的复杂度提升，数据量的增加，比如导致出现分布式事务。

随着互联网、金融等行业的快速发展，业务越来越复杂，一个完整的业务往往需要调用多个子业务或服务，随着业务的不断增多，涉及的服务及数据也
越来越多，越来越复杂。传统的系统难以支撑，出现了应用和数据库等的分布式系统。分布式系统又带来了数据一致性的问题，从而产生了分布式事务。


分布式条件下，多个节点操作的整体事务一致性。

特别是在微服务场景下，业务A和业务B关联，事务A成功，事务B失败，由于跨系统，就会导致不被感知。此时从整体来看，数据是不一致的。


此时从整体来看，数据是不一致的。

如何实现分布式下的一致性

典型情况下是两个思路：

1、理想状态：直接像单机数据库事务一样，多个数据库自动通过某种协议机制，实现了跨数据库节点的一致性。

使用场景：要求严格的一致性的，比如金融交易类业务。

2、一般情况：可以容忍一段时间的数据不一致，最终通过超时终止，调度补偿，等等方式，实现数据的最终状态的一致性。

使用场景：准实时或非实时的处理，比如T+1的各类操作，或者电扇类操作。


1、强一致：XA

2、弱一致：

1)不用事务，业务侧补偿冲正。造成大量的使用定时任务进行处理。

2)所谓的柔性事务，使用一套事务框架保证最终一致的事务。

>XA分布式事务协议

基于第一个强一致的思路，就有了基于数据库本身支持的协议，XA分布式事务。

XA整体设计思路可以概括为，如何在现有事务模型上微调扩展，实现分布式事务。

X/Open，即现在的open group，是一个独立的组织，主要负责制定各种行业标准。X/Open组织主要有各大知名公司或者产商进行支持，
这些组织不光遵循X/Open组织定义的行业技术标准，也参与到标准的制定。

应用程序（Application Program，简称AP）：用于定义事务边界（既定义事务的开始和结束），
并且在事务边界内对资源进行操作。

资源管理器（Resource Manager，简称RM）：如数据库、文件系统等，并提供访问资源的方式。各个分布式事务是不相互通信的。

事务管理器（Transaction Manager，简称TM）：负责分配事务唯一标识，监控事务的执行进度，并负责事务的提交、回滚等。

###XA接口

xa_start：负责开启或者恢复一个事务分支

xa_end：负责取消当前线程与事务分支的关联

xa_prepare：询问RM是否准备提交事务分支

xa_commit：通知RM提交事务分支

xa_rollback：通知RM回滚事务分支

xa_recover：需要恢复的XA事务，只要是查询当前所有的xa_prepare状态的事务。

MySQL从5.0.3开始支持InnoDB引擎的XA分布式事务，MySQL Connector/J 从5.0.0版本开始支持XA。

show engines;

在DTP模型中，MySQL属于资源管理器（RM）。分布式事务中存在多个RM，由事务管理器TM来统一进行协调。

XA {START|BEGIN} xid [JOIN|RESUME] //开启XA事务，如果使用的是XA START而不是XA BEGIN，那么不支持[JOIN|RESUME],xid是一个唯一值

XA END xid [SUSPEND [FOR MIGRATE]] //结束一个XA事务，不支持[SUSPEND[FOR MIGRATE]]

XA PREPARE xid //准备提交

XA COMMIT xid [ONE PHASE] //提交，如果使用了ONE PHASE，则表示使用一阶段提交。两阶段提交协议中，如果只有一个RM参与，那么可优化为一阶段提交。

XA ROLLBACK xid //回滚

XA RECOVER [CONVERT XID] //列出所有处于PREPARE阶段的XA事务

思考：为什么XA事务又叫两阶段事务。事务有个中间状态prepare，经过该状态后才去提交。

-思考一个问题：XA过程中，事务失败怎么办

1、业务SQL执行过程，某个RM崩溃怎么处理？

直接全部回滚

2、全部prepare后，某个RM崩溃怎么处理？

5.7对MySQL XA的优化/bug修复

MySQL < 5.7 版本会出现的问题(后续我们可以来重现)

已经Prepare的事务，在客户端退出或者服务宕机的时候，2PC的事务会被回滚。

在服务器故障重启提交后，响应的Binlog被丢失

MySQL 5.6版本在客户端退出的时候，自动把已经prepare的事务回滚了，那么MySQL为什么要这样做？这主要取决于MySQL的内部实现，MySQL5.7以前的版本，
对于prepare的事务，MySQL是不会记录binlog的(官方说是减少fsync，起到了优化的作用)。只有当分布式事务提交的时候才会把前面的操作写入binlog信息，
所以对于binlog来说，分布式事务与普通的事务没有区别，而prepare以前的操作信息都保存在连接的IO_CACHE中，如果这个时候客户端退出了，以前的binlog信息
都会被丢失，再次重连后允许提交的话，会造成Binlog丢失，从而造成主从数据库的不一致，所以官方在客户端退出的时候直接把已经prepare的事务回滚了！

3、commit时，某个RM崩溃怎么办？



XA分布式事务协议

-注意：XA默认不会改变隔离级别

XA协议存在的问题

1.同步阻塞问题 - 一般情况下，不需要调高隔离级别
全局事务内部包含了多个独立的事务分支，这一组事务分支要不都成功，要不都失败。各个事务分支的ACID
特性共同构成了全局事务的ACID特性。也就是将单个事务分支的支持的ACID特性提升一个层次（up a level）到分布式事务的范畴。即使在非分布式事务中（既本地事务）
，如果对操作读很敏感，我们也需要将事务隔离级别设置为Serializable。而对于分布式事务来说，更是如此，可重复读隔离级别不足以保证分布式事务一致性。
也就是说，如果我们使用mysql来支持XA分布式事务的话，那么最好将事务隔离级别设置为SERIALIZABLE。

2.单点故障

由于协调者的重要性，一旦协调者TM发生故障。参与者RM会一直阻塞下去。尤其在第二阶段，协调者发生故障，那么所有的参与者还都处于锁定事务资源的状态中，
而无法继续完成事务操作。（如果是协调者挂掉，可以重新选举一个协调者，但是无法解决因为协调者宕机导致的参与者处于阻塞状态的问题）。

3.数据不一致 -极端情况下，一定有事务失败问题，需要监控和人工处理

在二阶段提交的阶段二中，当协调者向参与者发送commit请求之后，发生了局部网络异常或者在发送commit请求过程中协调者发生了故障，这会导致只有一部分参与者
接受到了commit请求。而在这部分参与者接到commit请求之后就会执行commit操作。但是其他部分未接到commit请求的机器则无法执行事务提交。于是整个分布式
系统便出现了数据不一致的现象。

打破100%可靠，100%高可用的想法
>BASE柔性事务

本地事务 -> XA(2PC) -> BASE

如果将实现了ACID的事务要素的事务称为刚性事务的话，那么急于BASE事务要素的事务则称为柔性事务。
BASE是基本可用、柔性状态和最终一致性这三个要素的缩写。

-基本可用（Basically Available）保证分布式事务参与方式不一定同时在线。

-柔性状态（Soft state）则允许系统状态更新有一定的延时，这个延时对客户来说不一定能够察觉。

-而最终一致性（Eventually consistent）通常是通过消息传递的方式保证系统的最终一致性。

在ACID事务中对隔离性的要求很高，在事务执行过程中，必须将所有的资源锁定。柔性事务的理念则是通过业务逻辑将互斥锁操作从资源层面上移致业务层面。
通过放宽对强一致性要求来换取系统吞吐量的提升。
          
   本地事务 | 两（三）阶段事务 | 柔性事务

业务改造  |无      | 无             | 实现相关接口

一致性    |不支持   |支持            | 最终一致

隔离性    |不支持   | 支持           |业务放保证

并发性能  |无影响   | 严重衰退        |略微衰退

适合场景  |业务放处理不一致 | 短事务&低并发 | 长事务&高并发

BASE柔性事务常见模式

1、TCC

通过手动补偿处理

2、AT

通过自动补偿处理


####什么是TCC

BASE柔性事务TCC

TCC模式既将每个服务业务操作分为两个阶段，第一个阶段检查并预留相关资源，第二阶段根据所有服务
业务的Try状态来操作，如果都成功，则进行Confirm操作，如果任意一个Try发生错误，则全部Cancel。

TCC使用要求就是业务接口都必须事先三段逻辑：

1、准备操作Try：完成所有业务检查，预留必须的业务资源。

2、确认操作Confirm：真正执行的业务逻辑，不做任何业务检查，只使用Try阶段预留的业务资源。因此，只要Try操作成功，Confirm必须能成功。
另外，Confirm操作需满足幂等性，保证一笔分布式事务能切只能成功一次。

3、取消操作Cancel：释放Try阶段预留的业务资源。同样的，Cancel操作也需要满足幂等性。

TCC不依赖RM对分布式事务的支持，而是通过对业务逻辑的分解来实现分布式事务，不同于AT的是就是需要自行定义各个阶段的逻辑，对业务有侵入。

确定try阶段事务对业务资源进行了校验和处理。类似账户金额就得加入冻结金额，对业务有侵入。

confirm阶段真正的冻结金额进行减少或者增加。

confirm阶段抛异常进行回滚时，由于try阶段已经执行资源相关sql，所以需要cancel方法对前面资源的操作进行逆操作（回滚）。

TCC需要注意的几个问题：

1、允许空回滚 cancel方法可能没有处理的数据，try层没有产生数据。

2、防悬挂控制，try cancel，由于网络抖动，先执行cancel，后执行了try，导致后面的try没有cancel方法了。

保证cancel方法在try方法后面执行。

3、幂等设计，可能会重试

什么是SAGA（TC n个T和n个C）

Saga模式没有try阶段，直接提交事务。

复杂情况下，对回滚操作的设计要求较高。

什么是AT

AT模式就是两阶段提交，自动生成反向SQL

自动产生反向的SQL，多了个SQL解析的过程，太复杂的业务不一定能支持，revertSQL

####柔性事务下隔离级别

事务特性

原子性（Atomicity）：正常情况下保证。

一致性（Consistency）：在某个时间点，会出现A库和B库的数据违反一致性要求的情况，但是最终是一致的。

隔离性（Isolation）：在某个时间点，A事务能够读到B事务部分提交的结果。

持久性（Durability）：和本地事务一样，只要commit则数据被持久

隔离级别

一般情况下都是读已提交（全局锁）、读未提交（无全局锁）

Seata

Seata-TCC/AT柔性事务

Seata是阿里集团和蚂蚁金服联合打造的分布式事务框架。其AT事务的目标是在微服务架构下，提供增量的事务ACID语义，让开发者像使用
本地事务一样，使用分布式事务，核心理念同Apache ShardingSphere一脉相承。

Seata AT事务模型包含TM（事务管理器），RM（资源管理器）和TC（事务协调器）。TC是一个独立部署的服务，TM和RM以jar包的方式
同业务应用一同部署，它们同TC建立长连接，在整个事务生命周期内，保持远程通信。TM是全局事务的发起方，负责全局事务的开启，
提交和回滚。RM是全局事务的参与者，负责分支事务的执行结果上报，并且通过TC的协调进行分支事务的提交和回滚。

Seata 管理的分布式事务的典型生命周期：

TM要求TC开始一个全新的全局事务。

TC生成一个代表该全局事务的XID。

XID贯穿于微服务的整个调用链。

TM要求TC提交或回滚XID对应全局事务。

TC驱动XID对应的全局事务下的所有分支事务完成提交或回滚。

>hmily

支持嵌套事务（Nested transaction Support）等复杂场景

支持RPC事务恢复，超时异常恢复等，具有高稳定性

基于异步Confirm和Cancel设计，相比其他方式具有更高性能

基于SPI和API机制设计，定制性强，具有高扩展性

本地事务的多种序列化支持：redis/mongodb/zookeeper/file/mysql
事务日志的多种序列化支持：java/hessian/kryo/protostuff

基于高性能组件disruptor的异步日志性能良好

实现了SpringBoot-Starter，开箱即用，集成方便

采用Aspect AOP切面思想与SPring无缝集成，天然支持集群

实现了基于VUE的UI界面，方便监控和管理

MainService：事务发起者（业务服务）

TxManage：事务协调者

ActorService：事务参与者（多个业务服务）

Try：事务执行

Confirm：事务确认

Cancel：事务回滚

Redo日志：可以选择任意一种进行存储