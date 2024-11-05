package com.spring.blog.service;

import com.spring.blog.binding.LoginForm;
import com.spring.blog.binding.RegisterForm;

public interface UserService {
    public boolean saveUser(RegisterForm form);

    public boolean loginUser(LoginForm form);
}
