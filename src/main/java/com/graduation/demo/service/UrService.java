package com.graduation.demo.service;

import com.graduation.demo.common.entity.Ur;
import com.graduation.demo.dao.UrMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class UrService {

    @Resource
    private UrMapper urMapper;


    public void insertByUserId(String roleid, String userid) throws Exception {
        Ur ur = new Ur();
        ur.setId(UUID.randomUUID().toString().replaceAll("-", ""));
        ur.setRoleid(roleid);
        ur.setUserid(userid);
        if (urMapper.insertByUserId(ur) <= 0) {
            throw new Exception("添加异常");
        }
    }

    public void deleteByUserId(String userid) {
        urMapper.deleteByUserId(userid);
    }

    public List<Ur> queryByUserId(String userid) {
        return urMapper.queryListByUserId(userid);
    }
}
