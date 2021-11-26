package com.lemon.community.service;

import com.lemon.community.bean.User;

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
}
