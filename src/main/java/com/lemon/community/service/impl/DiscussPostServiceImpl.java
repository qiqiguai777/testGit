package com.lemon.community.service.impl;

import com.lemon.community.bean.DiscussPost;
import com.lemon.community.mapper.DiscussPostMapper;
import com.lemon.community.service.DiscussPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    @Override
    public List<DiscussPost> getDiscussPost(int userId, int offset, int limit) {
        return discussPostMapper.findDiscussPosts(userId,offset,limit);
    }

    @Override
    public Integer countPosts(int userId) {
        return discussPostMapper.discussPostsCount(userId);
    }
}
