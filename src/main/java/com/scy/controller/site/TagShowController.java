package com.scy.controller.site;

import com.scy.po.Tag;
import com.scy.service.BlogService;
import com.scy.service.TagService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @Author Scy
 * @Date 2020/8/15 10:12
 * @Version 1.0
 */
@Api(tags = "前台：博客标签控制器")
@Controller
@RequestMapping("/site")
public class TagShowController {

    @Autowired
    private TagService tagService;
    @Autowired
    private BlogService blogService;

    @ApiOperation("根据博客id加载标签")
    @GetMapping({"/tags/{id}"})
    public String tags(@PageableDefault(size = 1, sort = "updateTime", direction = Sort.Direction.DESC) Pageable pageable,
                       @ApiParam("博客id") @PathVariable Long id, Model model) {
        List<Tag> tags = tagService.listTagTop(1000);
        if (id == -1) {
            id = tags.get(0).getId();
        }

        model.addAttribute("tags", tags);
        model.addAttribute("activeTagId", id);
        model.addAttribute("page", blogService.listBlog(id, pageable));
        return "site/tags";
    }

}
