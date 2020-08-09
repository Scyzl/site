package com.scy.service.impl;

import com.scy.dao.TagRepository;
import com.scy.exception.NotFoundException;
import com.scy.po.Tag;
import com.scy.service.TagService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author Scy
 * @Date 2020/8/7 16:43
 * @Version 1.0
 */
@Service
public class TagServiceImpl implements TagService {

    @Autowired
    TagRepository tagRepository;

    @Override
    @Transactional
    public Tag save(Tag tag) {
        return tagRepository.save(tag);
    }

    @Override
    @Transactional
    public Tag getTag(Long id) {
        return tagRepository.getOne(id);
    }

    @Override
    public Tag getTagByName(String name) {
        return tagRepository.findByName(name);
    }

    @Override
    @Transactional
    public Page<Tag> listTag(Pageable pageable) {
        return tagRepository.findAll(pageable);
    }

    @Override
    @Transactional
    public Tag updateTag(Long id, Tag newTag) {
        Tag tag = tagRepository.getOne(id);
        if (tag == null) {
            throw new NotFoundException("该标签不存在！");
        }
        BeanUtils.copyProperties(newTag, tag);
        return tagRepository.save(tag);
    }

    @Override
    @Transactional
    public void removeTag(Long id) {
        tagRepository.deleteById(id);
    }
}
