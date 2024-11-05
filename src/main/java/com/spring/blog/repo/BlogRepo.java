package com.spring.blog.repo;

import com.spring.blog.entity.BlogEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlogRepo extends JpaRepository<BlogEntity, Integer> {

}
