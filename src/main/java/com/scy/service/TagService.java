package com.scy.service;

import com.scy.po.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @Author Scy
 * @Date 2020/8/7 16:35
 * @Version 1.0
 */
public interface TagService {

    /**
     * 保存标签
     *
     * @param tag 要保存的标签
     * @return Tag
     */
    Tag save(Tag tag);

    /**
     * 根据id查询
     *
     * @param id 要查询tag的id
     * @return Tag
     */
    Tag getTag(Long id);

    /**
     * 根据标签名称查询
     *
     * @param name 标签名称
     * @return Tag
     */
    Tag getTagByName(String name);

    /**
     * 分页查询
     *
     * @param pageable 可分页的
     * @return Page<Tag>
     */
    Page<Tag> listTag(Pageable pageable);

    List<Tag> listTag();

    List<Tag> listTag(String ids);

    /**
     * 根据id查询到tag，并进行更新
     *
     * @param id  要更新的tag的id
     * @param tag 更新后的数据
     * @return Tag
     */
    Tag updateTag(Long id, Tag tag);

    /**
     * 根据id删除tag
     *
     * @param id 要删除的id
     */
    void removeTag(Long id);
}
