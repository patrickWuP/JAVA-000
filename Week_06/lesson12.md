####复习下什么是性能

吞吐与延迟：有些结论是反直觉的，指导我们关注什么

没有量化就没有改进：监控与度量指标，指导我们怎么去入手

80/20原则：先优化性能瓶颈问题，指导我们如何去优化

过早的优化是万恶之源：指导我们要选择优化的时机

脱离场景谈性能都是耍流氓：指导我们对性能要求要符合实际

####DB/SQL优化是业务系统性能优化的核心

业务系统的分类：计算密集型、数据密集型

业务处理本身无状态，数据状态最终要保存到数据库

一般来说，DB/SQL操作的消耗在一次处理中占比最大

业务系统发展的不同阶段和时期，性能瓶颈要点不同，类似木桶装水

>关系数据库MySQL

什么是关系数据库

-1970年Codd提出关系模型，以关系代数理论为数学基础《A Relational Model of Data for Large Shared Data Banks》

-E-R图

-数据库范式

第一范式(1NF)：关系R属于第一范式，当且仅当R中的每一个属性A的值域只包含原子项

第二范式(2NF)：在满足1NF的基础上，消除非主属性对码的部分函数依赖

第三范式(3NF)：在满足2NF的基础上，消除非主属性对码的传递函数依赖

BC范式(BCNF)：在满足3NF的基础上，消除主属性对码的部分和传递函数依赖

第四范式(4NF)：消除非平凡的多值依赖

第五范式(5NF)：消除一些不适合的连接依赖

1NF：消除重复数据，既每一列都是不可再分的基本数据项；每个列都是原子的。

2NF：消除部分依赖，表中没有列只与主键的部分相关，既每一行都被主键唯一标识；

3NF：消除传递依赖，消除表中列不依赖主键，而是依赖表中的非主键列的情况，既没有列是与主键不相关的。

从表只引用主表的主键

既表中每列都和主键相关。

BCNF：Boyce-Codd Normal Form（巴斯-科德范式）

3NF的基础上消除主属性对于码的部分与传递函数依赖

设计表的约束，不是强制要求，按照业务需求进行灵活设置。

表字段的冗余是常态

>常见数据库

开源：MySQL、PostgreSQL

商业：Oracle、DB2、SQL Server

内存数据库：Redis?,VoltDB

图数数据库：Neo4j,Nebula

时序数据库：InfluxDB、openTSDB

其他关系数据库：Access、Sqlite、H2、Derby、Sybase、Infomix等

NoSQL数据库：MongoDB、Hbase、Cassandra、CouchDB

NewSQL/分布式数据库：TIDB、CockroachDB、NuoDB、OpenGauss、OB、TD

####SQL语言

SQL语言1974由Boyce和Chamberlin提出，并首先在IBM公司研制的关系数据库系统SystemR上实现。

1979年ORACLE公司首先提供商用的SQL，IBM公司在DB2和SQL/DS数据库系统中也实现了SQL。

1986年10月，美国ANSI采用SQL作为关系数据库管理系统的标准语言（ANSI X3. 135-1986），后来国际标准化组织(ISO)采纳为国际标准。

1989年，美国ANSI采纳在ANSI X3.135-1989报告中定义的关系数据库管理系统的SQL标准语言，称为ANSI SQL89，该标准替代ANSI X3.135-1986版本。

>结构化查询语言包含6个部分：

1、*数据查询语言(DQL:Data Query Language)：其语句，也称为“数据检索语句”，用以从表中获得数据，确定数据怎样在应用程序给出。保留字SELECT是DQL(也是所有SQL)
用的最多的动词，其他DQL常用的保留字有WHERE，ORDER BY，GROUP BY和HAVING。这些DQL保留字常与其他类型的SQL语句一起使用。

2、*数据操作语言（DML:Data Manipulation Language）：其语句包括动词INSERT、UPDATE和DELETE。它们分别用于添加、修改和删除。

3、事务控制语言（TCL）：它的语句能够确保被DML语句影响的表的所有行及时得以更新。包括COMMIT（提交）命令、SAVEPOINT（保存点）命令、ROLLBACK（回滚）命令。

4、数据控制语言（DCL）：它的语句通过GRANT或REVOKE实现权限控制，确定单个用户和用户组对数据库对象的访问。某些RDBMS可用GRANT或REVOKE控制对表单个列的访问。

5、*数据定义语言（DDL）：其语句包括动词CREATE，ALTER和DROP。在数据库中创建新表或修改、删除表（CREATE TABLE 或 DROP TABLE）；为表加入索引等。

6、指针控制语言（CCL）：它的语句，像DECLARE CURSOR、FETCH INTO和UPDATE 用于对一个或多个表单独行的操作。

SQL的各个版本：
1986年，ANSI X3.135-1986,ISO/IEC 9075:1986,SQL-86
1989年，ANSI X3.135-1989,ISO/IEC 9075:1989,SQL-89
*1992年，ANSI X3.135-1992,ISO/IEC 9075:1992,SQL-92(SQL2)
*1999年，ISO/IEC 9075:1999,SQL:1999(SQL3)
2003年，ISO/IEC 9075:2003,SQL:2003
2008年，ISO/IEC 9075:2008,SQL:2008
2011年，ISO/IEC 9075:2011,SQL:2011

