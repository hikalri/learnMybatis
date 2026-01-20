package com.example.demo.mapper;

import com.example.demo.entity.User;
import com.example.demo.utils.PageParam;
import java.util.List;

public interface UserMapper {
    // 根据ID查询用户
    User selectById(Long id);

    // 查询所有用户
    List<User> selectAll();

    // 分页查询用户
    List<User> selectByPage(PageParam pageParam);

    // 查询用户总数
    long selectCount();

    // 插入用户
    int insert(User user);

    // 更新用户
    int update(User user);

    // 根据ID删除用户
    int deleteById(Long id);

    // 根据用户名查询用户
    User selectByUsername(String username);
}