####1.从Spring到Spring Boot
配置的发展方向：
XML--全局

注解--类

配置类--方法

干掉ejb的spring，屠龙者Spring会不会成为下一个恶龙？

Spring臃肿以后的必然选择。

一切都是为了简化。

-让开发变简单；

-让配置变简单；

-让运行变简单；

怎么变简单？关键词：整合，就像是SSH、SSM，国产的SpringSide

限定性框架和非限定性框架？

非限定性框架，框架是通用的，但在各个方面都比较平庸。spring之前的定位。

限定性框架，比如搭配的脚手架，框子是定死的，避免出现一些问题。springboot的作用。

基于什么变简单：约定大于配置。

都用注解方式，不配置的取默认值，就按照springboot约束来。

为什么能做到简化：

1、Spring本身计数的成熟与完善，各方面第三方组件的成熟集成。

2、Spring团队在去Web容器化等方面的努力。实现了嵌入式的tomcat、jetty。

3、基于MAVEN与POM的Java生态体系，整合POM模板成为可能。

4、避免大量maven导入和各种版本冲突。

Spring Boot是Spring的一套快速配置脚手架，关注于自动配置，配置驱动。

什么是Spring Boot

Spring Boot使创建独立运行、生产级别的Spring应用变得容易，你可以直接运行它。我们对Spring平台和第三方库采用限定性视角，以此让大家能在最小的
成本下上手。大部分Spring Boot应用仅仅需要最少的配置。

功能特性

1.创建独立运行的Spring应用

2.直接嵌入Tomcat或jetty，Undertow，无需部署WAR包

3.提供限定性的starter依赖简化配置（就是脚手架）

4.在必要时自动化配置Spring和其他三方依赖库

5.提供生成production-ready特性，例如指标度量，健康检查，外部配置等

6.完全零代码生成和不需要XML配置

什么是脚手架？类比建筑工程中使用的脚手架，钢结构脚手架。

####2.Spring Boot核心原理*
1、自动化配置：简化配置核心
基于Configuration，EnableXX，Condition

2、spring-boot-starter：脚手架核心
整合各种第三方类库，协同工具

为什么要约定大于配置

举例来说，JVM有1000多个参数，但是我们不需要一个参数，就能java Hello。

优势在于，开箱即用：

1、Maven的目录结构：默认有resources文件夹存放配置文件。默认打包方式为jar。

2、默认的配置文件：application.properties或application.yml文件。

3、默认通过spring.profiles.active属性来决定运行环境时的配置文件。

4、enableAutoConfiguration默认对于依赖的starter进行自行装载。

5、spring-boot-starter-web中默认包含spring-mvc相关依赖以及内置的web容器，使得构建一个web应用更加简单。

spring-boot-autoconfigure spring.factories 中配置了上百个 org.springframework.boot.autoconfigure.EnableAutoConfiguration

需要pom文件引入jar且在 application.properties中配置相关参数，即可启用。

####3.Spring Boot Starter详解*

自己实现一个SpringBootStarter：
spring.factories 

spring.provides 

additional-spring-configuration-metadata.json

####4.JDBC与数据库连接池*

JDBC定义了数据库交互接口：

DriverManager

Connection

Statement

ResultSet

后来又加了DataSource--Pool

Java操作数据库的各种类库，都可以看做是在JDBC上做的增强实现

MySQL驱动JDBC接口--Connection 

数据库是系统中稀缺的资源

数据库连接池

C3P0 

DBCP--Apache 

CommonPool 

Druid 可以开启sql监控

Hikari 最快的连接池

连接池的快慢，意义不太大

字节码工具效率差异

json序列化效率差异

####5.ORM-Hibernate/MyBatis*
ORM(Object-Relational Mapping)表示对象关系映射。

Hibernate是一个开源的对象关系映射框架，它对JDBC进行了非常轻量级的对象封装，它将POJO与数据库表建立映射关系，是一个全自动的orm框架，hibernate
可以自动生成SQL语句，自动执行，使得Java程序员可以使用面向对象的思维来操纵数据库。

Hibernate里需要定义实体类和hbm映射关系文件(IDE一般有工具生成)

Hibernate里可以使用HQL、Criteria、Native SQL三种方式操作数据库。

也可以作为JPA适配实现，使用JPA接口操作。

MyBatis是一款优秀的持久层框架，它支持定制化SQL、存储过程以及高级映射。MyBatis避免了几乎所有的JDBC代码和手动设置参数以及获取结果集。
MyBatis可以使用简单的XML或注解来配置和映射原生信息，将接口和Java的POJOS(Plain Old Java Objects,普通的Java对象)映射成数据库中的记录。

需要Mapper.xml存放Mybatis对应的sql

Mybatis-半自动化ORM

1、需要使用映射文件mapper.xml定义map规则和SQL

2、需要定义mapper/DAO，基于xml规则，操作数据库

可以使用工具生成基础的mapper.xml和mapper/DAO

一个经验就是，继承生成的mapper，而不是覆盖掉

也可以直接在mapper上用注解方式配置SQL

MyBatis与Hibernate比较

MyBatis与Hibernate的区别与联系？

Mybatis优点：原生SQL(XML语法)，直观，对DBA友好

Hibernate优点：简单场景不用写SQL(HQL、Cretiria、SQL)

Mybatis缺点：繁琐，可以用Mybatis-generator、Mybatis-Plus之类的插件

Hibernate缺点：对DBA不友好

####6.Spring集成ORM/JPA*
JPA的全称是Java Persistence API，即Java持久化API，是一套基于ORM的规范，内部是由一系列的接口和抽象类构成。

JPA通过JDK5.0注解描述对象-关系表映射关系，并将运行期的实体对象持久化到数据库中。

核心EntityManager

ORM的火爆，致使从Hibernate中提取经验整理出了JPA

Spring JDBC与ORM

JDBC  |DataSource     |SpringJDBC

JPA   |EntityManager  |Spring ORM

JDBC层，数据库访问层，怎么操作事务？编程式事务管理

Spring怎么做到无侵入实现事务？声明式事务管理：事务管理器 + AOP

Spring管理事务

Spring声明式事务配置参考
事务的传播性
@Transactional(propagation=Propagation.REQUIRED)

事务的隔离级别：
@Transactional(isolation=isolation.READ_UNCOMMITTED)
读取未提交数据（会出现脏读，不可重复读）基本不使用

只读：
@Transaction(readOnly=true)
该属性用于设置当前事务是否为只读事务，设置为true表示只读，false则表示可读写，默认值为false。

事务的超时性：
@Transactional(timeout=30)

回滚：
指定单一异常类：@Transactional(rollbackFor=RuntimeException.class)

指定多个异常类：@Transactional(rollbackFor{RuntimeException.class, Exception.class})

编码原则，service层之间不要嵌套，
1.嵌套加事务的传播，使得事务变得复杂，会导致事务范围不明确；

2.会导致service间的循环依赖。

3.service层的聚合，可以再加一层进行不同service间的聚合。

多数据源处理，需要指定名称。
####7.Spring Boot集成ORM/JPA


####8.第10可总结回顾与作业实践