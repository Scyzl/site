package com.scy.service;

import com.scy.po.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @Author Scy
 * @Date 2020/8/14 16:11
 * @Version 1.0
 */
public interface CommentService {

    List<Comment> listCommentByBlogId(Long blogId);

    Page<Comment> listComment(Pageable pageable);

    Comment saveComment(Comment newComment);

    /**
     * 最新评论 top x
     *
     * @param size top x
     * @return List<Comment>
     */
    List<Comment> listNewCommentTop(Integer size);

    void deleteById(Long id);
}
