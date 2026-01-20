package com.example.demo;

import com.example.demo.entity.Order;
import com.example.demo.entity.User;
import com.example.demo.mapper.UserMapper;
import com.example.demo.utils.SqlSessionFactoryUtil;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * 延迟加载测试类
 */
public class LazyLoadingTest {
    private static final Logger logger = LoggerFactory.getLogger(LazyLoadingTest.class);

    public static void main(String[] args) {
        SqlSessionFactory sqlSessionFactory = SqlSessionFactoryUtil.getSqlSessionFactory();

        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);

            // 测试延迟加载部门和订单
            testLazyDepartmentAndOrders(userMapper);

            // 测试批量延迟加载
            testBatchLazyLoading(userMapper);
        }
    }

    /**
     * 测试延迟加载部门和订单
     */
    private static void testLazyDepartmentAndOrders(UserMapper userMapper) {
        logger.info("========== 测试延迟加载部门和订单 ==========");

        // 查询用户（不加载部门和订单）
        logger.info("1. 查询用户（不加载部门和订单）");
        User user = userMapper.selectWithLazyDepartmentAndOrders(1L);
        logger.info("用户信息: ID={}, 姓名={}, 邮箱={}", user.getId(), user.getName(), user.getEmail());

        // 访问部门信息，触发延迟加载
        logger.info("2. 访问部门信息，触发延迟加载");
        logger.info("部门信息: {}", user.getDepartment()); // 此时触发部门加载

        // 访问订单信息，触发延迟加载
        logger.info("3. 访问订单信息，触发延迟加载");
        List<Order> orders = user.getOrders(); // 此时触发订单加载
        logger.info("订单数量: {}", orders != null ? orders.size() : 0);

        logger.info("==========================================\n");
    }

    /**
     * 测试批量延迟加载
     */
    private static void testBatchLazyLoading(UserMapper userMapper) {
        logger.info("========== 测试批量延迟加载 ==========");

        // 查询所有用户（不加载部门和订单）
        logger.info("1. 查询所有用户（不加载部门和订单）");
        List<User> users = userMapper.selectAllWithLazyDepartmentAndOrders();
        logger.info("用户数量: {}", users.size());

        // 遍历用户，访问部门和订单信息
        logger.info("2. 遍历用户，访问部门和订单信息");
        for (User user : users) {
            logger.info("用户信息: ID={}, 姓名={}", user.getId(), user.getName());

            // 访问部门信息，触发延迟加载
            if (user.getDepartmentId() != null) {
                logger.info("部门信息: {}", user.getDepartment());
            }

            // 访问订单信息，触发延迟加载
            List<Order> orders = user.getOrders();
            if (orders != null && !orders.isEmpty()) {
                logger.info("订单数量: {}", orders.size());
            }
        }

        logger.info("=====================================\n");
    }
}