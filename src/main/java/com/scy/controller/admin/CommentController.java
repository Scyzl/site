package com.scy.controller.admin;

import com.scy.po.Comment;
import com.scy.service.BlogService;
import com.scy.service.CommentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * @Author Scy
 * @Date 2020/8/17 10:28
 * @Version 1.0
 */
@Api(tags = "后台：评论管理控制器")
@Controller
@RequestMapping("/admin")
public class CommentController {

    @Autowired
    private CommentService commentService;
    @Autowired
    private BlogService blogService;

    @ApiOperation("评论列表")
    @GetMapping("/comments")
    public String comments(@PageableDefault(size = 5, sort = "createTime", direction = Sort.Direction.DESC) Pageable pageable, Model model) {
        model.addAttribute("page", commentService.listComment(pageable));

        return "admin/list_comment";
    }

    @ApiOperation("评论列表，异步加载，部分刷新")
    @RequestMapping(value = "/commentsLoad", method = {RequestMethod.GET, RequestMethod.POST})
    public String commentsLoad(@PageableDefault(size = 5, sort = "createTime", direction = Sort.Direction.DESC) Pageable pageable, Model model) {
        model.addAttribute("page", commentService.listComment(pageable));

        return "admin/list_comment::commentList";
    }

    @ApiOperation("添加评论")
    @PostMapping("/comments/add")
    public String add_comment(@ApiParam("评论") Comment comment) {
        comment.setBlog(blogService.getBlog(comment.getBlog().getId()));
        commentService.saveComment(comment);
        return "redirect:/admin/commentsLoad";
    }

    @ApiOperation("删除评论")
    @GetMapping("/comments/delete/{id:[0-9_]{1,5}+}")
    public String delete_comment(@ApiParam("评论id") @PathVariable Long id, RedirectAttributes attributes) {

        commentService.deleteById(id);
        attributes.addFlashAttribute("flag", "0");
        attributes.addFlashAttribute("msg", "操作成功");

        return "redirect:/admin/comments";
    }
}
