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

    public int addUser(User user){ return userMapper.addUser(user); }

    public int deleteUserById(String id){ return userMapper.deleteUserById(id); }

    public int updateUserById(User user){ return userMapper.updateUserById(user); }

    public User queryUserByName(String userName){ return userMapper.queryUserByName(userName); }

    public User queryUserById(String id){ return userMapper.queryUserById(id); }

    public List<User> selectUserByName(String userName){ return userMapper.selectUserByName(userName); }

}
