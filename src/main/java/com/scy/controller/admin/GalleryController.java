package com.scy.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author Scy
 * @Date 2020/9/28 9:37
 * @Version 1.0
 */
@Controller
@RequestMapping("/admin")
public class GalleryController {

    @GetMapping("/gallery")
    public String categories() {

        return "admin/gallery";
    }
}
