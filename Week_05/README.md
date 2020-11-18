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

git出现了错误，无法提交，代码如下

error: inflate: data stream error (incorrect header check)
error: unable to unpack 4148ec4478c8fa5eeae3858a976e4a62913ec85a header
fatal: loose object 4148ec4478c8fa5eeae3858a976e4a62913ec85a (stored in .git/objects/41/48ec4478c8fa5eeae3858a976e4a62913ec85a) is corrupt
Updating 28a28f4..fc28dfb

``
public class HikariJdbc {
    String createTableSql = "CREATE TABLE STUDENT(id INT PRIMARY KEY,name VARCHAR(100))";
    private static final String JDBC_URL = "jdbc:mysql:localhost:3306/demo";
    private static final String USER = "root";
    private static final String PASSWORD = "root";
    private static final String DRIVER_CLASS = "com.mysql.jc.driver";
    private static final String POOL_SIZE = "10";

    public static void main(String[] args) throws Exception {
        Properties properties = new Properties();
        properties.setProperty("jdbcUrl", JDBC_URL);
        properties.setProperty("driverClassName", DRIVER_CLASS);
        properties.setProperty("dataSource.user", USER);
        properties.setProperty("dataSource.password", PASSWORD);
        properties.setProperty("dataSource.maximumPoolSize", POOL_SIZE);
        HikariConfig hikariConfig = new HikariConfig(properties);
        HikariDataSource hikariDataSource = new HikariDataSource(hikariConfig);
        Connection conn = hikariDataSource.getConnection();

        String sql = "INSERT INTO STUDENT VALUES(?, ?)";
        PreparedStatement preparedStatement = conn.prepareStatement(sql);
        String[] names = {"张飞", "关羽", "刘备"};
        // 批量插入
        for (int i = 0; i < names.length; i++) {
            preparedStatement.setInt(1, i + 1);
            preparedStatement.setString(2, names[i]);
            preparedStatement.addBatch();
        }
        preparedStatement.executeBatch();
        // 删
        sql = "DELETE FROM STUDENT WHERE name=?";
        preparedStatement = conn.prepareStatement(sql);
        preparedStatement.setString(1, "张飞");
        preparedStatement.execute();
        // 改增加事务
        try {
            conn.setAutoCommit(false);
            sql = "UPDATE STUDENT SET name=? WHERE name=?";
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, "关羽2");
            preparedStatement.setString(2, "关羽");
            preparedStatement.execute();
            Integer a = null;
            conn.commit();
            a.toString();
        } catch (Exception e) {
            conn.rollback();
        }
        // 查
        sql = "SELECT * FROM STUDENT WHERE id=?";
        preparedStatement = conn.prepareStatement(sql);
        preparedStatement.setInt(1, 3);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            System.out.println(resultSet.getString("id") + "," + resultSet.getString("name"));
        }
        // 查数据集
        sql = "SELECT * FROM STUDENT";
        preparedStatement = conn.prepareStatement(sql);
        ResultSet rs = preparedStatement.executeQuery();
        while (rs.next()) {
            System.out.println(rs.getString("id") + "," + rs.getString("name"));
        }
        //释放资源
        preparedStatement.close();
        //关闭连接
        hikariDataSource.close();
    }
}
``

