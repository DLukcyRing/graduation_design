package com.graduation.demo.controller;

import com.alibaba.druid.util.Base64;
import com.alibaba.fastjson.JSONArray;
import com.github.pagehelper.util.StringUtil;
import com.graduation.demo.common.entity.Company;
import com.graduation.demo.common.entity.Ur;
import com.graduation.demo.common.entity.User;
import com.graduation.demo.service.CompanyService;
import com.graduation.demo.service.UrService;
import com.graduation.demo.service.UserService;
import org.apache.catalina.util.URLEncoder;
import org.apache.shiro.SecurityUtils;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/user")
public class UserController {

    @Resource
    private UserService userService;

    @Resource
    private CompanyService companyService;

    @Resource
    private UrService urService;

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public ModelAndView index() {
        ModelAndView model = new ModelAndView("/user/index");
        List<User> data = userService.queryList();
        model.addObject("data", data);
        return model;
    }

    @RequestMapping(value = "/blank", method = RequestMethod.GET)
    public ModelAndView blank() {
        ModelAndView model = new ModelAndView("/user/blank");
        List<User> data = userService.queryList();
        model.addObject("data", data);
        return model;
    }

    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public ModelAndView editByUserId(@RequestParam(value = "userId") String id) {
        ModelAndView model = new ModelAndView("/user/userEdit");
        if (StringUtil.isNotEmpty(id)) {
            User data = userService.queryUserById(id);
            List<Company> company = companyService.queryList();
            model.addObject("data", data);
            model.addObject("company", company);
        }
        return model;
    }

    @ResponseBody
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public Map<String, Object> editByUserId(@RequestBody Map<String, Object> param) {
        Map<String, Object> map = new HashMap<>();
        if (userService.editUser(param)) {
            map.put("code", 0);
            map.put("message", "修改成功");
        } else {
            map.put("code", -1);
            map.put("message", "修改失败");
        }
        return map;
    }

    @ResponseBody
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public Map<String, Object> deleteByUserId(@RequestBody Map<String, String> param) {
        Map<String, Object> map = new HashMap<>();
        String id = param.get("userId");
        if (StringUtil.isNotEmpty(id)) {
            if (userService.deleteByUserId(id)) {
                map.put("code", 0);
                map.put("message", "删除成功");
            } else {
                map.put("code", -1);
                map.put("message", "删除失败");
            }
        }
        return map;
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public ModelAndView addUserView() {
        ModelAndView model = new ModelAndView("/user/userAdd");
        List<Company> companyList = companyService.queryList();
        model.addObject("company", companyList);
        return model;
    }

    @ResponseBody
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Map<String, Object> addUser(@RequestBody Map<String, Object> param) {
        Map<String, Object> map = new HashMap<>();
        if (userService.queryUserByName((String) param.get("username")) != null) {
            map.put("code", -1);
            map.put("message", "账户已存在");
        } else {
            if (userService.addUser(param)) {
                map.put("code", 0);
                map.put("message", "新增成功");
            } else {
                map.put("code", -1);
                map.put("message", "新增失败");
            }
        }
        return map;
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

    @RequestMapping(value = "/edit2", method = RequestMethod.GET)
    public ModelAndView editByUserId2() {
        ModelAndView model = new ModelAndView("/user/userEdit");
        String token = (String) SecurityUtils.getSubject().getPrincipal();
        User userId = userService.queryUserByName(token);
        if (StringUtil.isNotEmpty(userId.getId())) {
            List<Company> company = companyService.queryList();
            model.addObject("data", userId);
            model.addObject("company", company);
        }
        return model;
    }

    @ResponseBody
    @RequestMapping(value = "/edit2", method = RequestMethod.POST)
    public Map<String, Object> editByUserId2(@RequestBody Map<String, Object> param) {
        Map<String, Object> map = new HashMap<>();
        if (userService.editUser(param)) {
            map.put("code", 0);
            map.put("message", "修改成功");
        } else {
            map.put("code", -1);
            map.put("message", "修改失败");
        }
        return map;
    }

    @ResponseBody
    @RequestMapping(value = "/getRole", method = RequestMethod.POST)
    public List<Ur> getRole(@RequestBody Map<String, Object> param) {
        return urService.queryByUserId((String) param.get("userId"));
    }
}
