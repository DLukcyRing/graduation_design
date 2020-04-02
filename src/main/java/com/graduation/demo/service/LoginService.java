package com.graduation.demo.service;


import com.graduation.demo.common.entity.Role;
import com.graduation.demo.common.entity.Ur;
import com.graduation.demo.common.entity.User;
import com.graduation.demo.dao.RoleMapper;
import com.graduation.demo.dao.UrMapper;
import com.graduation.demo.dao.UserMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class LoginService {

    @Resource
    private UserMapper userMapper;
    @Resource
    private RoleMapper roleMapper;
    @Resource
    private UrMapper urMapper;

    public Map<String, Object> getUserByName(String userName) {
        User user = userMapper.queryUserByName(userName);
        List<Ur> ur = urMapper.queryListByUserId(user.getId());
        List<Role> roles = new ArrayList<>();
        for (Ur entity : ur) {
            roles.add(roleMapper.queryRoleByUrId(entity.getId()));
        }
        Map<String, Object> map = new HashMap<>();
        map.put("user", user);
        map.put("role", roles);
        return map;
    }

}
