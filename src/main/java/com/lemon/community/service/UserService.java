package com.lemon.community.service;

import com.lemon.community.bean.User;

import java.util.Map;

/**
 * @version 1.0.0
 * @ClassName UserService.java
 * @Description 用户业务层接口
 */
public interface UserService {

    /**
     * 根据用户id获取用户
     * @param id 用户id
     * @return 用户数据
     */
    User getUserById(Integer id);
    /**
     * 注册用户
     * @param user
     * @return 返回注册操作信息
     */
    Map<String, Object> register(User user);
    /**
     * 激活账户
     * @param userId
     * @param code
     * @return
     */
    int activation(int userId, String code);
}
