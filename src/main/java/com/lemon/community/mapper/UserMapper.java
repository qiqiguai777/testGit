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

    /**
     * 根据用户名查找用户
     * @param username
     * @return
     */
    User selectUserByUsername(String username);

    /**
     * 根据邮箱查找用户
     * @param email
     * @return
     */
    User selectUserByEmail(String email);

    /**
     * 插入一条用户信息
     * @param user
     */
    int insertUser(User user);

    /**
     * 更新用户的激活状态
     * @param id
     * @param status
     * @return
     */
    int updateStatus(int id , int status);

    /**
     * 根据id更新用户头像路径
     * @param id
     * @param headerUrl
     * @return
     */
    int updateHeader(Integer id, String headerUrl);

    /**
     * 根据id更新用户的密码
     * @param id
     * @param password
     * @return
     */
    int updatePassword(Integer id, String password);
}
