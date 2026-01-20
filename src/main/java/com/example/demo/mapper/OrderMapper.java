package com.example.demo.mapper;

import com.example.demo.entity.Order;
import java.util.List;

public interface OrderMapper {
    // 根据ID查询订单
    Order selectById(Long id);

    // 查询所有订单
    List<Order> selectAll();

    // 插入订单
    int insert(Order order);

    // 更新订单
    int update(Order order);

    // 根据ID删除订单
    int deleteById(Long id);

    // 根据用户ID查询订单（一对多关系）
    List<Order> selectByUserId(Long userId);

    // 根据ID查询订单及其关联的用户（多对一关系）
    Order selectWithUser(Long id);

    // 查询所有订单及其关联的用户（多对一关系）
    List<Order> selectAllWithUser();
}