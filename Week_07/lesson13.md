>如何避免高并发，mysql死锁

1.sql尽量简单；

2.修改，删除尽量不要操作范围；

>修改表结构的危害

1.索引重建

2.锁表

3.抢占资源

4.主从延时

>大批量写入的优化

PreparedStatement减少SQL解析

Multiple Values/Add Batch 减少交互

Load Data，直接导入

索引和约束问题，先清除去除索引和约束，导入数据后再加索引和约束

>连接查询

驱动表的选择问题  小表，减少捞数据的容量

避免笛卡尔积

>索引失效

NULL not、not in、函数等

减少使用or，可以用union（注意union all的区别），以及前面提到的like

大数据两下，放弃所有条件组合都走索引的幻想，出门左拐“全文检索”

必要时可以用force index来强制走索引

>查询SQL到底怎么设计？

查询数据量和查询次数的平衡

避免不必须的大量重复数据传输

避免使用临时文件排序或临时表

分析类需求，可以用汇总表

>怎么实现主键ID

自增

sequence

模式seq

UUID

时间戳/随机数

snowflake

>高效分页

分页：count/pageSize/pageNum，带条件的查询语句

常见实现-分页插件：使用查询SQL，嵌套一个count，性能的坑？

改进一下1，重写count

大数量级分页的问题，limit 10000,20

改进一下2，反序

继续改进3，技术向：带id

继续改进4，需求向：非精确分页

所有条件组合？索引？

>乐观锁和悲观锁
