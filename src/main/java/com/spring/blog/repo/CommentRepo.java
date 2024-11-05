package com.spring.blog.repo;

import com.spring.blog.entity.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CommentRepo extends JpaRepository<CommentEntity, Integer> {

    @Query("SELECT c FROM CommentEntity c WHERE c.blog.user.userId = :userId")
    List<CommentEntity> findCommentsByUserId(Integer userId);

    @Transactional
    @Modifying
    @Query("DELETE FROM CommentEntity c WHERE c.id = :commentId AND c.blog.blogId = :blogId")
    void deleteByCommentIdAndBlogId(Integer commentId, Integer blogId);


}
