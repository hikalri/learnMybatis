package com.example.demo.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Department implements Serializable {
    private Long id;
    private String name;
    private String description;
    private Date createdTime;

    // 一对多关系：一个部门有多个用户
    private List<User> users;
}