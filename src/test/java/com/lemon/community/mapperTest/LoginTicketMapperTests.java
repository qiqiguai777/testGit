package com.lemon.community.mapperTest;

import com.lemon.community.CommunityApplication;
import com.lemon.community.bean.LoginTicket;
import com.lemon.community.mapper.LoginTicketMapper;
import com.lemon.community.util.CommunityUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

/**
 * @version 1.0.0
 * @ClassName LoginTicketMapperTests.java
 * @Description TODO
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = CommunityApplication.class)
public class LoginTicketMapperTests {

    @Autowired
    private LoginTicketMapper loginTicketMapper;
    @Test
    public void insertLoginTicketTest() {
        LoginTicket ticket = new LoginTicket();
        ticket.setUserId(1);
        ticket.setStatus(0);
        ticket.setExpired(new Date(System.currentTimeMillis()));
        ticket.setTicket(CommunityUtil.generateUUID());
        loginTicketMapper.insertLoginTicket(ticket);
    }
    @Test
    public void updateStatusTest() {
        loginTicketMapper.updateStatus("fab6ab6ffa394e6a9708ae77f841d891",1);
    }
    @Test
    public void selectTest() {
        LoginTicket ticket = loginTicketMapper.selectByTicket("fab6ab6ffa394e6a9708ae77f841d891");
        System.out.println(ticket);
    }

}
