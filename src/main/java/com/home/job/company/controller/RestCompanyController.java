package com.home.job.company.controller;

import com.home.job.company.dto.CompanyInfoDto;
import com.home.job.company.dto.RecruitBoardDto;
import com.home.job.company.service.CompanyService;
import com.home.job.dto.RestResponseDto;
import com.home.job.user.dto.UserInfoDto;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/company")
public class RestCompanyController {

    @Autowired
    private CompanyService companyService;

//    회원가입
    @RequestMapping("createCompanyInfo")
    public RestResponseDto createCompanyInfo(CompanyInfoDto companyInfoDto) {
        RestResponseDto restResponseDto = new RestResponseDto();
        restResponseDto.setResult("success");

        companyService.accountCreate(companyInfoDto);

//        /api/company/createCompanyInfo
        return restResponseDto;
    }

//    아이디 중복 체크
    @RequestMapping("getAccountId")
    public RestResponseDto getAccountId(@RequestParam("accountId") String accountId) {
        RestResponseDto restResponseDto = new RestResponseDto();
        restResponseDto.setResult("success");

        restResponseDto.add("countByAccountId", companyService.countByAccountId(accountId));

//        /api/company/getAccountId?accountId=
        return restResponseDto;
    }

//    로그인
    @RequestMapping("getCompanyInfo")
    public RestResponseDto getCompanyInfo(CompanyInfoDto params) {
        RestResponseDto restResponseDto = new RestResponseDto();
        restResponseDto.setResult("success");

        restResponseDto.add("loginCompanyInfo", companyService.getUserInfoByAccountIdAndPw(params.getAccountId(), params.getAccountPw()));
//        /api/company/getCompanyInfo?accountId=&accountPw=
        return restResponseDto;
    }


//    로그인 확인
    @RequestMapping("getSessionId")
    public RestResponseDto getSessionId(HttpSession session) {
        RestResponseDto restResponseDto = new RestResponseDto();
        restResponseDto.setResult("success");

        CompanyInfoDto loginCompany = (CompanyInfoDto) session.getAttribute("loginCompany");
        if (loginCompany != null) {
            restResponseDto.add("id", loginCompany.getId());
        } else {
            restResponseDto.add("id", null);
        }

//        /api/company/getSessionId
        return restResponseDto;
    }

//    공고 등록
    @RequestMapping("createRecruit")
    public RestResponseDto createRecruit(RecruitBoardDto params) {
        RestResponseDto restResponseDto = new RestResponseDto();
        restResponseDto.setResult("success");

        companyService.recruitCreate(params);
//        //api/company/createRecruit
        return restResponseDto;
    }

}
