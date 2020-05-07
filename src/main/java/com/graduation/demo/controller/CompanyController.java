package com.graduation.demo.controller;

import com.github.pagehelper.PageInfo;
import com.github.pagehelper.util.StringUtil;
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
    public String queryCompany(String id){
        System.out.println(id);
        Company company = companyService.queryCompanyById(id);
        return company.toString();
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public ModelAndView addUserView() {
        ModelAndView model = new ModelAndView("/company/companyAdd");
//        String companyList = URLEncoder.DEFAULT.encode(JSONArray.toJSONString(companyService.queryList()),Charset.defaultCharset());
        List<Company> companyList = companyService.queryList();
        model.addObject("company", companyList);
        return model;
    }

    @ResponseBody
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Map<String, Object> addUser(@RequestBody Map<String, Object> param) {
        Map<String, Object> map = new HashMap<>();
        if (companyService.queryCompanyById((String) param.get("companyid")) != null) {
            map.put("code", -1);
            map.put("message", "公司已存在");
        } else {
            if (companyService.addCompany(param)) {
                map.put("code", 0);
                map.put("message", "新增成功");
            } else {
                map.put("code", -1);
                map.put("message", "新增失败");
            }
        }
        System.out.println();
        return map;
    }

    @ResponseBody
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public Map<String, Object> deleteByCompanyId(@RequestBody Map<String, String> param) {
        Map<String, Object> map = new HashMap<>();
        String id = param.get("companyid");
        if (StringUtil.isNotEmpty(id)) {
            if (companyService.deleteByCompanyId(id)) {
                map.put("code", 0);
                map.put("message", "删除成功");
            } else {
                map.put("code", -1);
                map.put("message", "删除失败");
            }
        }
        return map;
    }

}

