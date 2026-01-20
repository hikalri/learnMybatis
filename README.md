# MyBatis 学习项目

这是一个原生MyBatis学习项目，涵盖了MyBatis的核心功能和高级特性。项目通过用户-部门-订单的业务场景，演示了MyBatis在实际开发中的应用。

## 项目结构

```
src/main/java/com/example/demo/
├── entity/          # 实体类
│   ├── User.java    # 用户实体
│   ├── Department.java  # 部门实体
│   └── Order.java   # 订单实体
├── mapper/          # Mapper接口
│   ├── UserMapper.java
│   ├── DepartmentMapper.java
│   └── OrderMapper.java
├── service/         # 服务层
│   ├── UserService.java
│   ├── DepartmentService.java
│   └── OrderService.java
├── utils/           # 工具类
│   ├── SqlSessionFactoryUtil.java
│   ├── Page.java
│   └── PageParam.java
├── CacheTest.java   # 缓存测试
├── LazyLoadingTest.java  # 延迟加载测试
└── RelationTest.java     # 关系映射测试

src/main/resources/
├── mybatis-config.xml    # MyBatis配置文件
├── logback.xml          # 日志配置
├── ehcache.xml          # 缓存配置
└── com/example/demo/mapper/  # Mapper XML文件
    ├── UserMapper.xml
    ├── DepartmentMapper.xml
    └── OrderMapper.xml
```

## 1. MyBatis 配置

### 1.1 类型别名 (Type Aliases)

