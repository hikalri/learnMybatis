package com.example.demo.service;

import com.example.demo.entity.User;
import com.example.demo.utils.Page;
import com.example.demo.utils.PageParam;
import org.apache.ibatis.session.SqlSession;
import com.example.demo.utils.SqlSessionFactoryUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CacheService {
    private static final Logger logger = LoggerFactory.getLogger(CacheService.class);
    private UserService userService;

    public CacheService() {
        this.userService = new UserService();
    }

    /**
     * 演示分页查询缓存
     */
    public void demonstratePageCache() {
        logger.info("=== 演示分页查询缓存 ===");

        // 第一次查询，应该从数据库获取数据
        logger.info("第一次查询分页数据（从数据库获取）");
        PageParam pageParam = new PageParam(1, 5);
        Page<User> page1 = userService.getUsersByPage(pageParam);
        logger.info("第一页数据：{}", page1);

        // 第二次查询相同参数，应该从缓存获取
        logger.info("第二次查询分页数据（从缓存获取）");
        Page<User> page2 = userService.getUsersByPage(pageParam);
        logger.info("第一页数据：{}", page2);

        // 查询不同页码，应该从数据库获取
        logger.info("查询不同页码数据（从数据库获取）");
        PageParam pageParam2 = new PageParam(2, 5);
        Page<User> page3 = userService.getUsersByPage(pageParam2);
        logger.info("第二页数据：{}", page3);
    }

    /**
     * 演示单个用户查询缓存
     */
    public void demonstrateUserCache() {
        logger.info("=== 演示单个用户查询缓存 ===");

        // 创建测试用户
        User testUser = new User(null, "测试用户", "test@example.com", 25, null);
        userService.createUser(testUser);

        // 第一次查询用户，应该从数据库获取
        logger.info("第一次查询用户（从数据库获取）");
        User user1 = userService.getUserById(testUser.getId());
        logger.info("查询到的用户：{}", user1);

        // 第二次查询相同用户，应该从缓存获取
        logger.info("第二次查询用户（从缓存获取）");
        User user2 = userService.getUserById(testUser.getId());
        logger.info("查询到的用户：{}", user2);

        // 更新用户，缓存应该失效
        logger.info("更新用户（缓存应该失效）");
        user1.setEmail("updated@example.com");
        userService.updateUser(user1);

        // 再次查询，应该从数据库获取最新数据
        logger.info("更新后查询用户（从数据库获取最新数据）");
        User user3 = userService.getUserById(testUser.getId());
        logger.info("查询到的用户：{}", user3);

        // 清理测试数据
        userService.deleteUser(testUser.getId());
    }

    /**
     * 演示缓存统计信息
     */
    public void demonstrateCacheStats() {
        logger.info("=== 演示缓存统计信息 ===");

        try (SqlSession sqlSession = SqlSessionFactoryUtil.getSqlSessionFactory().openSession()) {
            // 获取Mapper接口
            com.example.demo.mapper.UserMapper userMapper = sqlSession.getMapper(com.example.demo.mapper.UserMapper.class);

            // 执行一些查询操作
            userMapper.selectAll();
            userMapper.selectCount();

            logger.info("缓存演示完成，请查看日志了解缓存使用情况");
        }
    }
}