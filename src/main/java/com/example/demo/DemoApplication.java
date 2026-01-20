package com.example.demo;

import com.example.demo.entity.User;
import com.example.demo.service.CacheService;
import com.example.demo.service.UserService;
import com.example.demo.utils.Page;
import com.example.demo.utils.PageParam;

import java.util.List;

public class DemoApplication {
    public static void main(String[] args) {
        System.out.println("=== MyBatis项目功能演示 ===");

        // 测试分页功能
        testPagination();

        // 测试缓存功能
        testCache();

        System.out.println("\n=== 程序执行完成 ===");
    }

    /**
     * 测试分页功能
     */
    private static void testPagination() {
        System.out.println("\n=== 测试分页功能 ===");

        UserService userService = new UserService();

        // 创建一些测试数据
        for (int i = 1; i <= 15; i++) {
            User user = new User(null, "用户" + i, "user" + i + "@example.com", 20 + i, null);
            userService.createUser(user);
        }

        // 测试分页查询
        System.out.println("第一页数据（每页5条）：");
        PageParam pageParam = new PageParam(1, 5);
        Page<User> page1 = userService.getUsersByPage(pageParam);
        System.out.println("总记录数：" + page1.getTotal());
        System.out.println("总页数：" + page1.getTotalPages());
        System.out.println("当前页：" + page1.getPageNum());
        System.out.println("是否有下一页：" + page1.isHasNext());
        page1.getData().forEach(System.out::println);

        System.out.println("\n第二页数据（每页5条）：");
        PageParam pageParam2 = new PageParam(2, 5);
        Page<User> page2 = userService.getUsersByPage(pageParam2);
        page2.getData().forEach(System.out::println);

        System.out.println("\n第三页数据（每页5条）：");
        PageParam pageParam3 = new PageParam(3, 5);
        Page<User> page3 = userService.getUsersByPage(pageParam3);
        page3.getData().forEach(System.out::println);

        // 清理测试数据
        System.out.println("\n清理测试数据...");
        List<User> allUsers = userService.getAllUsers();
        allUsers.forEach(user -> {
            if (user.getName().startsWith("用户")) {
                userService.deleteUser(user.getId());
            }
        });
    }

    /**
     * 测试二级缓存功能
     */
    private static void testCache() {
        System.out.println("\n=== 测试缓存功能 ===");

        CacheService cacheService = new CacheService();

        // 演示分页查询缓存
        cacheService.demonstratePageCache();

        // 演示单个用户查询缓存
        cacheService.demonstrateUserCache();

        // 演示缓存统计信息
        cacheService.demonstrateCacheStats();
    }
}