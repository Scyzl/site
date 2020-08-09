package com.scy.dao;

import com.scy.po.Type;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 分类管理持久层接口
 *
 * @Author Scy
 * @Date 2020/8/9 10:18
 * @Version 1.0
 */
public interface TypeRepository extends JpaRepository<Type, Long> {

    Type getTypeByName(String name);
}
