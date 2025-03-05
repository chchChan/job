package com.home.job.user.controller;

import com.home.job.user.dto.UserInfoDto;
import com.home.job.user.entity.FindPwQuestion;
import com.home.job.user.entity.UserInfo;
import com.home.job.user.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

//    회원가입 성공 페이지
    @RequestMapping("joinSuccess")
    public String joinSuccessPage() {
        return "user/joinSuccessPage";
    }

//    로그인 프로세스
    @RequestMapping("loginUserProcess")
    public String loginUserProcess(UserInfoDto params, HttpSession session) {
//        UserInfoDto userInfoDto = userService.findByIdAndPw(params);
        UserInfoDto userInfoDto = userService.getUserInfoByAccountIdAndPw(params.getAccountId(), params.getAccountPw());
        session.setAttribute("loginUser", userInfoDto);

        return "redirect:/";
    }
}
