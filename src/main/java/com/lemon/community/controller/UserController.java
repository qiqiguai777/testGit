package com.lemon.community.controller;

import com.lemon.community.annotation.LoginRequired;
import com.lemon.community.bean.User;
import com.lemon.community.service.UserService;
import com.lemon.community.util.CommunityUtil;
import com.lemon.community.util.HostHolder;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Map;

/**
 * @version 1.0.0
 * @ClassName UserController.java
 * @Description 用户信息控制类
 */
@Controller
@RequestMapping("/user")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private UserService userService;
    @Autowired
    private HostHolder hostHolder;
    @Value("${community.path.upload}")
    private String uploadPath;
    @Value("${community.path.domain}")
    private String domain;
    @Value("${server.servlet.context-path}")
    private String contextPath;
    @LoginRequired
    @GetMapping("/setting")
    public String getSettingPage() {
        return "/site/setting";
    }
    @LoginRequired
    @PostMapping("/upload")
    public String uploadHeader(MultipartFile headerImage, Model model) {
        if(headerImage == null) {
            model.addAttribute("error","您还没有选择图片！");
            return "/site/setting";
        }
        String filename = headerImage.getOriginalFilename();
        String suffix = filename.substring(filename.lastIndexOf("."));
        if(StringUtils.isBlank(suffix)) {
            model.addAttribute("error", "文件格式不正确！");
            return "/site/setting";
        }
        //生成随机文件名
        filename = CommunityUtil.generateUUID() +suffix;
        //确定文件存放路径
        File dest = new File(uploadPath + "/" + filename);
        try {
            //存储文件
            headerImage.transferTo(dest);
        } catch (IOException e) {
            logger.error("上传文件失败: " + e.getMessage());
            throw new RuntimeException("上传文件失败,服务器发生异常!", e);
        }
        //更新用户头像的路径(web访问路径)
        // http://localhost:8080/community/user/header/xxx.png
        User user = hostHolder.getUser();
        String headerUrl = domain + contextPath + "/user/header/" +filename;
        userService.updateHeader(user.getId(), headerUrl);
        return "redirect:/index";
    }
    @GetMapping("/header/{fileName}")
    public void getHeader(@PathVariable("fileName") String fileName, HttpServletResponse response) {
        //构建图标在服务器的存放路径
        fileName = uploadPath + "/" +fileName;
        //文件后缀
        String suffix = fileName.substring(fileName.lastIndexOf("."));
        //传输图片
        try {
            FileInputStream fis = new FileInputStream(fileName);
            OutputStream os = response.getOutputStream();
            byte[] buffer = new byte[1024];
            int b = 0;
            while((b = fis.read(buffer)) != -1) {
                os.write(buffer, 0 ,b);
            }
        } catch (IOException e) {
            logger.error("读取头像失败: " + e.getMessage());
        }
    }
    @PostMapping("/change_password")
    public String changePassword(String oldPassword, String newPassword, Model model) {
        if(oldPassword == null ) {
            model.addAttribute("passwordMsg", "原密码不能为空！");
            return "/site/setting";
        }
        if(newPassword == null ) {
            model.addAttribute("newPasswordMsg", "新密码不能为空！");
            return "/site/setting";
        }
        Map<String,Object> map = userService.changePassword(oldPassword, newPassword);
        if(map == null || map.isEmpty()) {
            model.addAttribute("successMsg","修改密码成功！");
            return "redirect:/index";
        } else {
//            model.addAttribute("loginStatusMsg",map.get("loginStatusMsg"));
            model.addAttribute("passwordMsg",map.get("passwordMsg"));
            return "/site/setting";
        }
    }
}
