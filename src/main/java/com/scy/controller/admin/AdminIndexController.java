package com.scy.controller.admin;

import com.scy.po.User;
import com.scy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class AdminIndexController {

    @Autowired
    UserService userService;

    @GetMapping({"/", "/index"})
    public String index() {
        return "admin/index";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "admin/login";
    }

    @PostMapping("/login")
    public String login(@RequestParam("username") String username,
                        @RequestParam(name = "password") String password,
                        HttpSession session,
                        RedirectAttributes attributes) {
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

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("user");
        return "redirect:/admin/login";
    }

    @GetMapping("/blogs")
    public String blogs() {
        return "admin/list_blog";
    }

    @GetMapping("/types")
    public String types() {
        return "admin/list_type";
    }

    @GetMapping("/comments")
    public String comments() {
        return "admin/list_comment";
    }

    @GetMapping("/post")
    public String create_post() {
        return "admin/create_post";
    }
}
