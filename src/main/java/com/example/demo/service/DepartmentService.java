package com.example.demo.service;

import com.example.demo.entity.Department;
import com.example.demo.mapper.DepartmentMapper;
import com.example.demo.utils.SqlSessionFactoryUtil;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

public class DepartmentService {

    /**
     * 根据ID查询部门
     */
    public Department getDepartmentById(Long id) {
        try (SqlSession sqlSession = SqlSessionFactoryUtil.getSqlSessionFactory().openSession()) {
            DepartmentMapper departmentMapper = sqlSession.getMapper(DepartmentMapper.class);
            return departmentMapper.selectById(id);
        }
    }

    /**
     * 查询所有部门
     */
    public List<Department> getAllDepartments() {
        try (SqlSession sqlSession = SqlSessionFactoryUtil.getSqlSessionFactory().openSession()) {
            DepartmentMapper departmentMapper = sqlSession.getMapper(DepartmentMapper.class);
            return departmentMapper.selectAll();
        }
    }

    /**
     * 创建部门
     */
    public int createDepartment(Department department) {
        try (SqlSession sqlSession = SqlSessionFactoryUtil.getSqlSessionFactory().openSession()) {
            DepartmentMapper departmentMapper = sqlSession.getMapper(DepartmentMapper.class);
            int result = departmentMapper.insert(department);
            sqlSession.commit();
            return result;
        }
    }

    /**
     * 更新部门
     */
    public int updateDepartment(Department department) {
        try (SqlSession sqlSession = SqlSessionFactoryUtil.getSqlSessionFactory().openSession()) {
            DepartmentMapper departmentMapper = sqlSession.getMapper(DepartmentMapper.class);
            int result = departmentMapper.update(department);
            sqlSession.commit();
            return result;
        }
    }

    /**
     * 删除部门
     */
    public int deleteDepartment(Long id) {
        try (SqlSession sqlSession = SqlSessionFactoryUtil.getSqlSessionFactory().openSession()) {
            DepartmentMapper departmentMapper = sqlSession.getMapper(DepartmentMapper.class);
            int result = departmentMapper.deleteById(id);
            sqlSession.commit();
            return result;
        }
    }

    /**
     * 根据ID查询部门及其关联的用户（一对多关系）
     */
    public Department getDepartmentWithUsers(Long id) {
        try (SqlSession sqlSession = SqlSessionFactoryUtil.getSqlSessionFactory().openSession()) {
            DepartmentMapper departmentMapper = sqlSession.getMapper(DepartmentMapper.class);
            return departmentMapper.selectWithUsers(id);
        }
    }

    /**
     * 查询所有部门及其关联的用户（一对多关系）
     */
    public List<Department> getAllDepartmentsWithUsers() {
        try (SqlSession sqlSession = SqlSessionFactoryUtil.getSqlSessionFactory().openSession()) {
            DepartmentMapper departmentMapper = sqlSession.getMapper(DepartmentMapper.class);
            return departmentMapper.selectAllWithUsers();
        }
    }
}