package com.spring.blog.binding;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterForm {

    private String firstName;
    private String lastName;
    private String email;
    private String password;
}
