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
        company.setTelephone((String) param.get("telephone"));
        company.setCompanyname((String) param.get("companyname"));
        company.setUSCC((String) param.get("USCC"));
        company.setAddress((String) param.get("address"));
        company.setRegisteredCapital((String) param.get("registered_capital"));
        company.setBusinessScope((String) param.get("business_scope"));
//        String token = (String) SecurityUtils.getSubject().getPrincipal();
//        Company companyId = companyMapper.queryUserByName(token);
//        user.setCreateby(userId.getId());
//        user.setUpdateby(userId.getId());
//        user.setCreatetime(new Date());
//        user.setUpdatetime(new Date());
//        if (param.get("company") != null) {
//            user.setCompanyid((String) param.get("company"));
//        }
        return companyMapper.addCompany(company) > 0;
    }

    public Company queryCompanyById(String id) {
        return companyMapper.queryCompanyById(id);
    }

//    public boolean editUser(Map<String, Object> param) {
//        User user = userMapper.queryUserById((String) param.get("userid"));
//        user.setAccount((String) param.get("username"));
//        user.setPassword(Md5Utils.encode((String) param.get("password")));
//        user.setName((String) param.get("name"));
//        user.setDuty((String) param.get("duty"));
//        user.setEdu((String) param.get("edu"));
//        user.setSex((String) param.get("sex"));
//        String token = (String) SecurityUtils.getSubject().getPrincipal();
//        User userId = userMapper.queryUserByName(token);
//        user.setUpdateby(userId.getId());
//        user.setUpdatetime(new Date());
//        if (param.get("company") != null) {
//            user.setCompanyid((String) param.get("company"));
//        }
//        return userMapper.editUserById(user) > 0;
//    }

    public boolean deleteByCompanyId(String id) {
        return companyMapper.deleteCompanyById(id) > 0;
    }

}
