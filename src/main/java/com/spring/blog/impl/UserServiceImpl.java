package com.spring.blog.impl;

import com.spring.blog.binding.LoginForm;
import com.spring.blog.binding.RegisterForm;
import com.spring.blog.entity.UserEntity;
import com.spring.blog.repo.UserRepo;
import com.spring.blog.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private HttpSession session;
    @Override
    public boolean saveUser(RegisterForm form) {
        UserEntity userEntity  =  new UserEntity();
        BeanUtils.copyProperties(form,userEntity);
        UserEntity user = userRepo.findByEmail(form.getEmail());
        if(user!=null){
            return false;
        }else {
            userRepo.save(userEntity);
            return true;
        }
    }

    @Override
    public boolean loginUser(LoginForm form) {
        UserEntity user = userRepo.findByEmail(form.getEmail());
        if(user!=null && user.getPassword().equals(form.getPassword())){
            session.setAttribute("userId",user.getUserId());
            return true;
        }else {
            return false;
        }
    }


}
