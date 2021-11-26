package com.lemon.community.mapperTest;

import com.lemon.community.mapper.DiscussPostMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @version 1.0.0
 * @ClassName DiscussPostMapperTests.java
 * @Description TODO
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class DiscussPostMapperTests {

    @Autowired
    private DiscussPostMapper discussPostMapper;
    @Test
    public void findAllPost(){
        System.out.println(discussPostMapper.findDiscussPosts(149,0,5));
    }
    @Test
    public void countPosts(){
        System.out.println(discussPostMapper.discussPostsCount(149));
    }
}
