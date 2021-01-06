Netty运行原理：

Boss Group：

Worker Group：

EventLoop

Executor Group

关键对象

Bootstrap：启动线程，开启socket

EventLoopGroup

EventLoop

SocketChannel：连接

ChannelInitializer：初始化

ChannelPipeline：处理器链

ChannelHandler：处理器


单线程高效的秘诀是不能被卡住。

粘包和拆包的问题：
都是人为问题：

规范好什么是完整的一段数据。

ByteToMessageDecoder提供的一些常见的实现类：

1.FixedLengthFrameDecoder：定长协议解码器，我们可以指定固定的字节数算一个完整的报文。

2.LineBasedFrameDecoder：行分隔符解码器，遇到\n或者\r\n，则认为是一个完整的报文。

3.DelimiterBasedFrameDecoder：分隔符解码器，分隔符可以自己指定。

4.LengthFieldBasedFrameDecoder：长度编码解码器，将报文划分为报文头/报文体。

5.JsonObjectDecoder：json格式解码器，当检测到匹配数量的"{"、"}"或"["、"]"时，则认为时一个完整的
json对象或者json数组。

断点续传问题：
1.无法断点续传，则下载出现异常，则需要从头开始下载。

网络拥堵与Nagle算法优化

TCP_NODELAY：关闭Nagie算法优化

MTU：Maxitum Transmission Unit 最大传输单元------------------1500Byte

MSS：Maxitum Segment Size 最大分段大小-----------------------1460Byte + tcp头（20Byte） + ip头（20Byte）

优化条件：使得一趟能发送尽量多的数据，使其效率最大化。

-缓冲区满

-达到超时

### 连接优化
三次连接：SYN（请求，在吗）、ACK(响应，在)、谁收到ack谁就能将传输状态置为可用状态。
四次挥手：

TCP连接必须经过时间2MSL后才真正释放掉。

TIME-WAIT：客户端等待2MSL才进行关闭。

优化：不要阻塞EventLoop、降低等待优化、  


### 典型应用：API网关

四大职能：

请求介入 -> 作为所有API接口服务请求的接入点。

业务聚合 -> 作为所有后端业务服务的聚合点。

中介策略 -> 实现安全、验证、路由、过滤、流控等策略。

统一管理 -> 对所有API服务和策略进行统一管理。

网关的分类：


流量网关(关注稳定与安全) -> 全局性流控、日志统计、防止SQL注入、防止Web攻击、屏蔽工具扫描、黑白IP名单、证书/加解密处理。

性能非常好，适合流量网关（OpenResty、Kong）

业务网关(提供更好的服务) -> 服务级别流控、服务降级与熔断、路由与负载均衡和灰度策略、服务过滤和聚合与发现、权限验证与用户等级策略、
业务规则与参数校验、多级缓存策略。

扩展性好，适合业务网关，二次开发（Spring Cloud Gateway、Zuul2、Netflix oss）

### Netty对三种模式的支持
Reactor单线程模式 
~~~~
EventLoopGroup eventGroup = new NioEventLoopGroup(1);
ServerBootstrap serverBootstrap = new ServerBootstrap();
serverBootstrap.group(eventGroup);
~~~~
非主从Reactor多线程模式
~~~~
EventLoopGroup eventGroup = new NioEventLoopGroup();
ServerBootstrap serverBootstrap = new ServerBootstrap();
serverBootstrap.group(eventGroup);
~~~~
主从Reactor多线程模式
~~~~
EventLoopGroup bossGroup = new NioEventLoopGroup();
EventLoopGroup workerGroup = new NioEventLoopGroup();
ServerBootstrap serverBootstrap = new ServerBootstrap();
serverBootstrap.group(bossGroup,workerGroup);
~~~~

高效利用单线程，将任务放到队列中，单线程不停的轮询去执行任务，就好像任务同时并发执行一样，
类似单cpu分时间片处理，但是eventLoop被阻塞了会影响后续的。

类似redis是单线程，数据在内存中，qps能达到10k，但是有范围查询或者复杂的lua脚本计算会导致单线程被卡主。
这段时间查询效率降低。

最好是业务处理线程池和io处理线程池是完全隔离的。

####架构设计：

设计：技术复杂度与业务复杂度

抽象：概念理清、正确命名

组合：组件之间的相互关系