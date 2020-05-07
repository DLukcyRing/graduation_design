package com.graduation.demo.service;


import com.graduation.demo.common.entity.Company;
import com.graduation.demo.common.entity.User;
import com.graduation.demo.common.utils.IdGen;
import com.graduation.demo.common.utils.Md5Utils;
import com.graduation.demo.dao.CompanyMapper;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;


@Service
@Transactional
public class CompanyService {

    @Resource
    private CompanyMapper companyMapper;

    public List<Company> queryList() {
        return companyMapper.queryList();
    }

    public boolean addCompany(Map<String, Object> param) {
        Company company = new Company();
        company.setCompanyid(IdGen.getUUID().replaceAll("-", ""));
        company.setContact((String) param.get("contact"));
        company.setTelephone(Integer.valueOf((String) param.get("telephone")));
        company.setCompanyname((String) param.get("companyname"));
        company.setUSCC((String) param.get("USCC"));
        company.setAddress((String) param.get("address"));
        company.setRegisteredCapital((String) param.get("registered"));
        company.setBusinessScope((String) param.get("businessscope"));
        return companyMapper.addCompany(company) > 0;
    }

    public Company queryCompanyById(String id) {
        return companyMapper.queryCompanyById(id);
    }

    public boolean deleteByCompanyId(String id) {
        return companyMapper.deleteCompanyById(id) > 0;
    }

}
