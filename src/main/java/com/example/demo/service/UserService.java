package com.example.demo.service;

import com.example.demo.entity.User;
import com.example.demo.mapper.UserMapper;
import com.example.demo.utils.Page;
import com.example.demo.utils.PageParam;
import org.apache.ibatis.session.SqlSession;
import com.example.demo.utils.SqlSessionFactoryUtil;

import java.util.List;

public class UserService {
    /**
     * 分页查询用户
     * @param pageParam 分页参数
     * @return 分页结果
     */
    public Page<User> getUsersByPage(PageParam pageParam) {
        try (SqlSession sqlSession = SqlSessionFactoryUtil.getSqlSessionFactory().openSession()) {
            UserMapper mapper = sqlSession.getMapper(UserMapper.class);

            // 获取分页数据
            List<User> users = mapper.selectByPage(pageParam);

            // 获取总记录数
            long total = mapper.selectCount();

            // 返回分页结果
            return new Page<>(users, total, pageParam.getPageNum(), pageParam.getPageSize());
        }
    }

    /**
     * 获取所有用户
     * @return 用户列表
     */
    public List<User> getAllUsers() {
        try (SqlSession sqlSession = SqlSessionFactoryUtil.getSqlSessionFactory().openSession()) {
            UserMapper mapper = sqlSession.getMapper(UserMapper.class);
            return mapper.selectAll();
        }
    }

    /**
     * 根据ID获取用户
     * @param id 用户ID
     * @return 用户
     */
    public User getUserById(Long id) {
        try (SqlSession sqlSession = SqlSessionFactoryUtil.getSqlSessionFactory().openSession()) {
            UserMapper mapper = sqlSession.getMapper(UserMapper.class);
            return mapper.selectById(id);
        }
    }

    /**
     * 创建用户
     * @param user 用户
     * @return 影响行数
     */
    public int createUser(User user) {
        try (SqlSession sqlSession = SqlSessionFactoryUtil.getSqlSessionFactory().openSession()) {
            UserMapper mapper = sqlSession.getMapper(UserMapper.class);
            int result = mapper.insert(user);
            sqlSession.commit();
            return result;
        }
    }

    /**
     * 更新用户
     * @param user 用户
     * @return 影响行数
     */
    public int updateUser(User user) {
        try (SqlSession sqlSession = SqlSessionFactoryUtil.getSqlSessionFactory().openSession()) {
            UserMapper mapper = sqlSession.getMapper(UserMapper.class);
            int result = mapper.update(user);
            sqlSession.commit();
            return result;
        }
    }

    /**
     * 删除用户
     * @param id 用户ID
     * @return 影响行数
     */
    public int deleteUser(Long id) {
        try (SqlSession sqlSession = SqlSessionFactoryUtil.getSqlSessionFactory().openSession()) {
            UserMapper mapper = sqlSession.getMapper(UserMapper.class);
            int result = mapper.deleteById(id);
            sqlSession.commit();
            return result;
        }
    }

    /**
     * 根据ID查询用户及其部门（多对一关系）
     * @param id 用户ID
     * @return 用户及其部门信息
     */
    public User getUserWithDepartment(Long id) {
        try (SqlSession sqlSession = SqlSessionFactoryUtil.getSqlSessionFactory().openSession()) {
            UserMapper mapper = sqlSession.getMapper(UserMapper.class);
            return mapper.selectWithDepartment(id);
        }
    }

    /**
     * 查询所有用户及其部门（多对一关系）
     * @return 用户列表及其部门信息
     */
    public List<User> getAllUsersWithDepartment() {
        try (SqlSession sqlSession = SqlSessionFactoryUtil.getSqlSessionFactory().openSession()) {
            UserMapper mapper = sqlSession.getMapper(UserMapper.class);
            return mapper.selectAllWithDepartment();
        }
    }

    /**
     * 根据ID查询用户及其订单（一对多关系）
     * @param id 用户ID
     * @return 用户及其订单信息
     */
    public User getUserWithOrders(Long id) {
        try (SqlSession sqlSession = SqlSessionFactoryUtil.getSqlSessionFactory().openSession()) {
            UserMapper mapper = sqlSession.getMapper(UserMapper.class);
            return mapper.selectWithOrders(id);
        }
    }

    /**
     * 查询所有用户及其订单（一对多关系）
     * @return 用户列表及其订单信息
     */
    public List<User> getAllUsersWithOrders() {
        try (SqlSession sqlSession = SqlSessionFactoryUtil.getSqlSessionFactory().openSession()) {
            UserMapper mapper = sqlSession.getMapper(UserMapper.class);
            return mapper.selectAllWithOrders();
        }
    }

    /**
     * 根据ID查询用户及其部门和订单（完整关联）
     * @param id 用户ID
     * @return 用户及其完整关联信息
     */
    public User getUserWithDepartmentAndOrders(Long id) {
        try (SqlSession sqlSession = SqlSessionFactoryUtil.getSqlSessionFactory().openSession()) {
            UserMapper mapper = sqlSession.getMapper(UserMapper.class);
            return mapper.selectWithDepartmentAndOrders(id);
        }
    }
}