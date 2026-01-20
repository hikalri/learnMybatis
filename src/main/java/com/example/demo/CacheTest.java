package com.example.demo;

import org.apache.ibatis.session.SqlSession;
import com.example.demo.utils.SqlSessionFactoryUtil;
import com.example.demo.mapper.UserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 测试一级缓存是否正常工作
 */
public class CacheTest {
    private static final Logger logger = LoggerFactory.getLogger(CacheTest.class);

    public static void main(String[] args) {
        try (SqlSession sqlSession = SqlSessionFactoryUtil.getSqlSessionFactory().openSession()) {
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);

            logger.info("=== 测试缓存是否工作 ===");

            // 第一次查询，应该从数据库获取数据
            logger.info("第一次查询用户列表（从数据库获取）");
            userMapper.selectAll();

            // 第二次查询，应该从缓存获取
            logger.info("第二次查询用户列表（从缓存获取）");
            userMapper.selectAll();

            // 查询用户总数
            logger.info("查询用户总数");
            userMapper.selectCount();

            // 再次查询用户总数
            logger.info("再次查询用户总数（从缓存获取）");
            userMapper.selectCount();

            logger.info("测试完成，请查看日志了解缓存使用情况");
        } catch (Exception e) {
            logger.error("测试过程中出现错误", e);
        }
    }
}