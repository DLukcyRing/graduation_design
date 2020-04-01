package com.graduation.demo.service;


import com.graduation.demo.common.entity.User;
import com.graduation.demo.dao.UserMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;


@Service
@Transactional
public class UserService {

    @Resource
    private UserMapper userMapper;


    public List<User> queryList() {
        return userMapper.queryList();
    }

}
