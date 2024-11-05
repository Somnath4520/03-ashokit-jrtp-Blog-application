package com.spring.blog.entity;

import jakarta.persistence.*;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "BLOG_TBL")
@Setter
@Getter
public class BlogEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer blogId;
    private String blogTitle;
    private String description;
    @Lob
    private String content;
    @CreationTimestamp
    @Column(updatable = false)
    private LocalDate created_date;
    @UpdateTimestamp
    @Column(insertable = false)
    private  LocalDate updated_date;

    @Column(name="ACTIVE_SW")
    private String activeSW;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @OneToMany(mappedBy = "blog" , cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    List<CommentEntity> comments;
}
