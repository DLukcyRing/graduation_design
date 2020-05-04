package com.graduation.demo.service;


import com.graduation.demo.common.entity.User;
import com.graduation.demo.common.utils.IdGen;
import com.graduation.demo.common.utils.Md5Utils;
import com.graduation.demo.dao.UserMapper;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;


@Service
@Transactional
public class UserService {

    @Resource
    private UserMapper userMapper;

    public List<User> queryList() {
        return userMapper.queryList();
    }

    public boolean addUser(Map<String, Object> param) {
        User user = new User();
        user.setId(IdGen.getUUID().replaceAll("-", ""));
        user.setAccount((String) param.get("username"));
        user.setPassword(Md5Utils.encode((String) param.get("password")));
        user.setName((String) param.get("name"));
        user.setCandelete("Y");
        user.setDuty((String) param.get("duty"));
        user.setEdu((String) param.get("edu"));
        user.setSex((String) param.get("sex"));
        user.setState("PUB");
        String token = (String) SecurityUtils.getSubject().getPrincipal();
        User userId = userMapper.queryUserByName(token);
        user.setCreateby(userId.getId());
        user.setUpdateby(userId.getId());
        user.setCreatetime(new Date());
        user.setUpdatetime(new Date());
        if (param.get("company") != null) {
            user.setCompanyid((String) param.get("company"));
        }
        return userMapper.addUser(user) > 0;
    }

    public User queryUserByName(String userName) {
        return userMapper.queryUserByName(userName);
    }

    public User queryUserById(String id) {
        return userMapper.queryUserById(id);
    }

    public boolean editUser(Map<String, Object> param) {
        User user = userMapper.queryUserById((String) param.get("userid"));
        user.setAccount((String) param.get("username"));
        user.setPassword(Md5Utils.encode((String) param.get("password")));
        user.setName((String) param.get("name"));
        user.setDuty((String) param.get("duty"));
        user.setEdu((String) param.get("edu"));
        user.setSex((String) param.get("sex"));
        String token = (String) SecurityUtils.getSubject().getPrincipal();
        User userId = userMapper.queryUserByName(token);
        user.setUpdateby(userId.getId());
        user.setUpdatetime(new Date());
        if (param.get("company") != null) {
            user.setCompanyid((String) param.get("company"));
        }
        return userMapper.editUserById(user) > 0;
    }

    public boolean deleteByUserId(String id) {
        return userMapper.deleteByUserId(id) > 0;
    }
}
