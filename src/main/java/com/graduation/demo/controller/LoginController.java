package com.graduation.demo.controller;

import com.graduation.demo.common.entity.User;
import com.graduation.demo.common.shiro.TokenManager;
import com.graduation.demo.service.LoginService;
import com.graduation.demo.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Controller
public class LoginController {

    @Autowired
    private  LoginService loginService;

    @GetMapping("/")
    public ModelAndView index(){
        return new ModelAndView("redirect:/login");
    }

//    @GetMapping("/register")
//    public String register(){
//        return "samples/register";
//    }

    @GetMapping("/login")
    public String login_get(){
        return "samples/login";
    }

    @PostMapping("/login")
    @ResponseBody
    public ModelAndView loginVerify(String username, String password) {
        System.out.println("username = " + username);
        System.out.println("password = " + password);
        ModelAndView model = new ModelAndView();
        model.setViewName("/error/404");
        try{
            UsernamePasswordToken token = new UsernamePasswordToken(username,password);
            Subject subject = SecurityUtils.getSubject();
            subject.login(token);
        }catch (Exception e){
            Throwable ex = e.getCause();
            if (ex instanceof IncorrectCredentialsException){
                System.out.println("Password failed");
                return model;
            }else {
                System.out.println("\n\n"+e.getMessage()+"\n\n");
                System.out.println("login Failed");
                return model;
            }
        }
        model.setViewName("redirect:/user/index");  //重定向
        return model;
    }


    @GetMapping("/logout")
    @ResponseBody
    public ModelAndView logout(){
        Subject subject = SecurityUtils.getSubject();
        if (subject.isAuthenticated()){
            subject.logout();
        }
        return new ModelAndView("redirect:/login");
    }
}
