package com.scy.dao;

import com.scy.po.Comment;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @Author Scy
 * @Date 2020/8/14 16:14
 * @Version 1.0
 */
public interface CommentRepository extends JpaRepository<Comment, Long>, JpaSpecificationExecutor<Comment> {

    List<Comment> findByBlogIdAndParentCommentNull(Long blogId, Sort sort);

    /**
     * 查询最新评论
     *
     * @param pageable
     * @return
     */
    @Query("select c from Comment c")
    List<Comment> findTop(Pageable pageable);
}
