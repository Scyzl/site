package com.scy.controller.admin;

import com.scy.po.Tag;
import com.scy.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

/**
 * @Author Scy
 * @Date 2020/8/7 17:12
 * @Version 1.0
 */
@Controller
@RequestMapping("/admin")
public class TagController {

    @Autowired
    private TagService tagService;

    @GetMapping("/tags")
    public String tags(@PageableDefault(size = 2, sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable,
                       Model model) {
        model.addAttribute("page", tagService.listTag(pageable));

        return "admin/list_tag";
    }

    /**
     * 跳转到添加标签页面，若服务器端进行校验，则需要在跳转时传递对应参数
     *
     * @param model 利用它来进行传参
     * @return 添加标签页面
     */
    @GetMapping("/tags/add")
    public String add_tag_page(Model model) {
        model.addAttribute("tag", new Tag());
        return "admin/add_tag";
    }

    @GetMapping("/tags/add/{id}")
    public String edit_tag_page(@PathVariable Long id, Model model) {
        model.addAttribute("tag", tagService.getTag(id));
        return "admin/add_tag";
    }

    /**
     * 添加标签操作，并考虑 name 非空验证
     * 注意： @NotBlank 要与 @Valid 一起使用，且保本必须对应，否则不成功
     *
     * @param newTag     要保存的新标签
     * @param result     验证结果，包含错误信息，可以利用它传递到前台
     * @param attributes 用于重定向时传递属性
     * @return 页面
     */
    @PostMapping(value = {"/tags/add", "/tags/add/{id}"})
    public String add_tag(@Valid Tag newTag, BindingResult result,
                          RedirectAttributes attributes, @PathVariable Long id) {

        // 校验标签是否已存在
        Tag tag1 = tagService.getTagByName(newTag.getName());
//        System.out.println("tag1  ==>  " + tag1);
        if (tag1 != null) {     // 标签已存在
            result.rejectValue("name", "nameError", "标签已存在，不可重复添加");
        }
        System.out.println("result  ==>  " + result);

        // 如果校验结果中有 错误，则返回添加页面
        if (result.hasErrors()) {
            return "admin/add_tag";
        }

        Tag t = null;
        // 如果id（标签）存在，则执行更新操作
        if (id > 0) {
            t = tagService.updateTag(id, newTag);
        } else {    // 否则执行添加操作
            t = tagService.save(newTag);
        }

        if (t == null) {
            attributes.addFlashAttribute("msg", "操作失败");
            attributes.addFlashAttribute("flag", "1");
        } else {
            attributes.addFlashAttribute("msg", "操作成功");
            attributes.addFlashAttribute("flag", "0");
        }
        return "redirect:/admin/tags";
    }

    @GetMapping("/tags/delete/{id}")
    public String delete_tag(@PathVariable Long id, RedirectAttributes attributes) {
        tagService.removeTag(id);
        attributes.addFlashAttribute("msg", "操作成功！");
        attributes.addFlashAttribute("flag", "0");

        return "redirect:/admin/tags";
    }
}
