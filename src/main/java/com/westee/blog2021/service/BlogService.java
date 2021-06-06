package com.westee.blog2021.service;

import com.westee.blog2021.dao.BlogDao;
import com.westee.blog2021.entity.Blog;
import com.westee.blog2021.entity.BlogResult;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

@Service
public class BlogService {
    BlogDao blogDao;
    UserService userService;
    @Inject
    public BlogService(BlogDao blogDao, UserService userService) {
        this.blogDao = blogDao;
        this.userService = userService;
    }

    public BlogResult getBlogs(Integer page, int pageSize, Integer userId) {
        try {
            List<Blog> blogs = blogDao.getBlogs(page, pageSize, userId);

            blogs.forEach(blog -> {
                blog.setUser(userService.getUserByUserId(blog.getUserId()));
            });
            Integer blogsCount = blogDao.getBlogsCount(userId);
            Integer totalPage = blogsCount % pageSize == 1 ?
                    blogsCount / pageSize + 1 : blogsCount / pageSize;

            return BlogResult.success("获取成功", blogs, page, blogsCount, totalPage);

        } catch (Exception e) {
            return BlogResult.failure(e.getMessage());
        }
    }
}
