package com.graduation.demo.service;


import com.graduation.demo.common.entity.Role;
import com.graduation.demo.dao.RoleMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;


@Service
@Transactional
public class RoleService {

    @Resource
    private RoleMapper roleMapper;

    public List<Role> queryList() {
        return roleMapper.queryList();
    }
}