>MySQL数据库

瑞典的MySQL AB创立于1995年。

2008年1月16日MySQL AB被Sun Microsystems收购。

2009年4月20日，甲骨文（Oracle）收购Sun Microsystems公司。

其后分离成两个版本：MariaDB和MySQL，这两个Sql的名称都来源于MySql的作者两个女儿的名字。

####MySQL的版本

-4.0支持InnoDB，事务

-2003年，5.0

-5.6 ==> 历史使用最多的版本

-5.7 ==> 近期使用最多的版本

-8.0 ==> 最新和功能完善的版本

-5.6/5.7的差异

5.7支持：

-多主

-MGR高可用

-分区表

-json

-性能

-修复XA等

-5.7/8.0的差异

-通用表达式

-窗口函数

-持久化参数

-自增列持久化

-默认编码utf8mb4

-DDL原子性

-JSON增强

-不再对group by 进行隐式排序？？ ==> 坑

>深入MySQL原理

独占模式

1、日志组文件：ib_logfile0和ib_logfile1，默认均为5M
2、表结构文件：*.frm
3、独占表空间文件：*.ibd
4、字符集和排序规则文件：db.opt
5、binlog二进制日志文件：记录主数据库服务器的DDL和DML操作
6、二进制日志索引文件：master-bin.index

共享模式innodb_file_per_table = 1
1)、数据都在ibdata1


create database k1;

create schema k2; 这两者是等价的。

>MySQL执行流程

>MySQL执行引擎和状态

sleep 线程正在等待客户端发送新的请求

query 线程正在执行查询或者正在讲结果发送给客户端

locked mysql服务器层，线程正在等待表锁

analying and statistics 线程正在收集存储引擎的统计信息，并生成查询的执行计划

copying to temp table  线程正在执行查询，并且将结果复制到临时表中，一般是做group by操作，要么是文件排序

on disk 将内存中的临时表放在磁盘上

sorting result 线程正在对结果集进行排序

sending data 线程在多个状态之间传递数据或者生成结果集或者向客户端返回数据

####MySQL对SQL执行顺序

sql执行顺序->顺序 


####MySQL索引原理

数据是按页来分块的，当一个数据被用到时，其附近的数据也通常会马上被使用。

InnoDB使用B+树实现聚集索引。

为什么一般单表数据不超过2000万？ 每个B+树层级不超过3层


####MySQL数据库操作演示
-安装的几种方式，安装文件或命令，docker

-操作工具，mysql-cli或IDE(DataGrip,MySQL-WorkBench，MySQL-Front，Navicat等)

-MySQL库结构，操作语句与命令

-MySQL SQL语法演示

有免安装的压缩包，启动mysql更加的方便

####参数配置优化

查看参数配置

-show variable like xxx

my.cnf文件

[mysqld]
server

[mysql]
client

1)连接请求的变量

*1、max_connections   最大连接数，资源宝贵，不适合开特别大

2、back_log

3、wait_timeout和interative_timeout

2)缓冲区变量

4、key_buffer_size

*5、query_cache_size(查询缓存简称QC)

6、max_connect_errors;

7、sort_buffer_size;

8、max_allowed_packet=32M

9、join_buffer_size=2M

10、thread_cache_size=300

3)配置InnoDB的几个变量

*11、Innodb_buffer_pool_size

12、innodb_flush_log_at_trx_commit

13、innodb_thread_concurrency=0

14、innodb_log_buffer_size

15、innodb_log_file_size=50M

16、innodb_log_files_in_group=3

17、read_buffer_size=1M

18、read_rnd_buffer_size=16M

19、bulk_insert_buffer_size=64M

20、binary log

mysql内存越大，CPU数越多，性能越好。


>MySQL数据库设计优化-最佳实践

-如何恰当选择引擎

-库表如何命名

-如何合理拆分宽表

-如何选择恰当数据类型：明确，尽量小
    -char、varchar的选择
    -(text/blob/clob)的使用问题？尽量少用这几个类型,insert的时候会先插入其他数据，再update该字段的数据
    -文件、图片是否要存入到数据库？
    -时间日期的存储问题？ 有多个格式可供选择，注意时区的问题。可以尝试使用时间戳进行存储
    -数值的精度问题
   -是否使用外键、触发器？ 绝大数情况下不建议使用外键和触发器。触发器引入了一定的不确定性。
   
   
-唯一约束和索引的关系

-是否可以冗余字段

-是否使用游标、变量、视图、自定义函数、存储过程

-自增主键的使用问题，分布式环境如何处理,防止主键冲突

-能够在线修改表结构(DDL操作)

-逻辑删除还是物理删除

-要不要加create_time，update_time时间戳

-数据库碎片问题

-如何快速导入导出、备份数据