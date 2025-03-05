package com.home.job.main.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {

//    메인 페이지
    @RequestMapping("/")
    public String mainPage() {
        return "main/mainPage";
    }
}