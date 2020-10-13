package com.scy.controller.admin;

import com.scy.po.User;
import com.scy.service.BlogService;
import com.scy.service.CommentService;
import com.scy.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

/**
 * @Author Scy
 * @Date 2020/8/9 14:37
 * @Version 1.0
 */
@Api(tags = "后台：登录控制器")
@Controller
@RequestMapping("/admin")
public class AdminIndexController {

    @Autowired
    private UserService userService;
    @Autowired
    private BlogService blogService;
    @Autowired
    private CommentService commentService;

    @ApiOperation("获取统计数据")
    private void getStatisticData(Model model) {
        model.addAttribute("commentNum", blogService.commentsSum());
        model.addAttribute("viewNum", blogService.viewsSum());
        model.addAttribute("blogNum", blogService.countBlog());
    }

    @ApiOperation("后台管理系统主页")
    @GetMapping({"/", "/index"})
    public String index(Model model) {
        getStatisticData(model);
        model.addAttribute("newPosts", blogService.listRecommendBlogTop(5, "updateTime"));
        model.addAttribute("newComments", commentService.listNewCommentTop(5));
        return "admin/index";
    }

    @ApiOperation("跳转到登录页")
    @GetMapping("/login")
    public String loginPage() {
        return "admin/login";
    }

    @ApiOperation("跳转到详情页")
    @GetMapping("/details")
    public String details() {
        return "admin/profile";
    }

    @ApiOperation("登录操作")
    @PostMapping("/login")
    public String login(@ApiParam("用户名") @RequestParam("username") String username,
                        @ApiParam("密码") @RequestParam(name = "password") String password,
                        @ApiParam("session") HttpSession session,
                        @ApiParam("转发属性集") RedirectAttributes attributes) {
        User user = userService.checkUser(username, password);
        if (user != null) {
            user.setPassword(null);     // 密码清空，保证安全
            session.setAttribute("user", user);
            return "redirect:/admin/";
        } else {
            attributes.addFlashAttribute("msg", "用户名或密码错误");
            return "redirect:/admin/login";
        }
    }

    @ApiOperation("注销")
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("user");
        return "redirect:/admin/login";
    }


}
