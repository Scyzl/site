package com.scy.dao;

import com.scy.po.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * 标签管理持久层接口
 *
 * @Author Scy
 * @Date 2020/8/7 16:44
 * @Version 1.0
 */
public interface TagRepository extends JpaRepository<Tag, Long> {

    /**
     * 根据名称查询标签
     *
     * @param name 标签名称
     * @return Tag
     */
    Tag findByName(String name);

    @Query("select t from Tag t")
    List<Tag> findTop(Pageable pageable);
}
