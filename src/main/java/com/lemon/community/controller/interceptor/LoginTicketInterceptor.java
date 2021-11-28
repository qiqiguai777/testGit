package com.lemon.community.controller.interceptor;

import com.lemon.community.bean.LoginTicket;
import com.lemon.community.bean.User;
import com.lemon.community.mapper.LoginTicketMapper;
import com.lemon.community.service.UserService;
import com.lemon.community.util.CookieUtil;
import com.lemon.community.util.HostHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * @version 1.0.0
 * @ClassName LoginTicketInterceptor.java
 * @Description 登录状态过滤器
 */
@Component
public class LoginTicketInterceptor implements HandlerInterceptor {

    @Autowired
    private LoginTicketMapper loginTicketMapper;
    @Autowired
    private UserService userService;
    @Autowired
    private HostHolder hostHolder;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //从cookie中获取凭证
        String ticket = CookieUtil.getValue(request, "ticket");
        if(ticket != null) {
            //查询凭证
            LoginTicket loginTicket  = loginTicketMapper.selectByTicket(ticket);
            //检查凭证是否有效
            if( loginTicket !=null && loginTicket.getStatus() == 0 && loginTicket.getExpired().after(new Date())) {
                User user = userService.getUserById(loginTicket.getUserId());
                //在本次请求中持有用户信息
                hostHolder.setUser(user);
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        User user = hostHolder.getUser();
        if(user !=null && modelAndView != null) {
            //将用户信息保存用于前端页面展示
            modelAndView.addObject("loginUser", user);
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        //本次请求结束时清理当前登录用户信息
        hostHolder.clear();
    }
}
