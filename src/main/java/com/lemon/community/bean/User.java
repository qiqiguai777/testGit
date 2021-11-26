package com.lemon.community.bean;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

/**
 * @version 1.0.0
 * @ClassName User.java
 * @Description 用户实体类
 */
@Data
@NoArgsConstructor
@ToString
public class User {

    private Integer id;
    private String username;
    private String password;
    private String salt;
    private String email;
    private Integer type;
    private Integer status;
    private String activationCode;
    private String headerUrl;
    private Date createdTime;
}
