# MyBatis 缓存配置说明

## 问题分析

您在 `mybatis-config.xml` 中使用的 `cacheImplementation` 设置是无效的，MyBatis 不识别这个配置项。

## 正确的缓存配置方式

### 1. 全局启用二级缓存

在 `mybatis-config.xml` 中只需要设置：

```xml
<settings>
    <!-- 开启二级缓存 -->
    <setting name="cacheEnabled" value="true"/>
</settings>
```

### 2. 在 Mapper 中指定缓存实现

在具体的 Mapper XML 文件中添加缓存配置：

```xml
<mapper namespace="com.example.demo.mapper.UserMapper">
    <!-- 使用Ehcache缓存 -->
    <cache type="org.mybatis.caches.ehcache.EhcacheCache"/>

    <!-- 其他配置... -->
</mapper>
```

### 3. 添加必要的依赖

在 `pom.xml` 中需要添加以下依赖：

```xml
<!-- MyBatis EhCache适配器 -->
<dependency>
    <groupId>org.mybatis.caches</groupId>
    <artifactId>mybatis-ehcache</artifactId>
    <version>1.2.2</version>
</dependency>

<!-- EhCache核心库 -->
<dependency>
    <groupId>net.sf.ehcache</groupId>
    <artifactId>ehcache</artifactId>
    <version>2.10.9.2</version>
</dependency>
```

### 4. 配置 EhCache

在 `src/main/resources/ehcache.xml` 中配置 EhCache：

```xml
<?xml version="1.0" encoding="UTF-8"?>
<ehcache xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:noNamespaceSchemaLocation="http://ehcache.org/ehcache.xsd"
         updateCheck="false">

    <!-- 默认缓存配置 -->
    <defaultCache
        maxEntriesLocalHeap="1000"
        eternal="false"
        timeToIdleSeconds="300"
        timeToLiveSeconds="600"
        diskSpoolBufferSizeMB="30">
        <persistence strategy="localTempSwap"/>
    </defaultCache>

    <!-- 特定缓存配置 -->
    <cache name="userCache"
           maxEntriesLocalHeap="500"
           eternal="false"
           timeToIdleSeconds="600"
           timeToLiveSeconds="1200"
           diskSpoolBufferSizeMB="30">
        <persistence strategy="localTempSwap"/>
    </cache>
</ehcache>
```

## 缓存配置选项

在 Mapper 中可以配置更详细的缓存选项：

```xml
<cache
  type="org.mybatis.caches.ehcache.EhcacheCache"
  eviction="LRU"
  flushInterval="60000"
  size="1024"
  readOnly="true"/>
```

参数说明：
- `type`: 缓存实现类
- `eviction`: 缓存淘汰策略（LRU、FIFO、SOFT、WEAK）
- `flushInterval`: 缓存刷新间隔（毫秒）
- `size`: 缓存对象数量
- `readOnly`: 是否只读

## 总结

1. `cacheImplementation` 不是 MyBatis 的有效配置项
2. 正确的方式是在全局设置中启用 `cacheEnabled`
3. 在具体的 Mapper 中使用 `<cache>` 标签指定缓存实现
4. 确保添加了所有必要的依赖
5. 配置 EhCache 的 XML 文件

这样配置后，MyBatis 就会使用 EhCache 作为二级缓存的实现。