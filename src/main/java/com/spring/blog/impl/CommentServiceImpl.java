package com.spring.blog.impl;

import com.spring.blog.binding.CommentForm;
import com.spring.blog.entity.BlogEntity;
import com.spring.blog.entity.CommentEntity;
import com.spring.blog.repo.BlogRepo;
import com.spring.blog.repo.CommentRepo;
import com.spring.blog.service.CommentService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepo commentRepo;

    @Autowired
    private BlogRepo blogRepo;

    @Override
    public String createComment(Integer blogId, CommentForm form) {
        Optional<BlogEntity> blogEntity = blogRepo.findById(blogId);
        if(blogEntity.isPresent()){
            BlogEntity entity = blogEntity.get();
            CommentEntity cmtEntity = new CommentEntity();
            BeanUtils.copyProperties(form,cmtEntity);
            cmtEntity.setBlog(entity);
            commentRepo.save(cmtEntity);
            return "success";
        }else {
            return "error";
        }

    }



    @Override
    public void deleteComment(Integer commentId, Integer blogId) {

        commentRepo.deleteByCommentIdAndBlogId(commentId,blogId);
    }

    @Override
    public List<CommentEntity> getCommentsByUser(Integer userId) {
        List<CommentEntity> commentsByUser = commentRepo.findCommentsByUserId(userId);
         return commentsByUser.stream().filter(c->c.getBlog().getActiveSW().equals("Yes"))
                .collect(Collectors.toList());

    }
}
