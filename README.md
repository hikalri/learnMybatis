# MyBatis 学习项目

这是一个简单的 MyBatis 学习项目，使用 Maven 构建和 JDK 1.8 环境。

## 项目结构

```
mybatis-demo/
├── pom.xml                          # Maven 配置文件
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── example/
│   │   │           └── demo/
│   │   │               ├── DemoApplication.java      # 主程序类
│   │   │               ├── entity/
│   │   │               │   └── User.java              # 用户实体类
│   │   │               ├── mapper/
│   │   │               │   └── UserMapper.java        # 用户 Mapper 接口
│   │   │               └── utils/
│   │   │                   └── SqlSessionFactoryUtil.java # SqlSessionFactory 工具类
│   │   └── resources/
│   │       ├── mybatis-config.xml                    # MyBatis 配置文件
│   │       ├── logback.xml                           # 日志配置文件
│   │       ├── database.sql                          # 数据库脚本
│   │       └── com/
│   │           └── example/
│   │               └── demo/
│   │                   └── mapper/
│   │                       └── UserMapper.xml        # User Mapper XML
│   └── test/
│       └── java/
└── logs/                          # 日志文件目录
```

## 环境要求

- JDK 1.8+
- Maven 3.0+
- MySQL 5.7+ 或 8.0+

## 数据库配置

1. 首先执行 [`database.sql`](src/main/resources/database.sql) 脚本创建数据库和表
2. 修改 [`mybatis-config.xml`](src/main/resources/mybatis-config.xml) 中的数据库连接信息：

```xml
<property name="url" value="jdbc:mysql://localhost:3306/mybatis_demo"/>
<property name="username" value="root"/>
<property name="password" value="your_password"/>
```

## 运行项目

1. 编译项目：
```bash
mvn clean compile
```

2. 运行主程序：
```bash

```

或者直接在 IDE 中运行 [`DemoApplication.java`](src/main/java/com/example/demo/DemoApplication.java)

## 功能说明

项目演示了 MyBatis 的基本 CRUD 操作：

- 插入用户（insert）
- 查询所有用户（selectAll）
- 根据ID查询用户（selectById）
- 根据用户名查询用户（selectByUsername）
- 更新用户信息（update）
- 删除用户（deleteById）

## 学习要点

1. MyBatis 配置文件的使用
2. Mapper 接口和 XML 映射文件的编写
3. 实体类的设计
4. SqlSessionFactory 的创建和使用
5. 事务管理
6. 结果映射配置

## 注意事项

- 项目使用了 MyBatis 3.5.13 和 MySQL Connector 8.0.33
- 日志使用 SLF4J + Logback 实现
- 项目开启了驼峰命名转换（mapUnderscoreToCamelCase）
- 请根据实际环境修改数据库连接信息