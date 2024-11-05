package com.spring.blog.service;

import com.spring.blog.binding.BlogForm;
import com.spring.blog.entity.BlogEntity;

import java.util.List;

public interface BlogService {

    public String createBlog(BlogForm form);

    public BlogEntity getBlog(Integer blogId);

    public boolean blogDelete(Integer blogId);

    public List<BlogEntity> viewBlogs(Integer userId);

    public List<BlogEntity> viewAllBlogs();

    public List<BlogEntity> filterBlogs(String search);

    public List<BlogEntity> filterBlogByUser(String search,Integer userId);
}
