package com.graduation.demo.dao;

import com.graduation.demo.common.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserMapper {

    List<User> queryList();

    User queryUserByName(String userName);

    int addUser(User user);

    User queryUserById(String id);

    int editUserById(User user);

    int deleteByUserId(String id);
}