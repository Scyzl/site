package com.scy.controller.site;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/site")
public class IndexController {

    @GetMapping({"/", "/index"})
    public String index() {
        return "site/index";
    }

    @GetMapping({"/blog_details"})
    public String blog_details() {
        return "site/blog_details";
    }

    @GetMapping({"/archives"})
    public String archives() {
        return "site/archives";
    }

    @GetMapping({"/tags"})
    public String tags() {
        return "site/tags";
    }

    @GetMapping({"/about"})
    public String about() {
        return "site/about";
    }

    @GetMapping({"/contact"})
    public String contact() {
        return "site/contact";
    }

    @GetMapping("/error")
    public String e_404() {
        return "error/error";
    }
}
