package com.lemon.community.bean;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

/**
 * @version 1.0.0
 * @ClassName DiscussPost.java
 * @Description 话题实体类
 */
@Data
@NoArgsConstructor
@ToString
public class DiscussPost {

    private Integer id;
    private Integer userId;
    private String title;
    private String content;
    /* 0普通 1置顶 */
    private Integer type;
    /* 0正常 1精华 2拉黑 */
    private Integer status;
    private Date createdTime;
    private Integer commentCount;
    private Double score;
}
