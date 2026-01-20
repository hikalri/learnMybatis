package com.example.demo.utils;

import lombok.Data;
import java.util.List;

@Data
public class Page<T> {
    private List<T> data;      // 分页数据
    private long total;         // 总记录数
    private int pageNum;        // 当前页码
    private int pageSize;       // 每页大小
    private int totalPages;     // 总页数
    private boolean hasNext;    // 是否有下一页
    private boolean hasPrevious; // 是否有上一页

    public Page(List<T> data, long total, int pageNum, int pageSize) {
        this.data = data;
        this.total = total;
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.totalPages = (int) Math.ceil((double) total / pageSize);
        this.hasNext = pageNum < totalPages;
        this.hasPrevious = pageNum > 1;
    }
}