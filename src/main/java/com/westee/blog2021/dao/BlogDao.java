package com.westee.blog2021.dao;

import com.westee.blog2021.entity.Blog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.List;

@Service
public class BlogDao {
    private final SqlSession sqlSession;

    @Inject
    public BlogDao(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    public List<Blog> getBlogs(Integer page, int pageSize, Integer userId) {
        HashMap<String, Integer> params= new HashMap<>();
        params.put("pageLimit", pageSize);
        params.put("userId", userId);
        params.put("offset", (page - 1) * pageSize);
        return sqlSession.selectList("selectBlog", params);
    }

    public int getBlogsCount(Integer userId) {
        return sqlSession.selectOne("countBlog", userId);
    }
}
