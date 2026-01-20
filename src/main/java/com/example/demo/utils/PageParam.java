package com.example.demo.utils;

import lombok.Data;

@Data
public class PageParam {
    private int pageNum = 1;    // 当前页码，默认为1
    private int pageSize = 10;   // 每页大小，默认为10

    public PageParam() {}

    public PageParam(int pageNum, int pageSize) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
    }

    public int getOffset() {
        return (pageNum - 1) * pageSize;
    }
}