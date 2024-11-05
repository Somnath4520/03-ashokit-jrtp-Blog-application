package com.spring.blog.binding;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BlogForm {

    private Integer blogId;
    private String blogTitle;
    private String description;
    private String content;
}
