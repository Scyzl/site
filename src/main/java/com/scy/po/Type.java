package com.scy.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author Scy
 * @Date 2020/8/7 10:52
 * @Version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
//@ToString
@Entity
@Table(name = "t_type")
public class Type {
    @Id
    @GeneratedValue
    private Long id;
    @NotBlank(message = "分类名称不得为空！")
    private String name;

    @OneToMany(mappedBy = "type")
    private List<Blog> blogs = new ArrayList<>();   // 一种类型可以包含多篇博客
}
