###学习笔记
2.（必做）按自己设计的表结构，插入 100 万订单模拟数据，测试不同方式的插入效率

3.（选做）按自己设计的表结构，插入 1000 万订单模拟数据，测试不同方式的插入效

尝试使用存储过程和普通jdbc操作进行比较
````
CREATE DEFINER=`root`@`%` PROCEDURE `insertOrder`(start_i int,num int)
BEGIN
	DECLARE i INT DEFAULT 0;
	DECLARE d TIMESTAMP DEFAULT now();
	DECLARE EXIT HANDLER FOR SQLEXCEPTION BEGIN ROLLBACK; SELECT -1; END;
	START TRANSACTION;
		REPEAT
			SET i = i + 1;
			SET d = ADDDATE('2020-01-01', FLOOR(RAND() * 365));
			INSERT INTO `order` VALUES(start_i + i,FLOOR(1 + RAND() * 10000),FLOOR(1 + RAND() * 2000),FLOOR(1 + RAND() * 10),0,d,d);
		UNTIL i = num END REPEAT;
	COMMIT;
END
````

CALL insertOrder(0, 1000000);

jdbc 100w 一次性插入效率太低下。
#### 对比两种插入效率

| 插入方式  | 数据量 | 耗时(s) |
| --------- | ------ | ------- |
| 存储过程  | 100W   | 33.13    |
| Java JDBC | 100W   | 4737.304  16.312(加了rewriteBatchedStatements=true参数后的表现)         |
| 存储过程  | 1000W  | 315.85   |
| Java JDBC | 1000W  |      187.460(加了rewriteBatchedStatements=true参数后的表现)   |

spring.datasource.url=jdbc:mysql://127.0.0.1:3306/shop?characterEncoding=utf8&rewriteBatchedStatements=true&useSSL=false&serverTimezone=Asia/Shanghai

很好奇 rewriteBatchedStatements=true 做了什么黑魔法？

https://blog.csdn.net/qq271859852/article/details/79562262
