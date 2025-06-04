package com.home.job.company.controller;

import com.home.job.company.dto.CompanyInfoDto;
import com.home.job.company.service.CompanyService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/company")
public class CompanyController {

    @Autowired
    private CompanyService companyService;

//    로그인
    @RequestMapping("loginPage")
    public String loginPage() {
        return "company/loginPage";
    }

//    회원가입
    @RequestMapping("joinPage")
    public String joinPage() {
        return "company/joinPage";
    }

//    회원가입 성공
    @RequestMapping("joinSuccessPage")
    public String joinSuccessPage() {
        return "company/joinSuccessPage";
    }

//    로그인 프로세스
    @RequestMapping("loginCompanyProcess")
    public String loginCompanyProcess(CompanyInfoDto params, HttpSession session) {
        CompanyInfoDto companyInfoDto = companyService.getUserInfoByAccountIdAndPw(params.getAccountId(), params.getAccountPw());
        session.setAttribute("loginCompany", companyInfoDto);

        return "redirect:/jobBoardPage";
    }

//    로그아웃 프로세스
    @RequestMapping("logoutCompanyProcess")
    public String logoutCompanyProcess(HttpSession session) {
        session.removeAttribute("loginCompany");

        return "redirect:/company/loginPage";
    }

//    공고 등록 페이지
    @RequestMapping("insertRecruitPage")
    public String insertRecruitPage() {
        return "company/insertRecruitPage";
    }

}
