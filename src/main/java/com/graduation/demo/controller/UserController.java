package com.graduation.demo.controller;

import com.github.pagehelper.PageInfo;
import com.graduation.demo.common.entity.User;
import com.graduation.demo.service.UserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping(value = "/user")
public class UserController {

    @Resource
    private UserService userService;

    @ResponseBody
    @RequestMapping(value = "/queryList", method = RequestMethod.GET)
    public PageInfo<User> queryList() {
        List<User> data = userService.queryList();
        PageInfo<User> pageInfo = new PageInfo<>();
        pageInfo.setList(data);
        return pageInfo;
    }
}
