###Spring技术发展
为什么产生Spring：干掉复杂庞大的EJB，迎来Java的春天。

作者是Rod Johnson 音乐博士：编程思想的重要性。

###Spring 框架设计
什么是框架：不解决业务问题，类比房子最初始的骨架。

架构：类比盖房子前设计的图纸、比如说房高、面宽、那里放承重墙。

自己理解的Spring框架是什么呢？ 对象容器、AOP切面编程、bean的生命周期管理。

Spring framework 6大模块

1.Core:Bean/Context/AOP
2.Testing:Mock/TextContext
3.DataAccess:Tx/JDBC/ORM
4.Spring MVC/WebFlux:web
以上4个常用模块
5.Integration:remoting/JMS/WS
6.Languages:Kotlin/Groovy

引入Spring意味着引入了一种研发协作模式

###Spring AOP
AOP-面向切面编程

Spring早期版本的核心功能，管理对象生命周期与对象装配。

为了实现管理和装配，一个自然而然的想法就是，加一个中间层代理（字节码增强）来实现所有对象的托管。

IOC-控制反转

也称为DI(Dependency Injection)依赖注入

接口类型  默认使用JdkProxy  com.sun.proxy.$Proxy
         proxyTargetClass EnhancerBySpringCGLIB

非接口类型  默认使用CGlib    EnhancerBySpringCGLIB

有两种方式：（CGLIB、Java Proxy）对原Java类进行代理处理，无法改变原Java类。
          （Instrumentation JavaAgent）在Java类加载之前，对类进行字节码的修改，直接生成新的字节码的类。

字节码增强新工具：ByteBuddy提供了更友好的操作API

使用JavaAgent在APM系统上，存在的风险是生成的类不够稳定。无法对原有代码进行改动，可以考虑使用Instrument。

###Spring Bean 核心原理
Bean的加载过程

构造函数 -> 依赖注入 -> BeanNameAware -> BeanFactoryAware -> ApplicationContextAware -> BeanPostProcessor前置方法
-> InitializingBean -> 自定义init方法 -> BeanPostProcessor后置方法 -> 使用 -> DisposableBean -> 自定义destroy方法 

为什么设计的这么复杂：Spring作为一个通用框架想要加载所有的类，需要这么复杂。

总体分为4大块：

1)创建对象

2)属性赋值

3)初始化

4)注销接口注册

复杂的对象使用FactoryBean来进行初始化。

###Spring XML 配置原理

自定义标签 schema Location  [spring.handler,spring.schemas] [检查XML配置是否正确,从DOM节点parse对象]  Bean

自动化XML配置工具： XmlBeans -> Spring-xbean （支持Xml文件和POJO对象相互之间的转换）

2个原理：
1、根据Bean的字段结构，自动生成XSD
2、根据Bean的字段结构，配置XML文件

XML/@AutoWire 1.0/2.0 XML配置/注解注入

@Service 2.5 半自动注解配置

@Bean/@Configuration 3.0 Java Congfig 配置

@Condition/@AutoConfigureX  4.0/SpringBoot 全自动注解配置

###Spring Messaging等技术

同步转异步

点对点的模式 一个消息 一次消费

发布订阅模式 一个消息 多人订阅