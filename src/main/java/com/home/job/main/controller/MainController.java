package com.home.job.main.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {

//    메인 페이지 (로그인 화면)
    @RequestMapping("")
    public String loginPage() {
        return "user/loginPage";
    }

    @RequestMapping("mainPage")
    public String mainPage() {
        return "main/mainPage";
    }

    @RequestMapping("jobBoardPage")
    public String jobBoardPage() {
        return "main/jobBoardPage";
    }

//    로그인 화면 (회사 ver)
    @RequestMapping("companyLoginPage")
    public String companyLoginPage() {
        return "company/loginPage";
    }

}