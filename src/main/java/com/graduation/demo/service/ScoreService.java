package com.graduation.demo.service;


import com.graduation.demo.common.entity.ScoreDetails;
import com.graduation.demo.common.entity.User;
import com.graduation.demo.dao.ScoreDetailsMapper;
import com.graduation.demo.dao.UserMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;


@Service
@Transactional
public class ScoreService {

    @Resource
    private ScoreDetailsMapper scoreDetailsMapper;

    public List<ScoreDetails> queryList(){return scoreDetailsMapper.queryList();}
    public List<ScoreDetails> selectScoreDetailsById(int userId){return scoreDetailsMapper.selectScoreDetailsById(userId);}
    public int addScoreDetails(ScoreDetails scoreDetails){return scoreDetailsMapper.addScoreDetails(scoreDetails);}
    public int deleteScoreDetailsById(String identifier){return scoreDetailsMapper.deleteScoreDetailsById(identifier);}
    public int updateScoreDetailsById(ScoreDetails scoreDetails){return scoreDetailsMapper.updateScoreDetailsById(scoreDetails);}

}
