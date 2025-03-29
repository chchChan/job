package com.home.job.main.controller;

import com.home.job.dto.RestResponseDto;
import com.home.job.main.dto.BusinessDto;
import com.home.job.main.repository.BusinessRepository;
import com.home.job.main.service.MainService;
import com.home.job.user.dto.UserInfoDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/main")
public class RestMainController {
    @Autowired
    private MainService mainService;

//    근무지 등록
    @RequestMapping("createBusiness")
    public RestResponseDto createBusiness(BusinessDto businessDto) {
        RestResponseDto restResponseDto = new RestResponseDto();
        restResponseDto.setResult("success");

        mainService.businessCreate(businessDto);

//        /api/main/createBusiness
        return restResponseDto;
    }
}
