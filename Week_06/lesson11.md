###Java Lambda表达式

面向对象与面向函数。

Java里，函数不是第一等公民，需要封装到接口里。

从而Java Lambda表达式 -> 内部匿名类。

方法签名。

两种函数。

只有一行时可以省略大括号

(parameters) -> expression
或
(parameters) -> {statement;}

//1.不需要参数，返回值为5

() -> 5

//2.接收一个参数(数字类型)，返回其2倍的值

x -> 2*x

//3.接受2个参数（数字），并返回他们的差值

(x, y) -> x - y

//4.接收2个int型整数，返回他们的和

(int x, int y) -> x + y

//5.接受一个string对象，并在控制台打印，不返回任何值(看起来像是返回void)

(String s) -> System.out.println(s);

接口只有一个方法时，可以直接使用lambda表达式。
由于lambda没有名称，接口有多个待实现的方法时，则不行。


什么是流

Stream（流）是一个来自数据源的元素队列并支持聚合操作

元素：特定类型的对象，形成一个队列。Java中的Stream并不会存储元素，而是按需计算。

数据源：流的来源。可以是集合，数组，I/O channel，产生器generator等。

聚合操作类似SQL语句一样的操作，比如filter，map，reduce，find，match，sorted等。

和以前的Collection操作不同，Stream操作还有两个基础的特征：

Pipelining:中间操作都会返回流对象本身。这样多个操作可以串联成一个管道，如同流式风格(fluent style)。这样做可以对操作进行优化，
比如延迟执行(laziness)和短路(short-circuiting)。

内部迭代：以前对集合遍历都是通过Iterator或者For-Each的方式，显示的在集合外这叫做外部迭代。Stream提供了内部迭代的方式，通过访问者
模式（Visitor）实现。

####Stream操作
>中间操作：

1、选择与过滤
filter(Predicate p)接收Lambda，从流中排除某些元素。

distinct(),筛选通过流所生成元素的hashCode()和equals()去除重复元素limit(long maxSize)截断流，使其元素不超过给定数量。
skip(long n)跳过元素，返回一个扔掉了前n个元素的流。若流中元素不足n个，则返回一个空流。

2、映射
map(Function f)接收Lambda，将元素转换成其他形式或提取信息；接收一个函数作为参数，该函数会被应用到每个元素上，并将其映射成一个新的元素。

mapToDouble(ToDoubleFunction f)接收一个函数作为参数，该函数会被应用到每个元素上，产生一个新的DoubleStream。

mapToInt(ToIntFunction f)接收一个函数作为参数，该函数会被应用到每个元素上，产生一个新的IntStream。

mapToLong(ToLongFunction f)接收一个函数作为参数，该函数会被应用到每个元素上，产生一个新的LongStream。

flatMap(Function f)接收一个函数作为参数，将流中的每个值都换成另一个流，然后把所有流连接成一个流。

3、排序

sorted()产生一个新流，其中按自然顺序排序

sorted(Comparator comp)产生一个新流，其中按比较器顺序排序

>终止操作：

1.查找与匹配  allMatch   检查是否匹配所有元素
            anyMatch   检查是否至少匹配一个元素
            noneMatch  检查是否没有匹配的元素
            findFirst  返回第一个元素
            findAny    返回当前流中的任意元素
            count      返回流中元素的总个数
            max        返回流中最大值
            min        返回流中最小值

2.归约 reduce，需要初始值(类比Map-Reduce)

3.收集collect
                toList List<T> 把流中元素收集到List
                toSet Set<T> 把流中元素收集到Set
                toCollection collection<T> 把流中元素收集到创建的集合
                count 计算流中元素的个数
                summaryStatistic 统计最大最小平均值

4.迭代forEach

>Lombok是什么

Lombok是基于jsr269实现的一个非常神奇的Java类库，会利用注解自动生成java Bean中烦人的get、set方法及有参无参构造函数，还能自动生成
logger、ToString、HashCode、Builder等Java特色的函数或是符合设计模式的方法，能够让你Java Bean更简洁，更美观。

基于字节码增强，编译期处理

可以配置开发工具IDE或Maven使用。

Stream大大的简化了编程的代码


>什么是Guava

Guava是一种基于开源的Java库，其中包含谷歌正在由他们很多项目使用的很多核心库。这个库是为了方便编码，并减少编码错误。这个库提供
用于集合，缓存，支持原语，并发性，常见注解，字符串处理，I/O和验证的实用方法。

Guava的好处

标准化-Guava库是由谷歌托管。

高效-可靠，快速和有效的扩展JAVA标准库。

优化-GUAVA库经过高度的优化。

JDK8里的一些更新特性源于Guava

####集合[Collection]
Guava对JDK集合的扩展，这时Guava最成熟和为人所知的部分。

1.不可变集合：用不变的集合进行防御性编程和性能提升。

2.新集合类型：multisets，multimaps，tables，bidrectional maps等。

3.强大的集合工具类：提供java.util.Collections中没有的集合工具

4.扩展工具类：让视线的扩展集合类变得更容易，比如创建Collection的装饰器，或视线迭代器。

####缓存[Cache]

本地缓存视线，支持多种缓存过期策略。

####并发[Concurrency]

ListenableFuture:完成后触发回调的Future

####字符串处理[Strings]
非常有用的字符串工具，包括分割、连接、填充等操作
####事件总线[EventBus]
发布-订阅模式的组件通信，进程内模块间解耦
####反射[Reflection]
Guava的Java反射机制工具类

面向对象设计原则SOLID

SOLID是面向对象设计和编程(OOD&OOP)中几个重要编码原则


如何做单元测试

Junit -> TestCase, TestSuite, Runner

SpringTest

Mock技术

-Mockito

-easyMock

####如何做单元测试

1.单元测试方法应该每个方法是一个case，断言充分，提示明确。

2.单侧要覆盖所有的corner case

3.充分使用mock(一切皆可mock)

4.如果发现不好测试，则说明业务代码设计存在问题，可以反向优化代码

5.批量测试用例使用参数化单元测试

6.注意测试是单线程执行

7.合理使用before，after，setup准备环境

8.合理使用通用测试基类

9.配合checkstyle，coverage等工具

10.制定单元测试覆盖率基线


####单元测试的常见陷阱与经验

1.尽量不要访问外部数据库等外部资源

2.如果必须用数据库考虑用嵌入式DB+事务自动回滚

3.防止静态变量污染导致测试无效

4.小心测试方法的顺序导致的不同环境测试失败

5.单元测试总时间特别长的问题

####单元测试的常见陷阱与经验
1.尽量不要访问外部数据库等外部资源

2.如果必须用数据库考虑用嵌入式DB+事务自动回滚

3.防止静态变量污染导致测试无效

4.小心测试方法的顺序导致的不同环境测试失败，windows，linux，类中方法执行顺序存在系统的随机性

5.单元测试总时间特别长的问题  单元测试合理，执行时间长不是问题