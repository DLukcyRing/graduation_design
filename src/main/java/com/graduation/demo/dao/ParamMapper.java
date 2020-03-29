package com.graduation.demo.dao;

import com.graduation.demo.common.entity.Param;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ParamMapper {

    int deleteByPrimaryKey(String id);

    int insert(Param record);

    int insertSelective(Param record);

    Param selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Param record);

    int updateByPrimaryKey(Param record);
}