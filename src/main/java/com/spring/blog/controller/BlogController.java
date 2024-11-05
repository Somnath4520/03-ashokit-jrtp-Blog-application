package com.spring.blog.controller;

import com.spring.blog.binding.BlogForm;
import com.spring.blog.binding.CommentForm;
import com.spring.blog.constants.AppConstants;
import com.spring.blog.entity.BlogEntity;
import com.spring.blog.entity.CommentEntity;
import com.spring.blog.service.BlogService;
import com.spring.blog.service.CommentService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class BlogController {

    @Autowired
    private BlogService blogService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private HttpSession session;

    @GetMapping("/")
    public String index(Model model){
        List<BlogEntity> blogs = blogService.viewAllBlogs();
        model.addAttribute("allBlogs",blogs);
        return "index";
    }

    @GetMapping("/dashboard")
    public String dashBoard(Model model){
       getAllBlogs(model);
        return "dashboard";
    }

    //get all blogs
    private void getAllBlogs(Model model) {
        Integer userId = (Integer) session.getAttribute("userId");
        List<BlogEntity> blogs = blogService.viewBlogs(userId);
        model.addAttribute("blogs",blogs);
    }

    @GetMapping("/blog")
    public String blogForm(Model model){
        model.addAttribute(AppConstants.BLOG_DATA,new BlogForm());
        return AppConstants.RETURN_NEW_BLOG;
    }

    @PostMapping("/createBlog")
    public  String createBlog(@ModelAttribute("blogData") BlogForm form, Model model){
        String msg = blogService.createBlog(form);
        if(msg.contains("created")) {
            model.addAttribute("successMsg","Post created successfully");
            return "redirect:/dashboard";
        }else{
            model.addAttribute("errMsg","Something went wrong");
            return AppConstants.RETURN_NEW_BLOG;
        }
    }

    @GetMapping("/edit-blog")
    public String editBlog(@RequestParam Integer blogId, Model model){
        BlogEntity entity = blogService.getBlog(blogId);
        model.addAttribute(AppConstants.BLOG_DATA,entity);
        return AppConstants.RETURN_NEW_BLOG;
    }

    @GetMapping("/view-blog")
    public String viewBlog(@RequestParam("blogId") Integer blogId, Model model){
        BlogEntity entity = blogService.getBlog(blogId);
        List<CommentEntity> comments = entity.getComments();
        model.addAttribute("comments",comments);
        model.addAttribute(AppConstants.BLOG_DATA,entity);
        model.addAttribute("commentForm",new CommentForm());
        return "view-blog";
    }

    @GetMapping("/delete-blog")
    public String deleteBlog(@RequestParam("blogId") Integer blogId, Model model){
        boolean status = blogService.blogDelete(blogId);
        if(status){
            getAllBlogs(model);
            return "redirect:/dashboard";
        }
        else {
            return  "dashboard";
        }
    }

    @GetMapping("/filter-blog")
    public String filterBlogs(@RequestParam("searchData") String search,Model model){
        List<BlogEntity> blogs = blogService.filterBlogs(search);

        model.addAttribute("allBlogs",blogs);
        return  "index ::#blogDiv";
    }

    @GetMapping("filter-blogByUser")
    public String getBlogByUser(@RequestParam("searchData") String search,Model model){
        Integer userId = (Integer) session.getAttribute("userId");
        List<BlogEntity> blogs = blogService.filterBlogByUser(search,userId);
        model.addAttribute("blogs",blogs);
        return  "dashboard :: blogTable";
    }

}
