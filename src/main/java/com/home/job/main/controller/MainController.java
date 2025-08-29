package com.home.job.main.controller;

import com.home.job.company.service.CompanyService;
import com.home.job.main.service.MainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MainController {

    @Autowired
    private CompanyService companyService;

    @Autowired
    private MainService mainService;

    //    메인 페이지 (로그인 화면)
    @RequestMapping("")
    public String loginPage() {
        return "user/loginPage";
    }

//    메인 홈
    @RequestMapping("mainPage")
    public String mainPage() {
        return "main/mainPage";
    }

//    공고 목록
    @RequestMapping("jobBoardPage")
    public String jobBoardPage() {
        return "main/jobBoardPage";
    }

//    공고 상세
    @RequestMapping("recruitDetailPage")
    public String recruitDetailPage(@RequestParam("id") int id, Model model) {
        model.addAttribute("recruit", companyService.recruitDetailSelect(id));
        return "main/recruitDetailPage";
    }

//    채팅 상세
    @RequestMapping("chatDetailPage")
    public String chatDetailPage(@RequestParam("id") int id, Model model) {
        model.addAttribute("chatRoomInfo", mainService.findChatRoomById(id));
        return "main/chatDetailPage";
    }

//    채팅방 목록 (user)
    @RequestMapping("chatRoomPage")
    public String chatRoomPage() {
        return "main/chatRoomPage";
    }

}