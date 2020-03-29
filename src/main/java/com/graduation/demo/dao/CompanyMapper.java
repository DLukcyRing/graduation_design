package com.graduation.demo.dao;

import com.graduation.demo.common.entity.Company;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CompanyMapper {

    int deleteByPrimaryKey(Integer companyid);

    int insert(Company record);

    int insertSelective(Company record);

    Company selectByPrimaryKey(Integer companyid);

    int updateByPrimaryKeySelective(Company record);

    int updateByPrimaryKey(Company record);
}