在 [`mybatis-config.xml`](src/main/resources/mybatis-config.xml#L20-L23) 中配置了类型别名：

```xml
<!-- 类型别名 -->
<typeAliases>
    <package name="com.example.demo.entity"/>
</typeAliases>
```

这样配置后，在Mapper XML中可以直接使用类名而不需要写完整的包名，例如：
- `type="User"` 代替 `type="com.example.demo.entity.User"`
- `type="Department"` 代替 `type="com.example.demo.entity.Department"`

### 1.2 环境配置 (Environments)

在 [`mybatis-config.xml`](src/main/resources/mybatis-config.xml#L25-L37) 中配置了数据库环境：

```xml
<!-- 环境配置 -->
<environments default="development">
    <environment id="development">
        <transactionManager type="JDBC"/>
        <dataSource type="POOLED">
            <property name="driver" value="com.mysql.cj.jdbc.Driver"/>
            <property name="url" value="jdbc:mysql://localhost:3306/mydb?..."/>
            <property name="username" value="username"/>
            <property name="password" value="123456"/>
        </dataSource>
    </environment>
</environments>
```

- `transactionManager`: 指定事务管理器类型，JDBC表示使用JDBC的事务管理
- `dataSource`: 指定数据源类型，POOLED表示使用连接池

### 1.3 映射器 (Mappers)

在 [`mybatis-config.xml`](src/main/resources/mybatis-config.xml#L39-L42) 中配置了映射器：

```xml
<!-- 映射器 -->
<mappers>
    <package name="com.example.demo.mapper" />
</mappers>
```

使用包扫描方式注册Mapper，MyBatis会自动扫描指定包下的所有Mapper接口。

## 2. MyBatis生命周期和作用域

### 2.1 SqlSessionFactory

[`SqlSessionFactoryUtil`](src/main/java/com/example/demo/utils/SqlSessionFactoryUtil.java) 类展示了SqlSessionFactory的创建和使用：

```java
public class SqlSessionFactoryUtil {
    private static SqlSessionFactory sqlSessionFactory;

    static {
        try {
            String resource = "mybatis-config.xml";
            InputStream inputStream = Resources.getResourceAsStream(resource);
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static SqlSessionFactory getSqlSessionFactory() {
        return sqlSessionFactory;
    }
}
```

- **生命周期**: 应用程序整个生命周期
- **作用域**: 全局单例，一个数据库对应一个SqlSessionFactory
- **创建**: 通过SqlSessionFactoryBuilder创建

### 2.2 SqlSession

在 [`UserService`](src/main/java/com/example/demo/service/UserService.java#L19) 中展示了SqlSession的使用：

```java
try (SqlSession sqlSession = SqlSessionFactoryUtil.getSqlSessionFactory().openSession()) {
    UserMapper mapper = sqlSession.getMapper(UserMapper.class);
    // 执行数据库操作
    sqlSession.commit(); // 提交事务
}
```

- **生命周期**: 请求或方法级别
- **作用域**: 方法或请求级别，非线程安全
- **使用**: 每次数据库操作都应该创建新的SqlSession

### 2.3 Mapper

Mapper接口的生命周期与SqlSession绑定，通过SqlSession获取：

```java
UserMapper mapper = sqlSession.getMapper(UserMapper.class);
```

- **生命周期**: 与SqlSession相同
- **作用域**: 方法级别
- **特点**: 接口代理实现，线程不安全

## 3. resultMap结果映射

### 3.1 基本结果映射

在 [`UserMapper.xml`](src/main/resources/com/example/demo/mapper/UserMapper.xml#L7-L13) 中定义了基本结果映射：

```xml
<resultMap id="userResultMap" type="User">
    <id property="id" column="id" />
    <result property="name" column="name" />
    <result property="email" column="email" />
    <result property="age" column="age" />
    <result property="departmentId" column="department_id" />
</resultMap>
```

- `id`: 标识主键字段
- `result`: 标识普通字段
- `property`: Java实体属性名
- `column`: 数据库列名

### 3.2 继承结果映射

在 [`UserMapper.xml`](src/main/resources/com/example/demo/mapper/UserMapper.xml#L16-L23) 中展示了结果映射的继承：

```xml
<resultMap id="userWithDepartmentResultMap" type="User" extends="userResultMap">
    <association
        property="department"
        resultMap="com.example.demo.mapper.DepartmentMapper.departmentResultMap"
        columnPrefix="dept_"
    />
</resultMap>
```

- `extends`: 继承已有的结果映射
- `columnPrefix`: 为关联表的列名添加前缀，避免列名冲突

## 4. 日志工厂

项目使用Logback作为日志实现，在 [`logback.xml`](src/main/resources/logback.xml) 中配置：

```xml
<!-- 控制台输出 -->
<appender name="STDOUT" class="ch.qos.logback.core.ConsoleAppender">
    <encoder>
        <pattern>%d{yyyy-MM-dd HH:mm:ss} [%thread] %-5level %logger{36} - %msg%n</pattern>
        <charset>UTF-8</charset>
    </encoder>
</appender>

<!-- MyBatis日志级别 -->
<logger name="com.example.demo" level="DEBUG" additivity="false">
    <appender-ref ref="STDOUT"/>
    <appender-ref ref="FILE"/>
</logger>
```

MyBatis通过SLF4J接口输出日志，可以记录：
- SQL语句
- 参数信息
- 执行结果
- 缓存命中情况

## 5. 分页

### 5.1 分页参数

项目实现了简单的分页功能，使用 [`PageParam`](src/main/java/com/example/demo/utils/PageParam.java) 类封装分页参数：

```java
@Data
public class PageParam {
    private int pageNum = 1;    // 当前页码，默认为1
    private int pageSize = 10;   // 每页大小，默认为10

    public int getOffset() {
        return (pageNum - 1) * pageSize;
    }
}
```

### 5.2 分页查询

在 [`UserMapper.xml`](src/main/resources/com/example/demo/mapper/UserMapper.xml#L78-L80) 中实现了分页查询：

```xml
<select id="selectByPage" resultMap="userResultMap">
    SELECT id, name, email, age, department_id
    FROM user LIMIT #{offset}, #{pageSize}
</select>
```

### 5.3 分页结果

使用 [`Page`](src/main/java/com/example/demo/utils/Page.java) 类封装分页结果：

```java
@Data
public class Page<T> {
    private List<T> data;      // 分页数据
    private long total;         // 总记录数
    private int pageNum;        // 当前页码
    private int pageSize;       // 每页大小
    private int totalPages;     // 总页数
    private boolean hasNext;    // 是否有下一页
    private boolean hasPrevious; // 是否有上一页
}
```

## 6. Lombok

项目使用Lombok简化实体类代码，在 [`pom.xml`](pom.xml#L56-L62) 中添加了依赖：

```xml
<!-- Lombok -->
<dependency>
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok</artifactId>
    <version>1.18.30</version>
    <scope>provided</scope>
</dependency>
```

在实体类中使用Lombok注解，如 [`User.java`](src/main/java/com/example/demo/entity/User.java#L10-L12)：

```java
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {
    private Long id;
    private String name;
    // ...
}
```

- `@Data`: 自动生成getter、setter、toString、hashCode和equals方法
- `@NoArgsConstructor`: 生成无参构造函数
- `@AllArgsConstructor`: 生成全参构造函数

## 7. 多对一、一对多处理

### 7.1 多对一关系

用户与部门是多对一关系，在 [`UserMapper.xml`](src/main/resources/com/example/demo/mapper/UserMapper.xml#L16-L23) 中使用`association`处理：

```xml
<resultMap id="userWithDepartmentResultMap" type="User" extends="userResultMap">
    <association
        property="department"
        resultMap="com.example.demo.mapper.DepartmentMapper.departmentResultMap"
        columnPrefix="dept_"
    />
</resultMap>
```

### 7.2 一对多关系

用户与订单是一对多关系，在 [`UserMapper.xml`](src/main/resources/com/example/demo/mapper/UserMapper.xml#L26-L35) 中使用`collection`处理：

```xml
<resultMap id="userWithOrdersResultMap" type="User" extends="userResultMap">
    <collection property="orders" ofType="Order">
        <id property="id" column="order_id" />
        <result property="orderNo" column="order_no" />
        <result property="amount" column="order_amount" />
        <result property="userId" column="order_user_id" />
        <result property="orderTime" column="order_time" />
    </collection>
</resultMap>
```

### 7.3 延迟加载

在 [`UserMapper.xml`](src/main/resources/com/example/demo/mapper/UserMapper.xml#L55-L68) 中实现了延迟加载：

```xml
<resultMap id="userWithLazyDepartmentAndOrdersResultMap" type="User" extends="userResultMap">
    <association
        property="department"
        column="department_id"
        select="com.example.demo.mapper.DepartmentMapper.selectById"
        fetchType="lazy"
    />
    <collection
        property="orders"
        column="id"
        select="com.example.demo.mapper.OrderMapper.selectByUserId"
        fetchType="lazy"
    />
</resultMap>
```

- `fetchType="lazy"`: 设置延迟加载
- `select`: 指定延迟加载时执行的SQL语句
- `column`: 传递给延迟加载SQL的参数

## 8. 动态SQL

### 8.1 if条件判断

在 [`OrderMapper.xml`](src/main/resources/com/example/demo/mapper/OrderMapper.xml#L47-L61) 中使用了`if`条件判断：

```xml
<update id="update" parameterType="Order">
    UPDATE orders
    <set>
        <if test="orderNo != null">
            order_no = #{orderNo},
        </if>
        <if test="amount != null">
            amount = #{amount},
        </if>
        <if test="userId != null">
            user_id = #{userId},
        </if>
    </set>
    WHERE id = #{id}
</update>
```

### 8.2 set标签

`set`标签可以智能地处理逗号，避免SQL语法错误。

## 9. 缓存

### 9.1 一级缓存

MyBatis默认开启一级缓存（SqlSession级别），在 [`CacheTest.java`](src/main/java/com/example/demo/CacheTest.java) 中演示了一级缓存的使用：

```java
// 第一次查询，从数据库获取
userMapper.selectAll();

// 第二次查询，从缓存获取
userMapper.selectAll();
```

### 9.2 二级缓存

在 [`mybatis-config.xml`](src/main/resources/mybatis-config.xml#L11) 中开启了二级缓存：

```xml
<!-- 开启二级缓存 -->
<setting name="cacheEnabled" value="true"/>
```

在Mapper XML中配置二级缓存，如 [`UserMapper.xml`](src/main/resources/com/example/demo/mapper/UserMapper.xml#L4)：

```xml
<cache type="org.mybatis.caches.ehcache.EhcacheCache" />
```

### 9.3 Ehcache配置

在 [`ehcache.xml`](src/main/resources/ehcache.xml) 中配置了Ehcache：

```xml
<defaultCache
    maxEntriesLocalHeap="1000"
    eternal="false"
    timeToIdleSeconds="300"
    timeToLiveSeconds="600"
    diskSpoolBufferSizeMB="30">
    <persistence strategy="localTempSwap"/>
</defaultCache>
```

- `maxEntriesLocalHeap`: 内存中最大缓存对象数
- `timeToIdleSeconds`: 对象空闲时间
- `timeToLiveSeconds`: 对象存活时间

## 运行项目

1. 创建数据库并执行 [`database.sql`](database.sql) 脚本
2. 修改 [`mybatis-config.xml`](src/main/resources/mybatis-config.xml#L31-L34) 中的数据库连接信息
3. 运行测试类：
   - [`RelationTest.java`](src/main/java/com/example/demo/RelationTest.java): 测试关系映射
   - [`LazyLoadingTest.java`](src/main/java/com/example/demo/LazyLoadingTest.java): 测试延迟加载
   - [`CacheTest.java`](src/main/java/com/example/demo/CacheTest.java): 测试缓存

## 总结

本项目涵盖了MyBatis的核心功能和高级特性，包括：
- 基本配置和使用
- 结果映射和关系处理
- 动态SQL和分页
- 缓存机制
- 延迟加载

通过学习本项目，可以深入理解MyBatis的工作原理和最佳实践。