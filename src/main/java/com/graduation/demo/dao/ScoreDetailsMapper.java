package com.graduation.demo.dao;

import com.graduation.demo.common.entity.ScoreDetails;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ScoreDetailsMapper {

    int deleteByPrimaryKey(Integer identifier);

    int insert(ScoreDetails record);

    int insertSelective(ScoreDetails record);

    ScoreDetails selectByPrimaryKey(Integer identifier);

    int updateByPrimaryKeySelective(ScoreDetails record);

    int updateByPrimaryKey(ScoreDetails record);
}