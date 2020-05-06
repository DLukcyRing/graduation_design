package com.graduation.demo.controller;

import com.github.pagehelper.PageInfo;
import com.graduation.demo.common.entity.Company;
import com.graduation.demo.common.entity.User;
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
        ModelAndView model = new ModelAndView("/company/index");
        List<Company> data = companyService.queryList();
        model.addObject("data", data);
        return model;
    }


    @ResponseBody
    @RequestMapping(value = "/index", method = RequestMethod.POST)
    public String queryCompany(int id){
        System.out.println(id);
        Company company = companyService.queryCompanyById(id);
        return company.toString();
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public ModelAndView addCompany() {
        return new ModelAndView("/company/companyEdit");
    }

}

