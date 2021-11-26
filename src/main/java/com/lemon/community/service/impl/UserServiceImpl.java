package com.lemon.community.service.impl;

import com.lemon.community.bean.User;
import com.lemon.community.mapper.UserMapper;
import com.lemon.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @version 1.0.0
 * @ClassName UserServiceImpl.java
 * @Description TODO
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;
    @Override
    public User getUserById(Integer id) {
        return userMapper.selectUserById(id);
    }
}
