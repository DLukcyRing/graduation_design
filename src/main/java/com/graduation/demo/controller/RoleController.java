package com.graduation.demo.controller;

import com.alibaba.fastjson.JSON;
import com.graduation.demo.common.entity.Param;
import com.graduation.demo.common.entity.Role;
import com.graduation.demo.service.RoleService;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping(value = "/role")
public class RoleController {

    @Resource
    private RoleService roleService;

    @ResponseBody
    @RequestMapping(value = "/getRole", method = RequestMethod.GET)
    public List<Role> getRoleTree() {
        List<Role> roles = roleService.queryList();
        return roles;
    }

}
