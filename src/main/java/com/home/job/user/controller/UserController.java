package com.home.job.user.controller;

import com.home.job.user.dto.FindPwQuestion;
import com.home.job.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    //    로그인 페이지
    @RequestMapping("login")
    public String loginPage() {
        return "user/loginPage";
    }

//    회원가입 페이지
    @RequestMapping("join")
    public String joinPage(Model model) {
        List<FindPwQuestion> pwQuestions = userService.getAllQuestions();
//        System.out.println("잘 나오나요? : " + pwQuestions);
        model.addAttribute("pwQuestions", pwQuestions);
        return "user/joinPage";
    }

//    비밀번호 찾기 페이지
    @RequestMapping("findPassword")
    public String findPasswordPage() {
        return "user/findPasswordPage";
    }

//    아이디 찾기 페이지
    @RequestMapping("findAccount")
    public String findAccountPage() {
        return "user/findAccountPage";
    }
}
