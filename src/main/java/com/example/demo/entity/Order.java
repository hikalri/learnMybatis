package com.example.demo.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order implements Serializable {
    private Long id;
    private String orderNo;
    private BigDecimal amount;
    private Long userId;
    private Date orderTime;

    // 多对一关系：多个订单属于一个用户
    private User user;
}