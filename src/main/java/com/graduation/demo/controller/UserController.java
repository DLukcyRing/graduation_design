package com.graduation.demo.controller;

import com.github.pagehelper.PageInfo;
import com.graduation.demo.common.entity.User;
import com.graduation.demo.service.UserService;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import java.text.SimpleDateFormat;

@RestController
@RequestMapping(value = "/user")
public class UserController {

    @Resource
    private UserService userService;

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public ModelAndView index() {
        return new ModelAndView("/user/index");
    }


    @ResponseBody
    @RequestMapping(value = "/queryList", method = RequestMethod.GET)
    public PageInfo<User> queryList() {
        List<User> data = userService.queryList();
        PageInfo<User> pageInfo = new PageInfo<>();
        pageInfo.setList(data);
        for (User o:data) {
            System.out.println(o.toString());
            System.out.println(" Query");
        }
        return pageInfo;
    }

    @ResponseBody
    @RequestMapping(value = "/selectbyname", method = RequestMethod.POST)
    public PageInfo<User> selectName(String name) {
        List<User> data = userService.selectUserByName(name);
        System.out.println("/selectName:");
        for (User o :data) {
            System.out.println(o.toString());
        }
        PageInfo<User> pageInfo = new PageInfo<>();
        pageInfo.setList(data);
        return pageInfo;
    }


    @ResponseBody
    @RequestMapping(value = "/adduser", method = RequestMethod.GET) //receive post
    public ModelAndView addUser() {
        return new ModelAndView("/user/adduser");
    }
//PageInfo Post我无法测试，自行更改叭
//    @ResponseBody
//    @RequestMapping(value = "/adduser", method = RequestMethod.POST) //receive post
//    public String addUserHandler(PageInfo<User> userPageInfo) { //?
//        List<User> data = userPageInfo.getList();
//        for (User o:data) {
//            userService.addUser(o);
//        }
//        return "Add success";
//    }

    @ResponseBody
    @RequestMapping(value = "/adduser", method = RequestMethod.POST) //正常的post数据
    public ModelAndView addUserHandler(String id,String name,String password) { //?
        User user = new User(id,name,password);
        userService.addUser(user);
        return new ModelAndView("redirect:/user/queryList");
    }

    @ResponseBody
    @RequestMapping(value = "/deleteuser", method = RequestMethod.GET)//receive id
    public String deleteUser(@RequestParam(required = false, defaultValue = " ")String id) {
        userService.deleteUserById(id);
        return "Delete Success";
    }//return 1

    @ResponseBody
    @RequestMapping(value = "/deleteuser", method = RequestMethod.POST)//receive id
    public String deleteUserHandler(String id) {
        User user = userService.queryUserById(id);
        userService.deleteUserById(id);
        return user.toString()+"Delete Success";
    }


    @ResponseBody
    @RequestMapping(value = "/updateuser", method = RequestMethod.POST)//receive all
    public String updateUserHandler(){
        User user = new User();
        userService.updateUserById(user);
        return "Update Success" + user.toString();
    }

    @ResponseBody
    @RequestMapping(value = "/updateuser", method = RequestMethod.GET)//receive all
    public String updateUser(){
        User user = new User();
        userService.updateUserById(user);
        return "Update Success" + user.toString();
    }
}
