package com.scy.service;

import com.scy.po.Blog;
import com.scy.vo.BlogQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * @Author Scy
 * @Date 2020/8/9 14:29
 * @Version 1.0
 */
public interface BlogService {

    Blog save(Blog blog);

    Blog getBlog(Long id);

//    Page<Blog> listBlog(Pageable pageable, Blog blog);

    Page<Blog> listBlog(Pageable pageable, BlogQuery blog);

    Blog updateBlog(Long id, Blog blog);

    void deleteBlog(Long id);
}
