package com.spring.blog.entity;

import jakarta.persistence.*;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;

@Entity
@Table(name = "COMMENT_TBL")
@Setter
@Getter
public class CommentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String content;
    @CreationTimestamp
    @Column(updatable = false)
    private LocalDate created_at;
    private String name;
    private String email;

    @ManyToOne
    @JoinColumn(name = "blog_id")
    private BlogEntity blog;

}
