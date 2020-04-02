package com.graduation.demo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping(value = "/login")
public class LoginController {

    @RequestMapping(value = "")
    public ModelAndView login() {
        return new ModelAndView("login");
    }
}
