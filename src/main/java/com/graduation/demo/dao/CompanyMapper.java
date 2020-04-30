package com.graduation.demo.dao;

import com.graduation.demo.common.entity.Company;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface CompanyMapper {

    List<Company> queryList();

    int addCompany(Company company);

    int deleteCompanyById(int id);

    int updateCompanyById(Company company);

    //public Company queryCompanyByName(String userName);

    Company queryCompanyById(@Param("id")int id);
}