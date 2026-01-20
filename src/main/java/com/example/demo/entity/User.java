package com.example.demo.entity;

import java.io.Serializable;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {
    private Long id;
    private String name;
    private String email;
    private Integer age;
    private Long departmentId;

    // 多对一关系：多个用户属于一个部门
    private Department department;

    // 一对多关系：一个用户有多个订单
    private List<Order> orders;

    // 便捷构造函数，不包含关联对象
    public User(Long id, String name, String email, Integer age, Long departmentId) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.age = age;
        this.departmentId = departmentId;
    }
}