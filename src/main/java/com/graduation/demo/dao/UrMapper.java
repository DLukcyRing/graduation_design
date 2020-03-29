package com.graduation.demo.dao;

import com.graduation.demo.common.entity.Ur;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UrMapper {

    int deleteByPrimaryKey(String id);

    int insert(Ur record);

    int insertSelective(Ur record);

    Ur selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Ur record);

    int updateByPrimaryKey(Ur record);
}