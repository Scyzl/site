package com.scy.controller.site;

import com.scy.service.BlogService;
import com.scy.service.CommentService;
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
import org.springframework.web.bind.annotation.*;

/**
 * @Author Scy
 * @Date 2020/8/9 14:37
 * @Version 1.0
 */
@Api(tags = "前台：博客主页控制器")
@Controller
@RequestMapping("/site")
public class IndexController {

    @Autowired
    private BlogService blogService;
    @Autowired
    private TagService tagService;
    @Autowired
    private CommentService commentService;

    @ApiOperation("博客主页")
    @GetMapping({"", "/", "/index"})
    public String index(@PageableDefault(size = 2, sort = "updateTime", direction = Sort.Direction.DESC) Pageable pageable,
                        Model model) {
        model.addAttribute("page", blogService.listBlog(pageable));
        getRightBarData(model);
        return "site/index";
    }


    private void getRightBarData(Model model) {
        model.addAttribute("topTags", tagService.listTagTop(5));
        model.addAttribute("recommendTop", blogService.listRecommendBlogTop(3, "updateTime"));
        model.addAttribute("featuredTop", blogService.listRecommendBlogTop(3, "views"));
    }

    @ApiOperation("检索功能")
    @PostMapping("/search")
    public String search(@PageableDefault(size = 2, sort = "updateTime", direction = Sort.Direction.DESC) Pageable pageable,
                         @RequestParam String query, Model model) {

        model.addAttribute("page", blogService.listBlog("%" + query + "%", pageable));
        model.addAttribute("query", query);
        return "site/search";
    }

    @ApiOperation("跳转到指定博客")
    @GetMapping({"/blog/{id:[0-9_]{1,5}+}"})
    public String blog(@ApiParam("博客id") @PathVariable Long id, Model model) {
        model.addAttribute("blog", blogService.getBlogAndConvert(id));
        model.addAttribute("comments", commentService.listCommentByBlogId(id));
        getRightBarData(model);
        return "site/blog";
    }

    @ApiOperation("获取侧边栏数据")
    @GetMapping("/rightBar")
    public String rightBarData(Model model) {
        getRightBarData(model);

        return "site/commons/commons::rightbar";
    }

    @ApiOperation("获取底部数据")
    @GetMapping("/footer")
    public String footerData(Model model) {
        model.addAttribute("blogSum", blogService.countBlog());
        model.addAttribute("viewSum", blogService.viewsSum());
        model.addAttribute("commentSum", blogService.commentsSum());

        return "site/commons/commons::footerbar";
    }

    @ApiOperation("跳转到关于我页面")
    @GetMapping({"/about"})
    public String about(Model model) {
        getRightBarData(model);
        return "site/about";
    }

//    @GetMapping({"/contact"})
//    public String contact() {
//        return "site/contact";
//    }

    @ApiOperation("跳转到错误页面")
    @GetMapping("/error")
    public String e_404() {
        return "error/error";
    }
}
