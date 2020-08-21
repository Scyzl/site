package com.scy.service.impl;

import com.scy.dao.BlogRepository;
import com.scy.exception.NotFoundException;
import com.scy.po.Blog;
import com.scy.po.Type;
import com.scy.service.BlogService;
import com.scy.utils.MarkdownUtils;
import com.scy.utils.MyBeanUtils;
import com.scy.vo.BlogQuery;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.*;
import java.util.*;

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

    /**
     * 保存博客 注意未赋值的属性
     *
     * @param blog 要保存的博客
     * @return
     */
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

    /**
     * 根据 id 查询
     *
     * @param id
     * @return
     */
    @Override
    public Blog getBlog(Long id) {
        return blogRepository.getOne(id);
    }

    /**
     * 获取博客内容，并将 Markdown 格式的内容，转换成 HTML 格式
     *
     * @param id 要获取的博客的 id
     * @return HTML 格式的博客
     */
    @Override
    public Blog getBlogAndConvert(Long id) {
        Blog blog = blogRepository.getOne(id);
        if (blog == null) {
            throw new NotFoundException("该博客不存在");
        }
        Blog b = new Blog();
        BeanUtils.copyProperties(blog, b);
        String content = b.getContent();
        b.setContent(MarkdownUtils.markdown2HtmlExtensions(content));

        blogRepository.updateViews(id);
        return b;
    }

    /**
     * 分页查询
     *
     * @param pageable
     * @return
     */
    @Override
    public Page<Blog> listBlog(Pageable pageable) {
        return blogRepository.findAll(pageable);
    }

    /**
     * 根据 tagId 进行分页查询
     *
     * @param tagId    标签id
     * @param pageable 分页
     * @return Page<Blog>
     */
    @Override
    public Page<Blog> listBlog(Long tagId, Pageable pageable) {
        return blogRepository.findAll(new Specification<Blog>() {
            @Override
            public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Join join = root.join("tags");
                return criteriaBuilder.equal(join.get("id"), tagId);
            }
        }, pageable);
    }

    /**
     * Search功能实现：根据查询条件进行分页查询
     *
     * @param query    查询条件
     * @param pageable 分页
     * @return Page<Blog>
     */
    @Override
    public Page<Blog> listBlog(String query, Pageable pageable) {
        return blogRepository.findByQuery(query, pageable);
    }

    /**
     * 组合Search功能实现：根据组合查询条件进行分页查询
     *
     * @param pageable 分页
     * @param blog     BlogQuery，查询条件
     * @return Page<Blog>
     */
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

    /**
     * 时间轴（归档）功能实现：按年份查询
     *
     * @return Map<年份, List < Blog>>
     */
    @Override
    public Map<String, List<Blog>> archivesBlog() {
        List<String> years = blogRepository.findGroupYear();
        Map<String, List<Blog>> map = new LinkedHashMap<>();

        for (String year : years) {
            map.put(year, blogRepository.findByYear(year));
        }

        return map;
    }

    /**
     * 查询最新推荐的Top x
     *
     * @param size Top x
     * @return List<Blog>
     */
    @Override
    public List<Blog> listRecommendBlogTop(Integer size, String sortByPropName) {
        // 排序规则
        Sort sort = Sort.by(Sort.Direction.DESC, sortByPropName);
        Pageable pageable = PageRequest.of(0, size, sort);
        return blogRepository.findTop(pageable);
    }

    /**
     * 更新博客
     *
     * @param id      要更新博客的id
     * @param newBlog 新博客
     * @return 更新后的博客
     */
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

    /**
     * 根据id删除博客
     *
     * @param id 要删除博客的id
     */
    @Override
    @Transactional
    public void deleteBlog(Long id) {
        blogRepository.deleteById(id);
    }

    /**
     * 博客总数
     *
     * @return Long
     */
    @Override
    public Long countBlog() {
        return blogRepository.count();
    }

    /**
     * 查询已发布博客的总访问数
     *
     * @return Long 总访问数
     */
    @Override
    public Long viewsSum() {
        return blogRepository.sumOfViews();
    }

    /**
     * 查询已发布博客的总评论数
     *
     * @return Long 总评论数
     */
    @Override
    public Long commentsSum() {
        return blogRepository.sunOfComments();
    }
}
