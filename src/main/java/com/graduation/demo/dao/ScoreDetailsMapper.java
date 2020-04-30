package com.graduation.demo.dao;

import com.graduation.demo.common.entity.ScoreDetails;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ScoreDetailsMapper {

    List<ScoreDetails> queryList();
    List<ScoreDetails> selectScoreDetailsById(int userId);
    int addScoreDetails(ScoreDetails scoreDetails);
    int deleteScoreDetailsById(String identifier);
    int updateScoreDetailsById(ScoreDetails scoreDetails);
}