package com.lemon.community.controller;

import com.lemon.community.bean.DiscussPost;
import com.lemon.community.bean.Page;
import com.lemon.community.bean.User;
import com.lemon.community.service.DiscussPostService;
import com.lemon.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @version 1.0.0
 * @ClassName HomeController.java
 * @Description 关于首页
 */
@Controller
public class HomeController {

    @Autowired
    private DiscussPostService discussPostService;
    @Autowired
    private UserService userService;
//    @RequestMapping({"/index","/"})
//    public String index(){
//        return "index";
//    }
    @GetMapping("/index")
    public String getIndexPage(Model model, Page page){
        //将话题总数量添加到页面实体中用于分页
        page.setRows(discussPostService.countPosts(0));
        //设置路径
        page.setPath("/index");
        List<DiscussPost> list = discussPostService.getDiscussPost(0,page.getOffset(),page.getLimit());
        List<Map<String,Object>> discussPosts = new ArrayList<>();
        if(list != null) {
            //将每条话题及关联的用户信息放到list集合中返回给前端页面
            for(DiscussPost post : list){
                Map<String,Object> map = new HashMap<>();
                map.put("post",post);
                User user = userService.getUserById(post.getUserId());
                map.put("user",user);
                discussPosts.add(map);
            }
        }
        model.addAttribute("discussPosts",discussPosts);
        return "index";
    }
}
