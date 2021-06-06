package com.westee.blog2021.entity;

import java.util.List;

public class BlogResult extends Result<List<Blog>> {
    private int total;
    private int page;
    private int totalPage;

    private BlogResult(String status, String msg, List<Blog> data, int total, int page, int totalPage) {
        super(msg, status, data);
        this.total = total;
        this.page = page;
        this.totalPage = totalPage;
    }

    public static BlogResult failure(String msg) {
        return new BlogResult("fail", "获取失败", null, 0,0,0);
    }

    public static BlogResult success(String msg, List<Blog> data,int page, int total, int totalPage) {
        return new BlogResult("ok", "获取成功", data, total,page,totalPage);
    }

}
