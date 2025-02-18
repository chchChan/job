package com.home.job.user.controller;

import com.home.job.dto.RestResponseDto;
import com.home.job.user.dto.UserInfoDto;
import com.home.job.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/user")
public class RestUserController {

    @Autowired
    private UserService userService;

//    회원가입
    @RequestMapping("createUserInfo")
    public RestResponseDto createUserInfo(UserInfoDto userInfoDto, @RequestParam("question") int pwQuestion, @RequestParam("answer") String pwAnswer) {
        RestResponseDto restResponseDto = new RestResponseDto();
        restResponseDto.setResult("success");

//        System.out.println("유저 데이터 : " + userInfoDto);
//        System.out.println("비밀번호 찾기 질문 : " + question + answer);
        userService.accountCreate(userInfoDto, pwQuestion, pwAnswer);

//        /api/user/createUserInfo
        return restResponseDto;
    }

//    아이디 중복 체크
    @RequestMapping("getAccountId")
    public RestResponseDto getAccountId(@RequestParam("accountId") String accountId) {
        RestResponseDto restResponseDto = new RestResponseDto();
        restResponseDto.setResult("success");

        restResponseDto.add("countByAccountId", userService.countByAccountId(accountId));

//        /api/user/getAccountId?accountId=
        return restResponseDto;
    }

//    로그인
    @RequestMapping("getUserInfo")
    public RestResponseDto getUserInfo(UserInfoDto params) {
        RestResponseDto restResponseDto = new RestResponseDto();
        restResponseDto.setResult("success");

        restResponseDto.add("loginUserInfo", userService.findByIdAndPw(params));

//        /api/user/getUserInfo?accountId=&accountPw=
        return restResponseDto;
    }

//    아이디 찾기
    @RequestMapping("getFindAccountId")
    public RestResponseDto getAccountId(UserInfoDto params) {
        RestResponseDto restResponseDto = new RestResponseDto();
        restResponseDto.setResult("success");

        restResponseDto.add("findAccountId", userService.findByNameAndPhone(params));

//        /api/user/getFindAccountId?name=&phone=
        return restResponseDto;
    }

//    비밀번호 찾기
    @RequestMapping("getFindAccountPw")
    public RestResponseDto getAccountPw(@RequestParam("accountId") String accountId) {
        RestResponseDto restResponseDto = new RestResponseDto();
        restResponseDto.setResult("success");

        restResponseDto.add("findAccountPw", userService.findPasswordByAccountId(accountId));

//        /api/user/getFindAccountId?name=&phone=
        return restResponseDto;
    }

}
