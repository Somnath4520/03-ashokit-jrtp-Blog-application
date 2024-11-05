package com.spring.blog.service;

import com.spring.blog.binding.CommentForm;
import com.spring.blog.entity.CommentEntity;

import java.util.List;

public interface CommentService {

    public String createComment(Integer blogId, CommentForm form);

    public void deleteComment(Integer comId ,Integer blogId);

    public List<CommentEntity> getCommentsByUser(Integer userId);
}
