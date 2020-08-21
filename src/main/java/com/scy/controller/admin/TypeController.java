package com.scy.controller.admin;

import com.scy.po.Type;
import com.scy.service.TypeService;
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
 * 分类管理控制层
 *
 * @Author Scy
 * @Date 2020/8/9 10:51
 * @Version 1.0
 */
@Controller
@RequestMapping("/admin")
public class TypeController {

    @Autowired
    TypeService typeService;

    @GetMapping("/types")
    public String types(@PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable, Model model) {
        model.addAttribute("page", typeService.listType(pageable));
        return "admin/list_type";
    }

    @GetMapping("/types/add")
    public String add_type_page(Model model) {
        model.addAttribute("type", new Type());
        return "admin/add_type";
    }

    @GetMapping("/types/add/{id:[0-9_]{1,5}+}")
    public String edit_type_page(@PathVariable Long id, Model model) {
        model.addAttribute("type", typeService.getType(id));
        return "admin/add_type";
    }

    @PostMapping("/types/add/{id:[0-9_]{1,5}+}")
    public String add_type(@Valid Type newType, BindingResult result,
                           @PathVariable Long id, RedirectAttributes attributes) {
        // 校验是否分类已存在
        if (typeService.getTypeByName(newType.getName()) != null) {
            result.rejectValue("name", "nameError", "分类已存在，不可重复添加");
        }
        System.out.println("result  ==>  " + result);

        if (result.hasErrors()) {
            return "admin/add_type";
        }

        Type t = null;
        // 根据id是否存在来选择执行的操作
        if (id > 0) {
            t = typeService.updateType(id, newType);
        } else {
            t = typeService.save(newType);
        }

        if (t == null) {
            attributes.addFlashAttribute("flag", "1");
            attributes.addFlashAttribute("msg", "操作失败！");
        } else {
            attributes.addFlashAttribute("flag", "0");
            attributes.addFlashAttribute("msg", "操作成功！");
        }
        return "redirect:/admin/types";
    }

    @GetMapping("/types/delete/{id:[0-9_]{1,5}+}")
    public String delete_type(@PathVariable Long id, RedirectAttributes attributes) {
        typeService.remove(id);
        attributes.addFlashAttribute("flag", "0");
        attributes.addFlashAttribute("msg", "操作成功");

        return "redirect:/admin/types";
    }
}
