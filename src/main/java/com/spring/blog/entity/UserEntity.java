package com.spring.blog.entity;

import jakarta.persistence.*;

import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Entity
@Table(name = "USERS_TBL")
@Setter
@Getter
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;

    private String firstName;
    private String lastName;
    private String email;
    private String password;

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<BlogEntity> blogs;
}
