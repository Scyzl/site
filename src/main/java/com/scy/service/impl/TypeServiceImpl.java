package com.scy.service.impl;

import com.scy.dao.TypeRepository;
import com.scy.exception.NotFoundException;
import com.scy.po.Type;
import com.scy.service.TypeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author Scy
 * @Date 2020/8/9 10:39
 * @Version 1.0
 */
@Service
public class TypeServiceImpl implements TypeService {

    @Autowired
    TypeRepository typeRepository;

    @Override
    @Transactional
    public Type save(Type newType) {
        return typeRepository.save(newType);
    }

    @Override
    public Type getType(Long id) {
        return typeRepository.getOne(id);
    }

    @Override
    public Type getTypeByName(String name) {
        return typeRepository.getTypeByName(name);
    }

    @Override
    public Page<Type> listType(Pageable pageable) {
        return typeRepository.findAll(pageable);
    }

    @Override
    public List<Type> listType() {
        return typeRepository.findAll();
    }

    @Override
    @Transactional
    public void remove(Long id) {
        typeRepository.deleteById(id);
    }

    @Override
    @Transactional
    public Type updateType(Long id, Type newType) {
        Type type = typeRepository.getOne(id);
        if (type == null) {
            throw new NotFoundException("该分类不存在！");
        }
        BeanUtils.copyProperties(newType, type);
        return typeRepository.save(type);
    }
}
