package com.example.demo;

import com.example.demo.entity.Department;
import com.example.demo.entity.Order;
import com.example.demo.entity.User;
import com.example.demo.service.DepartmentService;
import com.example.demo.service.OrderService;
import com.example.demo.service.UserService;

import java.util.List;

public class RelationTest {
    public static void main(String[] args) {
        System.out.println("=== MyBatis多对一和一对多关系测试 ===");

        // 测试一对多关系：部门-用户
        testOneToManyDepartmentUser();

        // 测试多对一关系：用户-部门
        testManyToOneUserDepartment();

        // 测试一对多关系：用户-订单
        testOneToManyUserOrder();

        // 测试多对一关系：订单-用户
        testManyToOneOrderUser();

        // 测试完整关联：用户-部门-订单
        testCompleteRelation();

        System.out.println("\n=== 测试完成 ===");
    }

    /**
     * 测试一对多关系：部门-用户
     */
    private static void testOneToManyDepartmentUser() {
        System.out.println("\n=== 测试一对多关系：部门-用户 ===");

        DepartmentService departmentService = new DepartmentService();

        // 查询所有部门及其用户
        List<Department> departments = departmentService.getAllDepartmentsWithUsers();
        System.out.println("\n=== ===");

        System.out.println("\n=== ===");

        for (Department dept : departments) {
            System.out.println("部门：" + dept.getName() + " (ID: " + dept.getId() + ")");
            if (dept.getUsers() != null) {
                System.out.println("  包含用户：");
                for (User user : dept.getUsers()) {
                    System.out.println("    - " + user.getName() + " (ID: " + user.getId() + ")");
                }
            } else {
                System.out.println("  暂无用户");
            }
            System.out.println();
        }
    }

    /**
     * 测试多对一关系：用户-部门
     */
    private static void testManyToOneUserDepartment() {
        System.out.println("\n=== 测试多对一关系：用户-部门 ===");

        UserService userService = new UserService();

        // 查询所有用户及其部门
        List<User> users = userService.getAllUsersWithDepartment();

        for (User user : users) {
            System.out.println("用户：" + user.getName() + " (ID: " + user.getId() + ")");
            if (user.getDepartment() != null) {
                System.out.println("  所属部门：" + user.getDepartment().getName() +
                                 " (ID: " + user.getDepartment().getId() + ")");
            } else {
                System.out.println("  暂无部门");
            }
            System.out.println();
        }
    }

    /**
     * 测试一对多关系：用户-订单
     */
    private static void testOneToManyUserOrder() {
        System.out.println("\n=== 测试一对多关系：用户-订单 ===");

        UserService userService = new UserService();

        // 查询所有用户及其订单
        List<User> users = userService.getAllUsersWithOrders();

        for (User user : users) {
            System.out.println("用户：" + user.getName() + " (ID: " + user.getId() + ")");
            if (user.getOrders() != null) {
                System.out.println("  拥有订单：");
                for (Order order : user.getOrders()) {
                    System.out.println("    - " + order.getOrderNo() +
                                     " 金额：" + order.getAmount() +
                                     " (ID: " + order.getId() + ")");
                }
            } else {
                System.out.println("  暂无订单");
            }
            System.out.println();
        }
    }

    /**
     * 测试多对一关系：订单-用户
     */
    private static void testManyToOneOrderUser() {
        System.out.println("\n=== 测试多对一关系：订单-用户 ===");

        OrderService orderService = new OrderService();

        // 查询所有订单及其用户
        List<Order> orders = orderService.getAllOrdersWithUser();

        for (Order order : orders) {
            System.out.println("订单：" + order.getOrderNo() +
                             " 金额：" + order.getAmount() +
                             " (ID: " + order.getId() + ")");
            if (order.getUser() != null) {
                System.out.println("  所属用户：" + order.getUser().getName() +
                                 " (ID: " + order.getUser().getId() + ")");
            } else {
                System.out.println("  暂无用户");
            }
            System.out.println();
        }
    }

    /**
     * 测试完整关联：用户-部门-订单
     */
    private static void testCompleteRelation() {
        System.out.println("\n=== 测试完整关联：用户-部门-订单 ===");

        UserService userService = new UserService();

        // 查询第一个用户及其完整关联信息
        User user = userService.getUserWithDepartmentAndOrders(1L);

        if (user != null) {
            System.out.println("用户：" + user.getName() + " (ID: " + user.getId() + ")");

            // 显示部门信息
            if (user.getDepartment() != null) {
                System.out.println("  所属部门：" + user.getDepartment().getName() +
                                 " (ID: " + user.getDepartment().getId() + ")");
            }

            // 显示订单信息
            if (user.getOrders() != null && !user.getOrders().isEmpty()) {
                System.out.println("  拥有订单：");
                for (Order order : user.getOrders()) {
                    System.out.println("    - " + order.getOrderNo() +
                                     " 金额：" + order.getAmount() +
                                     " (ID: " + order.getId() + ")");
                }
            }
        } else {
            System.out.println("未找到ID为1的用户");
        }
    }
}