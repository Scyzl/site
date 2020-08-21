package com.scy.controller.site;

import com.scy.po.Blog;
import com.scy.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

/**
 * @Author Scy
 * @Date 2020/8/15 15:13
 * @Version 1.0
 */
@Controller
@RequestMapping("/site")
public class ArchiveShowController {

    @Autowired
    private BlogService blogService;

    @GetMapping("/archives")
    public String archives(Model model) {
        Map<String, List<Blog>> map = blogService.archivesBlog();
//        System.out.println("archiveMap ==> " + map);
        model.addAttribute("archiveMap", map);

        return "site/archives";
    }
}
