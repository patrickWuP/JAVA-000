###学习笔记

>Netty

网络应用开发框架

1、异步

2、事件驱动

3、基于NIO

适用于：

服务端

客户端

TCP/UDP

Channel           通道，Java NIO中的基础概念，代表一个打开的连接，可执行读取/写入IO操作。Netty对Channel的所有IO操作都是非阻塞的。

ChannelFuture     Java的Future接口，只能查询操作的完成情况，或者阻塞当前线程等待操作完成。Netty封装一个ChannelFuture接口。
                  我们可以将回调方法传给ChannelFuture，在操作完成时自动执行。
                
Event & Handler   Netty基于事件驱动，事件和处理器可以关联到入站和出站数据流。

Encoder & Decoder 处理网络IO时，需要进行序列化和反序列化，转换Java对象与字节流。
                  对入站数据进行解码，基类是ByteToMessageDecoder。
                  对出站数据进行编码，基类是MessageToByteEncoder。
                  
ChannelPipeline   数据处理管道就是事件处理器链。
                  有顺序、同一Channel的出站处理器和入站处理器在同一列表中。

>Event & Handler

入站事件：          

通道激活和停用      

读操作事件         

异常事件     

用户事件

出站事件：

打开连接

关闭连接

写入数据

刷新数据

事件处理程序接口：

ChannelHandler

ChannelOutboundHandler

ChannelInboundHandler

适配器（空实现，需要继承使用）

ChannelInboundHandlerAdapter

ChannelOutboundHandlerAdapter

Netty应用组成：

网络事件

应用程序逻辑事件

事件处理程序

>网关的设计与实现

网关实现的关键部分

[InBound -> filters -> routers -> outBound]  backend    


Reactor模式首先是事件驱动的，有一个或者多个并发输入源，有一个Service Handler和多个EventHandlers。

这个Service Handler会同步的将输入的请求多路复用的分发给相应的Event Handler。 


从Reactor模型到Netty NIO模型

