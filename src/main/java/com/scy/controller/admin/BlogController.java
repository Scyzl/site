package com.scy.controller.admin;

import com.scy.po.Blog;
import com.scy.po.User;
import com.scy.service.BlogService;
import com.scy.service.TagService;
import com.scy.service.TypeService;
import com.scy.vo.BlogQuery;
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

import javax.servlet.http.HttpSession;

/**
 * @Author Scy
 * @Date 2020/8/9 14:37
 * @Version 1.0
 */
@Controller
@RequestMapping("/admin")
public class BlogController {

    private static final String ADD = "admin/add_blog";
    private static final String LIST = "admin/list_blog";
    private static final String REDIRECT_LIST = "redirect:/admin/blogs";

    @Autowired
    private BlogService blogService;
    @Autowired
    private TypeService typeService;
    @Autowired
    private TagService tagService;

    @GetMapping("/blogs")
    public String blogs(@PageableDefault(size = 2, sort = "updateTime", direction = Sort.Direction.DESC) Pageable pageable,
                        BlogQuery blog, Model model) {
        model.addAttribute("page", blogService.listBlog(pageable, blog));
        model.addAttribute("types", typeService.listType());
        return LIST;
    }

    @PostMapping("/blogs/search")
    public String search(@PageableDefault(size = 2, sort = "updateTime", direction = Sort.Direction.DESC) Pageable pageable,
                         BlogQuery blog, Model model) {
        model.addAttribute("page", blogService.listBlog(pageable, blog));

        return "admin/list_blog :: blogList";
    }

    @GetMapping("/blogs/add")
    public String add_blog_page(Model model) {
        setTypeAndTag(model);
//        // 新建博客时，默认类型为 原创
//        Blog blog = new Blog();
//        blog.setType(typeService.getTypeByName("原创"));
        model.addAttribute("blog", new Blog());
        return ADD;
    }

    private void setTypeAndTag(Model model) {
        model.addAttribute("types", typeService.listType());
        model.addAttribute("tags", tagService.listTag());
    }

    @GetMapping("/blogs/add/{id:[0-9_]{1,5}+}")
    public String edit_blog_page(@PathVariable Long id, Model model) {
        setTypeAndTag(model);
        Blog blog = blogService.getBlog(id);
        blog.init();
        System.out.println(blog.getTagIds());
        model.addAttribute("blog", blog);
//        model.addAttribute("tagIds", blog.getTagIds());
        return ADD;
    }

    @PostMapping("/blogs/add")
    public String add_blog(Blog blog, HttpSession session, RedirectAttributes attributes) {

        System.out.println(blog.getTagIds());
        blog.setAuthor((User) session.getAttribute("user"));
        blog.setType(typeService.getType(blog.getType().getId()));
        blog.setTags(tagService.listTag(blog.getTagIds()));

        Blog b;        // 保存新增博客
        if (blog.getId() == null) {
            b = blogService.save(blog);
        } else {
            b = blogService.updateBlog(blog.getId(), blog);
        }

        if (b == null) {
            attributes.addFlashAttribute("flag", "1");
            attributes.addFlashAttribute("msg", "操作失败！");
        } else {
            attributes.addFlashAttribute("flag", "0");
            attributes.addFlashAttribute("msg", "操作成功！");
        }

        return REDIRECT_LIST;
    }

    @GetMapping("/blogs/delete/{id:[0-9_]{1,5}+}")
    public String delete_blog(@PathVariable Long id, RedirectAttributes attributes) {
        blogService.deleteBlog(id);
        attributes.addFlashAttribute("flag", "0");
        attributes.addFlashAttribute("msg", "操作成功");
        return REDIRECT_LIST;
    }
}
