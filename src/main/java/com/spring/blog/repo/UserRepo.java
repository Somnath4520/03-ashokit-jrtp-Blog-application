package com.spring.blog.repo;

import com.spring.blog.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<UserEntity,Integer> {


    public UserEntity findByEmail(String email);

}
