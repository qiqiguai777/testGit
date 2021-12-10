package com.lemon.community.mapper;

import com.lemon.community.bean.DiscussPost;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @version 1.0.0
 * @ClassName DiscussPostMapper.java
 * @Description 话题持久层接口
 */
@Mapper
public interface DiscussPostMapper {

    /**
     * 根据参数查找符合条件的所有话题
     * @param userId 用户id
     * @param offset 起始位置
     * @param limit 每一页话题数
     * @return 话题数据
     */
    List<DiscussPost> findDiscussPosts(int userId,int offset,int limit);

    /**
     * 根据用户id(可选)返回的所有正常话题数量
     * @param userId 用户id
     * @return 话题数量
     */
    Integer discussPostsCount(@Param("userId") int userId);

    /**
     * 插入一条话题数据
     * @param discussPost
     * @return
     */
    Integer insertDiscussPost(DiscussPost discussPost);

    /**
     * 根据id查询话题
     * @param id
     * @return
     */
    DiscussPost selectDiscussPostById(Integer id);

    /**
     * 更新评论数量
     * @param id
     * @param commentCount
     * @return
     */
    Integer updateCommentCount(Integer id, Integer commentCount);
}
