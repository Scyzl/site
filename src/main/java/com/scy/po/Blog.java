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
 * @Date 2020/8/7 10:52
 * @Version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "t_blog")
public class Blog {

    @Id
    @GeneratedValue
    private Long id;
    private String title;           // 博客标题
    private String content;         // 博客内容
    private String topPicture;      // 顶部大图
    private String flag;            // ?
    private Integer views;          // 访问/阅读数
    private boolean appreciate;     // 是否开启赞赏
    private boolean shareStatement; // 是否开启转载声明
    private boolean commentable;    // 是否开启评论
    private boolean published;      // 是否已发布，否则为草稿
    private boolean recommend;      // 是否作为推荐
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateTime;

    /**
     * mappedBy：
     * 1、只有@OneToOne，@OneToMany，@ManyToMany上才有mappedBy属性，ManyToOne不存在该属性；
     * 2、mappedBy标签一定是定义在被拥有方的（被控方），他指向拥有方；由多的一端来维护关系。（一般由有外键的一方来维护）
     * 3、mappedBy的含义，应该理解为，拥有方能够自动维护跟被拥有方的关系，当然，如果从被拥有方，通过手工强行来维护拥有方的关系也是可以做到的；
     * 4、mappedBy跟joinColumn/JoinTable总是处于互斥的一方，可以理解为正是由于拥有方的关联被拥有方的字段存在，拥有方才拥有了被拥有方。
     * mappedBy这方定义JoinColumn/JoinTable总是失效的，不会建立对应的字段或者表。
     */
    @ManyToOne
    private Type type;      // 原创、转载、翻译、其他；一篇博客只属于一种类型

    /**
     * 级联： 我的理解是：给当前设置的实体操作另一个实体的权限。
     * public class Student {
     *
     * @ManyToMany(cascade=CascadeType.PERSIST,fetch=FetchType.LAZY) private Set<Course> courses = new HashSet<>();
     * //其他代码略。
     * }
     * 上面的代码中给了Student对Course进行级联保存（cascade=CascadeType.PERSIST）的权限。
     * 此时，若Student实体持有的Course实体在数据库中不存在时，保存该Student时，系统将自动在Course实体对应的数据库中保存这条Course数据。
     * 而如果没有这个权限，则无法保存该Course数据。
     * <p>
     * CascadeType.PERSIST：级联新增（又称级联保存）
     * CascadeType.REMOVE：级联删除操作。 删除当前实体时，与它有映射关系的实体也会跟着被删除。
     * CascadeType.MERGE：级联更新（合并）操作。当Student中的数据改变，会相应地更新Course中的数据。
     * CascadeType.DETACH：级联脱管/游离操作。如果你要删除一个实体，但是它有外键无法删除，你就需要这个级联权限了。它会撤销所有相关的外键关联。
     * CascadeType.REFRESH：级联刷新操作。假设场景 有一个订单,订单里面关联了许多商品,这个订单可以被很多人操作,那么这个时候A对此订单和关联的商品进行了修改,与此同时,B也进行了相同的操作,但是B先一步比A保存了数据,那么当A保存数据的时候,就需要先刷新订单信息及关联的商品信息后,再将订单及商品保存。(来自良心会痛的评论)
     * CascadeType.ALL：拥有以上所有级联操作权限。
     */
    @ManyToMany(cascade = {CascadeType.PERSIST})
    private List<Tag> tags = new ArrayList<>();     // 一篇博客可以有多个标签

    @ManyToOne
    private User author;    // 一篇博客只有一个作者

    @OneToMany(mappedBy = "blog")
    private List<Comment> comments = new ArrayList<>(); // 一篇博客可以有多条评论


}