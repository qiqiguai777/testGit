package com.lemon.community.service.impl;

import com.lemon.community.bean.DiscussPost;
import com.lemon.community.bean.User;
import com.lemon.community.mapper.DiscussPostMapper;
import com.lemon.community.service.DiscussPostService;
import com.lemon.community.util.HostHolder;
import com.lemon.community.util.SensitiveFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import java.util.Date;
import java.util.List;

/**
 * @version 1.0.0
 * @ClassName DiscussPostServiceImpl.java
 * @Description 话题业务层实现类
 */
@Service
public class DiscussPostServiceImpl implements DiscussPostService {

    @Autowired
    private DiscussPostMapper discussPostMapper;
    @Autowired
    private SensitiveFilter sensitiveFilter;
    @Autowired
    private HostHolder hostHolder;
    @Override
    public List<DiscussPost> getDiscussPost(int userId, int offset, int limit) {
        return discussPostMapper.findDiscussPosts(userId,offset,limit);
    }

    @Override
    public Integer countPosts(int userId) {
        return discussPostMapper.discussPostsCount(userId);
    }

    @Override
    public Integer addDiscussPost(String title, String content) {
        //对标题和内容进行过滤
        title = sensitiveFilter.filter(title);
        content = sensitiveFilter.filter(content);
        //转HTML标记
        title = HtmlUtils.htmlEscape(title);
        content = HtmlUtils.htmlEscape(content);
        DiscussPost discussPost = new DiscussPost();
        User user = hostHolder.getUser();
        //填充数据
        discussPost.setUserId(user.getId());
        discussPost.setTitle(title);
        discussPost.setContent(content);
        discussPost.setCreatedTime(new Date(System.currentTimeMillis()));
        discussPost.setCommentCount(0);
        discussPost.setStatus(0);
        discussPost.setType(0);
        discussPost.setScore((double) 0);
        return discussPostMapper.insertDiscussPost(discussPost);
    }

    @Override
    public DiscussPost findDiscussPostById(Integer id) {
        return discussPostMapper.selectDiscussPostById(id);
    }

    @Override
    public Integer updateCommentCount(Integer id, Integer count) {
        return discussPostMapper.updateCommentCount(id,count);
    }
}
