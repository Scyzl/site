package com.scy.controller.site;

import com.scy.po.Comment;
import com.scy.service.BlogService;
import com.scy.service.CommentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author Scy
 * @Date 2020/8/14 15:55
 * @Version 1.0
 */
@Api(tags = "前台：博客评论控制器")
@RequestMapping("/site")
@Controller
public class CommentShowController {

    @Autowired
    private CommentService commentService;
    @Autowired
    private BlogService blogService;

    @Value("${comment.avatar}")
    private String avatar;

    @ApiOperation("根据博客id加载评论")
    @GetMapping("/comments/{blogId:[0-9_]{1,5}+}")
    public String comments(@ApiParam("博客id") @PathVariable Long blogId, Model model) {
        model.addAttribute("comments", commentService.listCommentByBlogId(blogId));
        return "site/blog::commentList";
    }

    @ApiOperation("在博客前台添加评论")
    @PostMapping("/comments")
    public String post(Comment comment) {
        comment.setBlog(blogService.getBlog(comment.getBlog().getId()));
        comment.setAvatar(avatar);
        commentService.saveComment(comment);
        return "redirect:/site/comments/" + comment.getBlog().getId();
    }
}
