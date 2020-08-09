package com.scy.service;

import com.scy.po.Type;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * @Author Scy
 * @Date 2020/8/9 10:29
 * @Version 1.0
 */
public interface TypeService {

    Type save(Type newType);

    Type getType(Long id);

    Type getTypeByName(String name);

    Page<Type> listType(Pageable pageable);

    void remove(Long id);

    Type updateType(Long id, Type newType);
}
