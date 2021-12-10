package com.lemon.community.service;

import com.lemon.community.bean.DiscussPost;

import java.util.List;

/**
 * @version 1.0.0
 * @ClassName DiscussPostService.java
 * @Description 话题业务层接口
 */
public interface DiscussPostService {
    /**
     * 获取话题
     * @param userId 用户id
     * @param offset 起始点
     * @param limit 数量
     * @return 话题数据
     */
    List<DiscussPost> getDiscussPost(int userId,int offset,int limit);

    /**
     * 统计话题个数
     * @param userId
     * @return 返回话题数
     */
    Integer countPosts(int userId);

    /**
     * 增加话题
     * @param title
     * @param content
     * @return
     */
    Integer addDiscussPost(String title, String content);

    /**
     * 根据id查找话题
     * @param id
     * @return
     */
    DiscussPost findDiscussPostById(Integer id);

    /**
     * 更新评论数量
     * @param id
     * @param count
     * @return
     */
    Integer updateCommentCount(Integer id, Integer count);
}
