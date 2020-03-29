package com.graduation.demo.dao;

import com.graduation.demo.common.entity.Role;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RoleMapper {

    int insert(Role record);

    int insertSelective(Role record);
}