package com.westee.blog2021.service;

import com.westee.blog2021.dao.BlogDao;
import com.westee.blog2021.entity.BlogResult;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith({MockitoExtension.class})
public class BlogServiceTest {
    @Mock
    BlogDao blogDao;

    @InjectMocks
    BlogService blogService;

    @Test
    public void getBlogs() {
        blogService.getBlogs(1, 10, null);

        Mockito.verify(blogDao).getBlogs(1, 10, null);
    }

    @Test
    public void getBlogFail(){
        Mockito.when(blogService.getBlogs(1, 10, null)).thenThrow(new RuntimeException());

        BlogResult result = blogService.getBlogs(1, 10, null);

        Assertions.assertEquals("获取失败", result.getMsg());
    }
}
