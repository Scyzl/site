package com.scy.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminIndexController {

    @GetMapping({"/", "/index"})
    public String index() {
        return "admin/index";
    }

    @GetMapping("/console")
    public String console() {
        return "admin/home/console";
    }

    @GetMapping("/home")
    public String home() {
        return "admin/home/home";
    }

    @GetMapping("/list")
    public String list() {
        return "admin/list";
    }

    @GetMapping("/comment")
    public String comment() {
        return "admin/comment";
    }

    @GetMapping("/tags")
    public String tags() {
        return "admin/tags";
    }

    @GetMapping("/article")
    public String article() {
        return "admin/article_edit";
    }
}
