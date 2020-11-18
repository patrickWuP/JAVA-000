###第9课作业实践
1.(选做)使用Java里的动态代理，实现一个简单的AOP。
详见jdkproxy包下的代码
2.(必做)写代码实现Spring Bean的装配，方式越多越好(XML、Annotation都可以)，提交到Github。
(1)在XML中进行显示配置
(2)在Java中进行显示配置
(3)隐式的bean发现和自动装配

https://github.com/patrickWuP/JAVA-000/tree/main/Week_05/fx/src/main/java/com/wp/beanassemble

以上方式非互斥可以混合使用。见beanassemble包下示例.

3.（选做）实现一个Spring XML自定义配置，配置一组Bean，例如Student/Klass/School。

https://github.com/patrickWuP/JAVA-000/tree/main/Week_05/fx/src/main/java/com/wp/customxsd

###第10课作业实践
1.（选做）总结一下，单例的各种写法，比较它们的优劣。

饿汉式：类加载阶段便初始化好对象，类加载阶段是线程安全的，无需考虑并发问题。

懒汉式：需要时才进行创建，由于可能会出现并发问题，使用double check加volatile。

饿汉式：缺点 -> 应用启动时就得初始化对象，会导致启动时间边长。优点 -> 用对象时直接获取即可。

懒汉式：缺点 -> 第一次使用对象时还需要进行初始化过程。优点 -> 减少应用启动初始化对象的数量。

2.（选做）maven/spring 的 profile 机制，都有什么用法？

根据传递的参数值，获取不同配置，例如区分各个环境的数据库配置。

3.（选做）总结 Hibernate 与 MyBatis 的各方面异同点。

Hibernate：

MyBatis：

4.（必做）给前面课程提供的 Student/Klass/School 实现自动配置和 Starter。



5.（选做）学习 MyBatis-generator 的用法和原理，学会自定义 TypeHandler 处理复杂类型。

6.（必做）研究一下 JDBC 接口和数据库连接池，掌握它们的设计和用法：

1）使用 JDBC 原生接口，实现数据库的增删改查操作。

2）使用事务，PrepareStatement 方式，批处理方式，改进上述操作。

3）配置 Hikari 连接池，改进上述操作。提交代码到 Github。
