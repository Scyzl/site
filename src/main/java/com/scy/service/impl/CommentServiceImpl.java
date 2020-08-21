package com.scy.service.impl;

import com.scy.dao.CommentRepository;
import com.scy.po.Comment;
import com.scy.service.CommentService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author Scy
 * @Date 2020/8/14 16:13
 * @Version 1.0
 */
@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Override
    public List<Comment> listCommentByBlogId(Long blogId) {
        Sort sort = Sort.by(Sort.Direction.ASC, "createTime");
        List<Comment> commentList = commentRepository.findByBlogIdAndParentCommentNull(blogId, sort);
        return eachComment(commentList);
    }

    @Override
    public Page<Comment> listComment(Pageable pageable) {
        return commentRepository.findAll(pageable);
    }

    @Transactional
    @Override
    public Comment saveComment(Comment newComment) {
        Long parentCommentId = newComment.getParentComment().getId();
        if (parentCommentId != -1) {
            newComment.setParentComment(commentRepository.getOne(parentCommentId));
        } else {
            newComment.setParentComment(null);
        }
        newComment.setCreateTime(new Date());
        return commentRepository.save(newComment);
    }

    @Override
    public List<Comment> listNewCommentTop(Integer size) {
        // 排序规则
        Sort sort = Sort.by(Sort.Direction.DESC, "createTime");
        Pageable pageable = PageRequest.of(0, size, sort);
        return commentRepository.findTop(pageable);
    }

    @Override
    public void deleteById(Long id) {
        commentRepository.deleteById(id);
    }

    /**
     * 循环每个顶级的评论节点
     *
     * @param comments 评论列表
     * @return
     */
    private List<Comment> eachComment(List<Comment> comments) {
        List<Comment> commentsView = new ArrayList<>();
        for (Comment comment : comments) {
            Comment c = new Comment();
            BeanUtils.copyProperties(comment, c);
            commentsView.add(c);
        }
        // 合并评论的各层子代到第一级子代集合中
        combineChildren(commentsView);
        return commentsView;
    }

    /**
     * 合并子代
     *
     * @param comments
     */
    private void combineChildren(List<Comment> comments) {
        for (Comment comment : comments) {
            List<Comment> replies = comment.getReplies();
            for (Comment reply : replies) {
                // 循环迭代，找出子代，存放在tempReplies中
                recursively(reply);
            }
            // 修改顶级节点的reply集合为迭代处理后的集合
            comment.setReplies(tempReplies);
            // 清除临时存放区
            tempReplies = new ArrayList<>();
        }
    }

    // 存放迭代找出的所有子代的集合
    private List<Comment> tempReplies = new ArrayList<>();

    private void recursively(Comment comment) {
        tempReplies.add(comment);   // 顶级节点添加到临时存放集合
        if (comment.getReplies().size() > 0) {
            List<Comment> replies = comment.getReplies();
            for (Comment reply : replies) {
                tempReplies.add(reply);
                if (reply.getReplies().size() > 0) {
                    recursively(reply);
                }
            }
        }
    }
}
