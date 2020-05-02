package com.graduation.demo.dao;

import com.graduation.demo.common.entity.Ur;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UrMapper {
    List<Ur> queryListByUserId(String userId);

    int insertByUserId(Ur ur);

    void deleteByUserId(String userid);
}