package com.graduation.demo.dao;

import com.graduation.demo.common.entity.Role;
import com.graduation.demo.common.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RoleMapper {

    Role queryRoleByUrId(String id);

    List<Role> queryList();
}