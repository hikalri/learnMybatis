package com.example.demo.mapper;

import com.example.demo.entity.Department;
import java.util.List;

public interface DepartmentMapper {
    // 根据ID查询部门
    Department selectById(Long id);

    // 查询所有部门
    List<Department> selectAll();

    // 插入部门
    int insert(Department department);

    // 更新部门
    int update(Department department);

    // 根据ID删除部门
    int deleteById(Long id);

    // 根据ID查询部门及其关联的用户（一对多关系）
    Department selectWithUsers(Long id);

    // 查询所有部门及其关联的用户（一对多关系）
    List<Department> selectAllWithUsers();
}