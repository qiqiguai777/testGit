package com.lemon.community.util;

import com.lemon.community.bean.User;
import org.springframework.stereotype.Component;

/**
 * @version 1.0.0
 * @ClassName HostHolder.java
 * @Description 持有用户信息
 */
@Component
public class HostHolder {

    private ThreadLocal<User> users = new ThreadLocal<>();
    public void setUser(User user) {users.set(user);}
    public User getUser() {return users.get();}
    public void clear() {users.remove();}

}
