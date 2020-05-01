package com.graduation.demo.controller;

import com.github.pagehelper.PageInfo;
import com.graduation.demo.common.entity.Company;
import com.graduation.demo.service.CompanyService;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/company")
public class CompanyController {

    @Resource
    private CompanyService companyService;

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public ModelAndView index() {
        return new ModelAndView("/company/index");
    }

    @ResponseBody
    @RequestMapping(value = "/queryList", method = RequestMethod.GET)
    public PageInfo<Company> queryList(){
        List<Company> data = companyService.queryList();
        PageInfo<Company> pageInfo = new PageInfo<>();
        pageInfo.setList(data);
        for (Company o:data) {
            System.out.println(o.toString());
        }
        return pageInfo;
    }

    @ResponseBody
    @RequestMapping(value = "/index", method = RequestMethod.POST)
    public String queryCompany(int id){
        System.out.println(id);
        Company company = companyService.queryCompanyById(id);
        return company.toString();
    }
}
