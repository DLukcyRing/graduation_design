package com.graduation.demo.dao;

import com.graduation.demo.common.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {

    int insert(User record);

    int insertSelective(User record);

    List<User> queryList();

}