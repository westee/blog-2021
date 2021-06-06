package com.westee.blog2021.controller;

import com.westee.blog2021.entity.BlogResult;
import com.westee.blog2021.service.BlogService;
import org.apache.catalina.AccessLog;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;

@RestController
public class BlogController {
    BlogService blogService;
    @Inject
    public BlogController(BlogService blogService) {
        this.blogService = blogService;
    }

    @GetMapping("/blog")
    public BlogResult getBlogs(@RequestParam("page") Integer page, @RequestParam(value = "pageSize", required = false) Integer pageSize,
                               @RequestParam(value = "userId", required = false) Integer userId) {
        System.out.println(pageSize);
        int formatSize;
        if(pageSize == null) {
            formatSize = 10;
        } else {
            formatSize = pageSize;
        }
        return blogService.getBlogs(page, formatSize, userId);
    }

}
