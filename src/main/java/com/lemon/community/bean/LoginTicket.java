package com.lemon.community.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @version 1.0.0
 * @ClassName LoginTicket.java
 * @Description 登录凭证
 */
@Data
@NoArgsConstructor
public class LoginTicket {

    private Integer id;
    private Integer userId;
    private String ticket;
    private Integer status;
    private Date expired;
}
