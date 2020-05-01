package com.graduation.demo.service;


import com.graduation.demo.common.entity.Company;
import com.graduation.demo.dao.CompanyMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;


@Service
@Transactional
public class CompanyService {

    @Resource
    private CompanyMapper companyMapper;

    public List<Company> queryList() {
        return companyMapper.queryList();
    }

    public int addCompany(Company company){ return companyMapper.addCompany(company); }

    public int deleteCompanyById(int id){ return companyMapper.deleteCompanyById(id); }

    public int updateCompanyById(Company company){ return companyMapper.updateCompanyById(company); }

    //public Company queryCompanyByName(String userName){ return companyMapper.queryUserByName(userName); }

    public Company queryCompanyById(int id){ return companyMapper.queryCompanyById(id); }

}
