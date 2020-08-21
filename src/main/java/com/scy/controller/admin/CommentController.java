package com.scy.controller.admin;

import com.scy.po.Comment;
import com.scy.service.BlogService;
import com.scy.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * @Author Scy
 * @Date 2020/8/17 10:28
 * @Version 1.0
 */
@Controller
@RequestMapping("/admin")
public class CommentController {

    @Autowired
    private CommentService commentService;
    @Autowired
    private BlogService blogService;

    @GetMapping("/comments")
    public String comments(@PageableDefault(size = 5, sort = "createTime", direction = Sort.Direction.DESC) Pageable pageable, Model model) {
        model.addAttribute("page", commentService.listComment(pageable));

        return "admin/list_comment";
    }

    @RequestMapping("/commentsLoad")
    public String commentsLoad(@PageableDefault(size = 5, sort = "createTime", direction = Sort.Direction.DESC) Pageable pageable, Model model) {
        model.addAttribute("page", commentService.listComment(pageable));

        return "admin/list_comment::commentList";
    }

    @PostMapping("/comments/add")
    public String add_comment(Comment comment) {
        comment.setBlog(blogService.getBlog(comment.getBlog().getId()));
        commentService.saveComment(comment);
        return "redirect:/admin/commentsLoad";
    }

    @GetMapping("/comments/delete/{id:[0-9_]{1,5}+}")
    public String delete_comment(@PathVariable Long id, RedirectAttributes attributes) {

        commentService.deleteById(id);
        attributes.addFlashAttribute("flag", "0");
        attributes.addFlashAttribute("msg", "操作成功");

        return "redirect:/admin/comments";
    }
}
