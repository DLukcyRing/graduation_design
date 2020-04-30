package com.graduation.demo.dao;

import com.graduation.demo.common.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserMapper {

    List<User> queryList();
    User queryUserByName(String userName);
    List<User> selectUserByName(String userName);
    int addUser(User user);
    int deleteUserById(String id);
    int updateUserById(User user);
    User queryUserById(String id);
}