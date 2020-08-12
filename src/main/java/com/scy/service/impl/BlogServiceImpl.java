package com.scy.service.impl;

import com.scy.dao.BlogRepository;
import com.scy.exception.NotFoundException;
import com.scy.po.Blog;
import com.scy.po.Type;
import com.scy.service.BlogService;
import com.scy.utils.MyBeanUtils;
import com.scy.vo.BlogQuery;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 博客管理业务层接口实现类
 *
 * @Author Scy
 * @Date 2020/8/9 14:32
 * @Version 1.0
 */
@Service
public class BlogServiceImpl implements BlogService {

    @Autowired
    BlogRepository blogRepository;

    @Override
    @Transactional
    public Blog save(Blog blog) {
        if (blog.getId() == null) {
            blog.setCreateTime(new Date());
            blog.setUpdateTime(new Date());
            blog.setViews(0);
        } else {
            blog.setUpdateTime(new Date());
        }

        return blogRepository.save(blog);
    }

    @Override
    public Blog getBlog(Long id) {
        return blogRepository.getOne(id);
    }

    @Override
    public Page<Blog> listBlog(Pageable pageable, BlogQuery blog) {
        return blogRepository.findAll(new Specification<Blog>() {
            @Override
            public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                // 标题
                if (blog.getTitle() != null && !"".equals(blog.getTitle())) {
                    predicates.add(
                            criteriaBuilder.like(root.<String>get("title"), "%" + blog.getTitle() + "%")
                    );
                }
                // 分类
                if (blog.getTypeId() != null) {
                    predicates.add(
                            criteriaBuilder.equal(root.<Type>get("type").get("id"), blog.getTypeId())
                    );
                }
                // 推荐
                if (blog.isRecommend()) {
                    predicates.add(
                            criteriaBuilder.equal(root.<Boolean>get("recommend"), blog.isRecommend())
                    );
                }
                criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()]));

                return null;
            }
        }, pageable);
    }

    @Override
    @Transactional
    public Blog updateBlog(Long id, Blog newBlog) {
        Blog blog = blogRepository.getOne(id);
        if (blog == null) {
            throw new NotFoundException("博客不存在！");
        }

        // 把newBlog的非空属性 copy 到 blog，MyBeanUtils.getNullPropertyNames(newBlog)获得空属性的数组
        BeanUtils.copyProperties(newBlog, blog, MyBeanUtils.getNullPropertyNames(newBlog));
        blog.setUpdateTime(new Date());
        return blogRepository.save(blog);
    }

    @Override
    @Transactional
    public void deleteBlog(Long id) {
        blogRepository.deleteById(id);
    }
}
