package com.lemon.community.service.impl;

import com.lemon.community.bean.LoginTicket;
import com.lemon.community.bean.User;
import com.lemon.community.mapper.LoginTicketMapper;
import com.lemon.community.mapper.UserMapper;
import com.lemon.community.service.UserService;
import com.lemon.community.util.CommunityConstant;
import com.lemon.community.util.CommunityUtil;
import com.lemon.community.util.HostHolder;
import com.lemon.community.util.MailClient;
import org.apache.catalina.Host;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * @version 1.0.0
 * @ClassName UserServiceImpl.java
 * @Description 用户服务实现类
 */
@Service
public class UserServiceImpl implements UserService, CommunityConstant {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private LoginTicketMapper loginTicketMapper;
    @Autowired
    private HostHolder hostHolder;
    @Override
    public User getUserById(Integer id) {
        return userMapper.selectUserById(id);
    }
    @Value("${community.path.domain}")
    private String domain;
    @Value(("${server.servlet.context-path}"))
    private String contextPath;
    @Autowired
    private TemplateEngine templateEngine;
    @Autowired
    private MailClient mailClient;
    @Override
    public Map<String, Object> register(User user) {
        Map<String,Object> result = new HashMap<>();
        //1.空值检验
        if(user == null) {
            throw new IllegalArgumentException("参数不能为空！");
        }
        if(user.getUsername()==null) {
            result.put("usernameMsg","用户名不能为空！");
            return result;
        }
        if(user.getPassword()==null) {
            result.put("passwordMsg","密码不能为空！");
            return result;
        }
        if(user.getEmail()==null) {
            result.put("emailMsg","邮箱不能为空！");
            return result;
        }
        //2.用户名以及邮箱重复检验
        if(userMapper.selectUserByUsername(user.getUsername()) != null) {
            result.put("usernameMsg","用户名已经被使用");
            return result;
        }
        if(userMapper.selectUserByEmail(user.getEmail()) != null) {
            result.put("emailMsg", "邮箱已经被使用");
            return result;
        }
        //3.补充user相关信息
        //添加盐值
        user.setSalt(CommunityUtil.generateUUID().substring(0,5));
        user.setPassword(CommunityUtil.md5(user.getPassword() + user.getSalt()));
        user.setType(0);
        user.setStatus(0);
        user.setActivationCode(CommunityUtil.generateUUID());
        //使用牛客网的随机头像
        user.setHeaderUrl(String.format("http://images.nowcoder.com/head/%dt.png", new Random().nextInt(1000)));
        user.setCreatedTime(new Date());
        //4.将数据插入到数据库中
        userMapper.insertUser(user);
        //5.发送激活邮件
        this.sendActivationMail(user);
        return result;
    }

    @Override
    public int activation(int userId, String code) {
        //1.根据用户id查询用户
        User user = userMapper.selectUserById(userId);
        //2.根据状态字段判断
        if (user.getStatus() == 1) {
            return ACTIVATION_REPEAT;
        } else if (user.getActivationCode().equals(code)) {
            userMapper.updateStatus(userId, 1);
            return ACTIVATION_SUCCESS;
        } else {
            return ACTIVATION_FAILURE;
        }
    }

    @Override
    public Map<String, Object> login(String username, String password, int expiredSeconds) {
        Map<String,Object> map = new HashMap<>();
        //1.空值检验
        if(username == null) {
            map.put("usernameMsg", "用户名不能为空！");
            return map;
        }
        if(password == null) {
            map.put("passwordMsg", "密码不能为空！");
            return map;
        }
        //2.账号检验
        User user = userMapper.selectUserByUsername(username);
        if(user == null) {
            map.put("usernameMsg","不存在此用户！");
            return map;
        }
        //3.状态检验
        if(user.getStatus() == 0) {
            map.put("usernameMsg","该账号未激活！");
            return map;
        }
        //4.密码检验
        password = CommunityUtil.md5(password + user.getSalt());
        if(!password.equals(user.getPassword())) {
            map.put("passwordMsg","密码不正确！");
            return map;
        }
        //5.生成登录凭证
        LoginTicket loginTicket = new LoginTicket();
        loginTicket.setUserId(user.getId());
        loginTicket.setTicket(CommunityUtil.generateUUID());
        loginTicket.setStatus(0);
        loginTicket.setExpired(new Date(System.currentTimeMillis() + expiredSeconds * 1000));
        loginTicketMapper.insertLoginTicket(loginTicket);
        map.put("ticket", loginTicket.getTicket());
        return map;
    }

    @Override
    public int logout(String ticket) {
        int result = loginTicketMapper.updateStatus(ticket,1);
        return result;
    }

    @Override
    public int updateHeader(Integer id, String headerUrl) {
        return userMapper.updateHeader(id, headerUrl);
    }

    @Override
    public Map<String, Object> changePassword(String oldPassword, String newPassword) {
        Map<String, Object> map = new HashMap<>();
        User user = hostHolder.getUser();
//        if(user == null) {
//            map.put("loginStatusMsg","当前未登录！");
//        }
        oldPassword = CommunityUtil.md5( oldPassword + user.getSalt());
        if(!oldPassword.equals(user.getPassword())) {
            map.put("passwordMsg", "原密码错误！");
            return map;
        }
        newPassword = CommunityUtil.md5(newPassword + user.getSalt());
        userMapper.updatePassword(user.getId(), newPassword);
        return map;
    }

    /**
     * 发送激活邮件
     * @param user
     */
    private void sendActivationMail(User user) {
        Context context = new Context();
        context.setVariable("email",user.getEmail());
        // http://localhost:8080/community/activation/101/code
        String url = domain + contextPath +
                "activation" + user.getId() +
                "/" + user.getActivationCode();
        context.setVariable("url", url);
        String content = templateEngine.process("/mail/activation", context);
        mailClient.sendMail(user.getEmail(),"激活账号",content);
    }
}
