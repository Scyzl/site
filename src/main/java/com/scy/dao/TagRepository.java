package com.scy.dao;

import com.scy.po.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Author Scy
 * @Date 2020/8/7 16:44
 * @Version 1.0
 */
public interface TagRepository extends JpaRepository<Tag, Long> {

    Tag findByName(String name);
}
