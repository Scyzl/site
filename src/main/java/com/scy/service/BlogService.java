package com.scy.service;

import com.scy.po.Blog;
import com.scy.vo.BlogQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

/**
 * @Author Scy
 * @Date 2020/8/9 14:29
 * @Version 1.0
 */
public interface BlogService {

    Blog save(Blog blog);

    Blog getBlog(Long id);

    Blog getBlogAndConvert(Long id);

    Page<Blog> listBlog(Pageable pageable);

    Page<Blog> listBlog(Long tagId, Pageable pageable);

    Page<Blog> listBlog(String query, Pageable pageable);

    Page<Blog> listBlog(Pageable pageable, BlogQuery blog);

    Map<String, List<Blog>> archivesBlog();

    List<Blog> listRecommendBlogTop(Integer size, String sortByPropName);

    Blog updateBlog(Long id, Blog blog);

    void deleteBlog(Long id);

    Long countBlog();

    Long viewsSum();

    Long commentsSum();
}
