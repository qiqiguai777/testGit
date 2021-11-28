package com.lemon.community;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

/**
 * @version 1.0.0
 * @ClassName DateTests.java
 * @Description TODO
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = CommunityApplication.class)
public class DateTests {

    @Test
    public void test() {
        System.out.println(new Date(System.currentTimeMillis()+ 3600*1000*24 *7 ));
    }
}
