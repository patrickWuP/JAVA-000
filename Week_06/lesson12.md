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