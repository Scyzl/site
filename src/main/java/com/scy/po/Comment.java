package com.scy.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author Scy
 * @Date 2020/8/7 10:59
 * @Version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "t_comment")
public class Comment {

    @Id
    @GeneratedValue
    private Long id;
    private String content;
    private String nickname;
    private String email;
    private String avatar;      // 头像
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;

    @ManyToOne
    private Blog blog;      // 一条评论只属于一篇博客

    @OneToMany(mappedBy = "parentComment")
    private List<Comment> replies = new ArrayList<>();  // 本条评论下有别人的回复（评论）
    @ManyToOne
    private Comment parentComment;      // 一条评论最多只能作为一条评论的回复
}
