package com.example.demo.service;

import com.example.demo.entity.Order;
import com.example.demo.mapper.OrderMapper;
import com.example.demo.utils.SqlSessionFactoryUtil;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

public class OrderService {

    /**
     * 根据ID查询订单
     */
    public Order getOrderById(Long id) {
        try (SqlSession sqlSession = SqlSessionFactoryUtil.getSqlSessionFactory().openSession()) {
            OrderMapper orderMapper = sqlSession.getMapper(OrderMapper.class);
            return orderMapper.selectById(id);
        }
    }

    /**
     * 查询所有订单
     */
    public List<Order> getAllOrders() {
        try (SqlSession sqlSession = SqlSessionFactoryUtil.getSqlSessionFactory().openSession()) {
            OrderMapper orderMapper = sqlSession.getMapper(OrderMapper.class);
            return orderMapper.selectAll();
        }
    }

    /**
     * 创建订单
     */
    public int createOrder(Order order) {
        try (SqlSession sqlSession = SqlSessionFactoryUtil.getSqlSessionFactory().openSession()) {
            OrderMapper orderMapper = sqlSession.getMapper(OrderMapper.class);
            int result = orderMapper.insert(order);
            sqlSession.commit();
            return result;
        }
    }

    /**
     * 更新订单
     */
    public int updateOrder(Order order) {
        try (SqlSession sqlSession = SqlSessionFactoryUtil.getSqlSessionFactory().openSession()) {
            OrderMapper orderMapper = sqlSession.getMapper(OrderMapper.class);
            int result = orderMapper.update(order);
            sqlSession.commit();
            return result;
        }
    }

    /**
     * 删除订单
     */
    public int deleteOrder(Long id) {
        try (SqlSession sqlSession = SqlSessionFactoryUtil.getSqlSessionFactory().openSession()) {
            OrderMapper orderMapper = sqlSession.getMapper(OrderMapper.class);
            int result = orderMapper.deleteById(id);
            sqlSession.commit();
            return result;
        }
    }

    /**
     * 根据用户ID查询订单（一对多关系）
     */
    public List<Order> getOrdersByUserId(Long userId) {
        try (SqlSession sqlSession = SqlSessionFactoryUtil.getSqlSessionFactory().openSession()) {
            OrderMapper orderMapper = sqlSession.getMapper(OrderMapper.class);
            return orderMapper.selectByUserId(userId);
        }
    }

    /**
     * 根据ID查询订单及其关联的用户（多对一关系）
     */
    public Order getOrderWithUser(Long id) {
        try (SqlSession sqlSession = SqlSessionFactoryUtil.getSqlSessionFactory().openSession()) {
            OrderMapper orderMapper = sqlSession.getMapper(OrderMapper.class);
            return orderMapper.selectWithUser(id);
        }
    }

    /**
     * 查询所有订单及其关联的用户（多对一关系）
     */
    public List<Order> getAllOrdersWithUser() {
        try (SqlSession sqlSession = SqlSessionFactoryUtil.getSqlSessionFactory().openSession()) {
            OrderMapper orderMapper = sqlSession.getMapper(OrderMapper.class);
            return orderMapper.selectAllWithUser();
        }
    }
}