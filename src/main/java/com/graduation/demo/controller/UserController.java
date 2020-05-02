package com.graduation.demo.controller;

import com.alibaba.druid.support.json.JSONUtils;
import com.github.pagehelper.util.StringUtil;
import com.graduation.demo.common.entity.User;
import com.graduation.demo.common.utils.StringUtils;
import com.graduation.demo.service.UserService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/user")
public class UserController {

    @Resource
    private UserService userService;

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public ModelAndView index() {
        ModelAndView model = new ModelAndView("/user/index");
        List<User> data = userService.queryList();
        model.addObject("data", data);
        return model;
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public ModelAndView editByUserId(@RequestBody Map<String, String> param) {
        ModelAndView model = new ModelAndView("/user/userEdit");
        String id = param.get("id");
        if (StringUtil.isNotEmpty(id)) {
            User data = userService.queryUserById(id);
            model.addObject("data", data);
        }
        return model;
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public ModelAndView addUser() {
        return new ModelAndView("/user/userEdit");
    }

}
