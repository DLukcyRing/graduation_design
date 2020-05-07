package com.graduation.demo.service;


import com.graduation.demo.common.entity.ScoreDetails;
import com.graduation.demo.common.entity.User;
import com.graduation.demo.dao.ScoreDetailsMapper;
import com.graduation.demo.dao.UserMapper;
import com.graduation.demo.dao.UserMapper;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;


@Service
@Transactional
public class ScoreService {

    @Resource
    private ScoreDetailsMapper scoreDetailsMapper;

    @Resource
    private UserMapper userMapper;

    public List<ScoreDetails> queryList(){return scoreDetailsMapper.queryList();}
    public List<ScoreDetails> selectScoreDetailsById(int userId){return scoreDetailsMapper.selectScoreDetailsById(userId);}
    public int deleteScoreDetailsById(String identifier){return scoreDetailsMapper.deleteScoreDetailsById(identifier);}
    public int updateScoreDetailsById(ScoreDetails scoreDetails){return scoreDetailsMapper.updateScoreDetailsById(scoreDetails);}

    public int addScoreDetails(Map<String, Object> param){

        ScoreDetails scoreDetails = new ScoreDetails();

        String token = (String) SecurityUtils.getSubject().getPrincipal();
        User userId = userMapper.queryUserByName(token);

        scoreDetails.setAddReduce(Integer.parseInt((String) param.get("addreduce")));
        scoreDetails.setUserid(userMapper.queryUserByName((String) param.get("username")).getId());
        double score = Double.parseDouble((String) param.get("score"));
        if (score>10){
            scoreDetails.setApprovalid("1");
        }
        scoreDetails.setScore(score);

        scoreDetails.setRegistrarid(userId.getId());

        return scoreDetailsMapper.addScoreDetails(scoreDetails);
    }

    public List<ScoreDetails> selectScoreByNullCheckerId (){ return scoreDetailsMapper.selectScoreByNullCheckerId(); }
    public ScoreDetails queryScoreDetailsById(int id){return scoreDetailsMapper.queryScoreDetailsById(id);}

    public List<ScoreDetails> selectScoreByScore10() {return scoreDetailsMapper.selectScoreByScore10();
    }

    public List<ScoreDetails> selectScoreByPlead() {return scoreDetailsMapper.selectScoreByPlead();
    }

    public List<ScoreDetails> queryScoreDetailsByUserId(String id) {return scoreDetailsMapper.queryScoreDetailsByUserId(id);
    }

    public List<ScoreDetails> selectScoreByUserid(String id) {return scoreDetailsMapper.selectScoreByUserid(id);
    }
}
