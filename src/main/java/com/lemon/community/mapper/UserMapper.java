package com.lemon.community.mapper;

import com.lemon.community.bean.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @version 1.0.0
 * @ClassName UserMapper.java
 * @Description 用户持久层接口
 */
@Mapper
public interface UserMapper {

    /**
     * 根据用户id查找用户
     * @param id 用户id
     * @return 用户数据
     */
    User selectUserById(@Param("id") Integer id);
}
