package com.spring.blog.impl;

import com.spring.blog.binding.BlogForm;
import com.spring.blog.entity.BlogEntity;
import com.spring.blog.entity.UserEntity;
import com.spring.blog.repo.BlogRepo;
import com.spring.blog.repo.UserRepo;
import com.spring.blog.service.BlogService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BlogServiceImpl implements BlogService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private BlogRepo blogRepo;

    @Autowired
    private HttpSession session;
    @Override
    public String createBlog(BlogForm form) {
        //get the user based on session userId
        Integer userId = (Integer) session.getAttribute("userId");
        Optional<UserEntity> userEntity = userRepo.findById(userId);
        //if user is present then copy form data to entity
        if(userEntity.isPresent()) {
            BlogEntity blogEntity = new BlogEntity();
            BeanUtils.copyProperties(form, blogEntity);
            //set the user
            blogEntity.setUser(userEntity.get());
            blogEntity.setActiveSW("Yes");
            //save post
            blogRepo.save(blogEntity);
            return "created";
        }else {
            return "fail";
        }
    }

    @Override
    public BlogEntity getBlog(Integer blogId) {
        Optional<BlogEntity> blogEntity = blogRepo.findById(blogId);
        if(blogEntity.isPresent()){
            return blogEntity.get();
        }
        return  null;
    }

    @Override
    public boolean blogDelete(Integer blogId) {
        Optional<BlogEntity> blogEntity = blogRepo.findById(blogId);
        if(blogEntity.isPresent()){
            BlogEntity b= blogEntity.get();
            b.setActiveSW("No");
            blogRepo.save(b);
            return  true;
        }else{
            return false;
        }
    }


    @Override
    public List<BlogEntity> viewBlogs(Integer userId) {
        Optional<UserEntity> userEntity = userRepo.findById(userId);

        if(userEntity.isPresent()){
            List<BlogEntity> blogsList = userEntity.get().getBlogs();
           return   blogsList.stream().filter(e->e.getActiveSW().equals("Yes")).collect(Collectors.toList());

        }else {
            return Collections.emptyList();
        }
    }

    @Override
    public List<BlogEntity> viewAllBlogs() {
        List<BlogEntity> entityList = blogRepo.findAll();
        return  entityList.stream().filter(b->b.getActiveSW().equals("Yes")).collect(Collectors.toList());
    }

    @Override
    public List<BlogEntity> filterBlogs(String search) {
        List<BlogEntity> blogs = blogRepo.findAll();
        List<BlogEntity> allBlogs = new ArrayList<>();

        for (BlogEntity blog : blogs) {
            if(blog.getBlogTitle().toLowerCase().contains(search.toLowerCase()) && blog.getActiveSW().equals("Yes")){
                allBlogs.add(blog);
            }
        }
        return allBlogs;
    }

    @Override
    public List<BlogEntity> filterBlogByUser(String search, Integer userId) {
        Optional<UserEntity> userEntity = userRepo.findById(userId);
        if(userEntity.isPresent()){
            List<BlogEntity> blogs = userEntity.get().getBlogs();
           return blogs.stream().filter(b-> b.getBlogTitle().toLowerCase().contains(search.toLowerCase()) && b.getActiveSW().equals("Yes"))
                    .collect(Collectors.toList());
        }else {
            return List.of();
        }
    }


}
