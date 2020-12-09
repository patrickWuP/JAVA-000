##作业
1.（选做）分析前面作业设计的表，是否可以做垂直拆分。
垂直拆分为业务拆分，判断创建的表字段是否较多，在合理的情况下业务是否可以拆分成更细的粒度。
由于前面的表结构比较简单，无需再次进行垂直拆分。
2.（必做）设计对前面的订单表数据进行水平分库分表，拆分 2 个库，每个库 16 张表。并在新结构在演示常见的增删改查操作。代码、sql 和配置文件，上传到 Github。
    
    a.先创建shop_0和shop_1的库。
    b.配置config-sharding.yaml文件，配置内容如下，关键点为每个库16张表。
    
    
    schemaName: test_shop
    
    dataSources:
      shop_0:
        url: jdbc:mysql://127.0.0.1:3306/shop_0?serverTimezone=UTC&useSSL=false
        username: root
        password: root
        connectionTimeoutMilliseconds: 30000
        idleTimeoutMilliseconds: 60000
        maxLifetimeMilliseconds: 1800000
        maxPoolSize: 10
        minPoolSize: 1
        maintenanceIntervalMilliseconds: 30000
        
      shop_1:
        url: jdbc:mysql://127.0.0.1:3306/shop_1?serverTimezone=UTC&useSSL=false
        username: root
        password: root
        connectionTimeoutMilliseconds: 30000
        idleTimeoutMilliseconds: 60000
        maxLifetimeMilliseconds: 1800000
        maxPoolSize: 10
        minPoolSize: 1
        maintenanceIntervalMilliseconds: 30000
    
    rules:
    - !SHARDING
      tables:
        t_order:
          actualDataNodes: shop_${0..1}.t_order_${0..15}
          tableStrategy:
            standard:
              shardingColumn: id
              shardingAlgorithmName: t_order_inline
          keyGenerateStrategy:
            column: id
            keyGeneratorName: snowflake
      defaultDatabaseStrategy:
        standard:
          shardingColumn: id
          shardingAlgorithmName: database_inline
      defaultTableStrategy:
        none:
      
      shardingAlgorithms:
        database_inline:
          type: INLINE
          props:
            algorithm-expression: shop_${id % 2}
        t_order_inline:
          type: INLINE
          props:
            algorithm-expression: t_order_${id % 16}
      
      keyGenerators:
        snowflake:
          type: SNOWFLAKE
          props:
            worker-id: 123


    c.配置server.yaml，配置文件如下：
    
    authentication:
      users:
        root:
          password: root
    
    props:
      max-connections-size-per-query: 1
      acceptor-size: 16   #The default value is available processors count * 2.
      executor-size: 16   #Infinite by default.
      proxy-frontend-flush-threshold: 128   #The default value is 128.
         #LOCAL: Proxy will run with LOCAL transaction.
         #XA: Proxy will run with XA transaction.
         #BASE: Proxy will run with B.A.S.E transaction.
      proxy-transaction-type: LOCAL
      proxy-opentracing-enabled: false
      proxy-hint-enabled: false
      query-with-cipher-column: true
      sql-show: true
      check-table-metadata-enabled: false
    
    d.配置完成后启动sharding-proxy,使用命令 mysql -h127.0.0.1 -P3307 -uroot -proot 连接启动的sharding-proxy，执行建表语句，会自动
    在两个库上分别创建16个表。
    
    e.手动在sharding-proxy命令窗口进行操作验证：
      问题1：由于id采用雪花算法，表id得是bigint类型，若为int类型，会抛出数组长度不足异常。
    
    f.用程序连接启动的sharding-proxy对proxy上的库test_shop中的表t_order进行操作，proxy会将真实的操作执行到具体的2个库（shop_0,shop_1）32张表上。
    
3.（选做）模拟 1000 万的订单单表数据，迁移到上面作业 2 的分库分表中。
    
    

4.（选做）重新搭建一套 4 个库各 64 个表的分库分表，将作业 2 中的数据迁移到新分库。
    
  按照课程内容，一共有全量；全量+增量；全量+增量+binlog方式。
  
  但由于是库表的分片方式发生了改变，不管哪种方式都需要通过代码或者脚本重新计算该数据分配的库，及该库对应的表中。

Week08 作业题目（周六）：
1.（选做）列举常见的分布式事务，简单分析其使用场景和优缺点。
2.（必做）基于 hmily TCC 或 ShardingSphere 的 Atomikos XA 实现一个简单的分布式事务应用 demo（二选一），提交到 Github。
3.（选做）基于 ShardingSphere narayana XA 实现一个简单的分布式事务 demo。
4.（选做）基于 seata 框架实现 TCC 或 AT 模式的分布式事务 demo。
5.（选做☆）设计实现一个简单的 XA 分布式事务框架 demo，只需要能管理和调用 2 个 MySQL 的本地事务即可，不需要考虑全局事务的持久化和恢复、高可用等。
6.（选做☆）设计实现一个 TCC 分布式事务框架的简单 Demo，需要实现事务管理器，不需要实现全局事务的持久化和恢复、高可用等。
7.（选做☆）设计实现一个 AT 分布式事务框架的简单 Demo，仅需要支持根据主键 id 进行的单个删改操作的 SQL 或插入操作的事务。