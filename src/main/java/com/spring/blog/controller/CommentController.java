package com.spring.blog.controller;

import com.spring.blog.binding.BlogForm;
import com.spring.blog.binding.CommentForm;
import com.spring.blog.entity.CommentEntity;
import com.spring.blog.service.CommentService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private HttpSession session;

    @PostMapping("/create-comment")
    public String addComment(@RequestParam("blogId") Integer blogId,
                             @ModelAttribute("commentForm") CommentForm form,
                             @ModelAttribute("blogData") BlogForm blogForm,
                             @ModelAttribute("comments") CommentEntity entity,
                             Model model){
        String comment = commentService.createComment(blogId, form);
        if(comment.equals("success")){
            model.addAttribute("msg","comment added successfully");
        }else {
            model.addAttribute("errMsg","something wrong");
        }
        return "redirect:/view-blog?blogId=" + blogId;
    }

    @GetMapping("/view-comment")
    public String getAllComments(Model model){
        allComments(model);
        return "view-comment";
    }

    //method for fetching all comments
    public void allComments(Model model){
        Integer userId = (Integer) session.getAttribute("userId");
        List<CommentEntity> allComments = commentService.getCommentsByUser(userId);
        model.addAttribute("comments",allComments);
    }

    @GetMapping("/delete-comment")
    public  String deleteComment(@RequestParam("cmntId") Integer comId,
                                 @RequestParam("blogId") Integer blogId,
                                 Model model){
         commentService.deleteComment(comId,blogId);

        return "redirect:/view-comment";
    }
}
