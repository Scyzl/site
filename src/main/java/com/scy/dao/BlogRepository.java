package com.scy.dao;

import com.scy.po.Blog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author Scy
 * @Date 2020/8/9 14:32
 * @Version 1.0
 */
public interface BlogRepository extends JpaRepository<Blog, Long>, JpaSpecificationExecutor<Blog> {

    /**
     * 查询最新推荐
     *
     * @param pageable
     * @return
     */
    @Query("select b from Blog b where b.recommend = true")
    List<Blog> findTop(Pageable pageable);

    /**
     * Search功能实现：根据查询条件进行分页查询
     *
     * @param query    查询条件
     * @param pageable 分页
     * @return Page<Blog>
     */
    @Query("select b from Blog b where b.title like ?1 or b.content like ?1")
    Page<Blog> findByQuery(String query, Pageable pageable);

    @Transactional
    @Modifying
    @Query("update Blog b set b.views = b.views+1 where b.id = ?1")
    int updateViews(Long id);

    /**
     * 按更新年份分组查询年份
     *
     * @return List<String-年份>
     */
    @Query("select function('date_format', b.updateTime, '%Y') as year from Blog b group by function('date_format', b.updateTime, '%Y') order by year desc ")
    List<String> findGroupYear();

    /**
     * 根据更新年份查询博客
     *
     * @param year 更新年份
     * @return List<Blog>
     */
    @Query("select b from Blog b where function('date_format', b.updateTime, '%Y') = ?1")
    List<Blog> findByYear(String year);

    /**
     * 查询已发布博客的总访问数
     *
     * @return Long 总访问数
     */
    @Query("select sum(b.views) from Blog b where b.published = true ")
    Long sumOfViews();

    /**
     * 查询已发布博客的总评论数
     *
     * @return Long 总评论数
     */
    @Query("select sum(b.comments.size) from Blog b where b.published = true ")
    Long sunOfComments();
}
