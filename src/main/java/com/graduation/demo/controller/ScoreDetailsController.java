package com.graduation.demo.controller;

import com.github.pagehelper.PageInfo;
import com.graduation.demo.common.entity.Company;
import com.graduation.demo.common.entity.User;
import com.graduation.demo.dao.ScoreDetailsMapper;
import com.graduation.demo.service.CompanyService;
import com.graduation.demo.common.entity.ScoreDetails;
import com.graduation.demo.service.ScoreService;
import com.graduation.demo.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.LinkedList;

@RestController
@RequestMapping(value = "/scores")
public class ScoreDetailsController {

    @Resource
    private ScoreService scoreService;

    @Resource
    private UserService userService;

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public ModelAndView scoreRegisterGet(){
        return new ModelAndView("/scores/register");
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public Map<String, Object> scoreRegisterPost(@RequestBody Map<String, Object> param){
        Map<String, Object> map = new HashMap<>();
//        ModelAndView model = new ModelAndView("redirect:/user/index");
        User user = userService.queryUserByName((String) param.get("username"));

        if (scoreService.addScoreDetails(param)==1) {
            map.put("code", 0);
            map.put("message", "添加成功");
        } else {
            map.put("code", -1);
            map.put("message", "添加失败");
        }
        return map;
    }

    @RequestMapping(value = "/check", method = RequestMethod.GET)
    public ModelAndView checkGet(){
        ModelAndView model = new ModelAndView("/scores/check");

        List<ScoreDetails> scoreDetails= scoreService.selectScoreByNullCheckerId();

        List<String> usererName = new LinkedList<>();
        List<String> registerName = new LinkedList<>();

        for (ScoreDetails o:scoreDetails) {
            registerName.add(userService.queryUserById(o.getRegistrarid()).getName());
            usererName.add(userService.queryUserById(o.getUserid()).getName());
        }
        model.addObject("scoredetails",scoreDetails);
        model.addObject("userername",usererName);
        model.addObject("registername",registerName);
        return model;
    }

    @RequestMapping(value = "/check", method = RequestMethod.POST)
    public Map<String, Object> checkPost(@RequestBody Map<String, Object> param){
        Map<String, Object> map = new HashMap<>();
        String check = (String) param.get("check");
        int id = Integer.parseInt((String) param.get("id"));
        ScoreDetails scoreDetails = scoreService.queryScoreDetailsById(id);
        String token = (String) SecurityUtils.getSubject().getPrincipal();
        User userId = userService.queryUserByName(token);

        if (check.equals("success")){
            scoreDetails.setCheckerid(userId.getId());
        }else {
            scoreDetails.setCheckerid("0");
        }
        if(scoreService.updateScoreDetailsById(scoreDetails)==1){
            map.put("code", 0);
            map.put("message", "添加成功");
        }else {
            map.put("code", -1);
            map.put("message", "添加失败");
        }
        return map;
    }

    @RequestMapping(value = "/check2", method = RequestMethod.GET)
    public ModelAndView check2Get(){
        ModelAndView model = new ModelAndView("/scores/check2");

        List<ScoreDetails> scoreDetails= scoreService.selectScoreByPlead();

        List<String> usererName = new LinkedList<>();
        List<String> registerName = new LinkedList<>();

        for (ScoreDetails o:scoreDetails) {
            registerName.add(userService.queryUserById(o.getRegistrarid()).getName());
            usererName.add(userService.queryUserById(o.getUserid()).getName());
        }
        model.addObject("scoredetails",scoreDetails);
        model.addObject("userername",usererName);
        model.addObject("registername",registerName);
        return model;
    }

    @RequestMapping(value = "/check2", method = RequestMethod.POST)
    public Map<String, Object> check2Post(@RequestBody Map<String, Object> param){
        Map<String, Object> map = new HashMap<>();
        String check = (String) param.get("check2");
        int id = Integer.parseInt((String) param.get("id"));
        ScoreDetails scoreDetails = scoreService.queryScoreDetailsById(id);
        String token = (String) SecurityUtils.getSubject().getPrincipal();
        User userId = userService.queryUserByName(token);

        if (check.equals("success")){
            scoreDetails.setCheckerid2(userId.getId());
        }else {
            scoreDetails.setCheckerid2("0");
        }
        if(scoreService.updateScoreDetailsById(scoreDetails)==1){
            map.put("code", 0);
            map.put("message", "添加成功");
        }else {
            map.put("code", -1);
            map.put("message", "添加失败");
        }
        return map;
    }

    @RequestMapping(value = "/approval", method = RequestMethod.GET)
    public ModelAndView approvalGet(){
        ModelAndView model = new ModelAndView("/scores/approval");

        List<ScoreDetails> scoreDetails= scoreService.selectScoreByScore10();

        List<String> usererName = new LinkedList<>();
        List<String> registerName = new LinkedList<>();

        for (ScoreDetails o:scoreDetails) {
            registerName.add(userService.queryUserById(o.getRegistrarid()).getName());
            usererName.add(userService.queryUserById(o.getUserid()).getName());
        }
        model.addObject("scoredetails",scoreDetails);
        model.addObject("userername",usererName);
        model.addObject("registername",registerName);
        return model;
    }

    @RequestMapping(value = "/approval", method = RequestMethod.POST)
    public Map<String, Object> approvalPost(@RequestBody Map<String, Object> param){
        Map<String, Object> map = new HashMap<>();
        String check = (String) param.get("approval");
        int id = Integer.parseInt((String) param.get("id"));
        ScoreDetails scoreDetails = scoreService.queryScoreDetailsById(id);
        String token = (String) SecurityUtils.getSubject().getPrincipal();
        User userId = userService.queryUserByName(token);

        if (check.equals("success")){
            scoreDetails.setApprovalid(userId.getId());
        }else {
            scoreDetails.setApprovalid("0");
        }
        if(scoreService.updateScoreDetailsById(scoreDetails)==1){
            map.put("code", 0);
            map.put("message", "添加成功");
        }else {
            map.put("code", -1);
            map.put("message", "添加失败");
        }
        return map;
    }

    @RequestMapping(value = "/plead", method = RequestMethod.GET)
    public ModelAndView PleadGet(){
        ModelAndView model = new ModelAndView("/scores/plead");
        String token = (String) SecurityUtils.getSubject().getPrincipal();
        User userId = userService.queryUserByName(token);
        List<ScoreDetails> scoreDetails= scoreService.selectScoreByUserid(userId.getId());

        List<String> usererName = new LinkedList<>();
//        List<String> registerName = new LinkedList<>();

        for (ScoreDetails o:scoreDetails) {
//            registerName.add(userService.queryUserById(o.getRegistrarid()).getName());
            usererName.add(userService.queryUserById(o.getUserid()).getName());
        }
        model.addObject("scoredetails",scoreDetails);
        model.addObject("userername",usererName);
//        model.addObject("registername",registerName);
        return model;
    }

    @RequestMapping(value = "/plead", method = RequestMethod.POST)
    public Map<String, Object> PleadPost(@RequestBody Map<String, Object> param){
        Map<String, Object> map = new HashMap<>();
        String check = (String) param.get("plead");
        int id = Integer.parseInt((String) param.get("id"));
        ScoreDetails scoreDetails = scoreService.queryScoreDetailsById(id);
        String token = (String) SecurityUtils.getSubject().getPrincipal();
        User userId = userService.queryUserByName(token);
//        ScoreDetails scoreDetails = scoreService.queryScoreDetailsByUserId(userId.getId());

        if (check.equals("success")){
            scoreDetails.setPlead("1");
        }else {
            scoreDetails.setPlead("0");
        }
        if(scoreService.updateScoreDetailsById(scoreDetails)==1){
            map.put("code", 0);
            map.put("message", "提交成功");
        }else {
            map.put("code", -1);
            map.put("message", "提交失败");
        }
        return map;
    }

    @RequestMapping(value = "/historyscoredetails", method = RequestMethod.GET)
    public ModelAndView historyscoredetailsGet(){
        ModelAndView model = new ModelAndView("/scores/historyscoredetails");
        String token = (String) SecurityUtils.getSubject().getPrincipal();
        User userId = userService.queryUserByName(token);
        List<ScoreDetails> scoreDetails= scoreService.queryScoreDetailsByUserId(userId.getId());

        List<String> usererName = new LinkedList<>();
//        List<String> registerName = new LinkedList<>();

        for (ScoreDetails o:scoreDetails) {
//            registerName.add(userService.queryUserById(o.getRegistrarid()).getName());
            usererName.add(userService.queryUserById(o.getUserid()).getName());
        }
        model.addObject("scoredetails",scoreDetails);
        model.addObject("userername",usererName);
//        model.addObject("registername",registerName);
        return model;
    }
}
