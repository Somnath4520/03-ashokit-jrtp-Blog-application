package com.spring.blog.binding;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CommentForm {

    private Integer blogId;
    private String name;
    private String email;
    private String content;
}
