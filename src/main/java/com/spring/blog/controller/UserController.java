package com.spring.blog.controller;

import com.spring.blog.binding.LoginForm;
import com.spring.blog.binding.RegisterForm;
import com.spring.blog.constants.AppConstants;
import com.spring.blog.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private HttpSession session;



    @GetMapping("/logout")
    public String logout(){
        //destroying session object
        session.invalidate();
        return "redirect:/";
    }
    @GetMapping("/register")
    public String showRegister(Model model){
        RegisterForm form = new RegisterForm();
        model.addAttribute("formData",form);
        return "registration";
    }

    @PostMapping("/register")
    public String createUser(@ModelAttribute("formData") RegisterForm form, Model model){
        if(form.getEmail()!=null && !form.getEmail().equals("")) {
            boolean status = userService.saveUser(form);
            if (status){
                model.addAttribute("success","Registration successfull");
            }else {
                model.addAttribute(AppConstants.ERROR_MSG,"User already exist with this email");
            }
        }else{
            model.addAttribute(AppConstants.ERROR_MSG,"Enter correct email");
        }

        return "registration";
    }

    @GetMapping("/login")
    public String login(Model model){
        model.addAttribute("loginData",new LoginForm());
        return "login";
    }

    @PostMapping("/login")
    public String handleLogin(@ModelAttribute("loginData") LoginForm loginForm,Model model){

            boolean status = userService.loginUser(loginForm);
            if(status){
                Integer userId = (Integer) session.getAttribute("userId");
                model.addAttribute("userId",userId);
                model.addAttribute("success","login successful");
                return "redirect:/dashboard";

            }
            else {
                model.addAttribute(AppConstants.ERROR_MSG,"Invalid Credentials!");
                return "login";
            }
    }




}
