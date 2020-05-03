package com.graduation.demo.controller;

import com.alibaba.druid.support.json.JSONUtils;
import com.github.pagehelper.util.StringUtil;
import com.graduation.demo.common.entity.User;
import com.graduation.demo.common.utils.StringUtils;
import com.graduation.demo.service.UrService;
import com.graduation.demo.service.UserService;
import org.apache.ibatis.annotations.Param;
import org.apache.tomcat.util.http.RequestUtil;
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

    @Resource
    private UrService urService;

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public ModelAndView index() {
        ModelAndView model = new ModelAndView("/user/index");
        List<User> data = userService.queryList();
        model.addObject("data", data);
        return model;
    }

    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public ModelAndView editByUserId(@RequestParam(value = "userId") String id) {
        ModelAndView model = new ModelAndView("/user/userEdit");
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

    @ResponseBody
    @RequestMapping(value = "/addUR", method = RequestMethod.POST)
    public String addUR(@RequestBody Map<String, Object> param) {
        List<String> roleIds = (List<String>) param.get("param");
        String userid = (String) param.get("userId");
        urService.deleteByUserId(userid);
        for (String roleid : roleIds) {
            try {
                urService.insertByUserId(roleid, userid);
            } catch (Exception e) {
                e.printStackTrace();
                return "error";
            }
        }
        return "success";
    }
}
