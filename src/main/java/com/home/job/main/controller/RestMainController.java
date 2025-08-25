package com.home.job.main.controller;

import com.home.job.company.dto.CompanyInfoDto;
import com.home.job.dto.RestResponseDto;
import com.home.job.main.dto.ActualWorkDto;
import com.home.job.main.dto.BusinessDto;
import com.home.job.main.repository.BusinessRepository;
import com.home.job.main.service.MainService;
import com.home.job.user.dto.UserInfoDto;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalTime;

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

//    회원의 근무지 목록
    @RequestMapping("getBusinessList")
    public RestResponseDto getBusinessList(@RequestParam("userId") int userId) {
        RestResponseDto restResponseDto = new RestResponseDto();
        restResponseDto.setResult("success");

        restResponseDto.add("businessList", mainService.getAllBusiness(userId));
//        /api/main/getBusinessList?userId=
        return restResponseDto;
    }

//    근무시간 등록
    @RequestMapping("createActualWork")
    public RestResponseDto createActualWork(ActualWorkDto actualWorkDto) {
        RestResponseDto restResponseDto = new RestResponseDto();
        restResponseDto.setResult("success");

        mainService.actualWorkCreate(actualWorkDto);

//        /api/main/createActualWork
        return restResponseDto;
    }

//    회원의 근무시간
    @RequestMapping("getActualWorkList")
    public RestResponseDto getActualWorkList(@RequestParam("userId") int userId) {
        RestResponseDto restResponseDto = new RestResponseDto();
        restResponseDto.setResult("success");

        restResponseDto.add("actualWorkList", mainService.getAllActualWork(userId));
//        /api/main/getActualWorkList?userId=
        return restResponseDto;
    }

//    근무시간 수정
    @RequestMapping("updateActualWork")
    public RestResponseDto updateActualWork(ActualWorkDto actualWorkDto) {
        RestResponseDto restResponseDto = new RestResponseDto();
        restResponseDto.setResult("success");

        mainService.actualWorkUpdate(actualWorkDto);
    //        /api/main/updateActualWork?id=
        return restResponseDto;
    }

//    근무시간 삭제
    @RequestMapping("deleteActualWork")
    public RestResponseDto deleteActualWork(@RequestParam("actualWorkId") int actualWorkId) {
        RestResponseDto restResponseDto = new RestResponseDto();
        restResponseDto.setResult("success");

        mainService.actualWorkDelete(actualWorkId);
    //        /api/main/deleteActualWork?id=
        return restResponseDto;
    }

//    주소 api
//    @RequestMapping("juso")
//    public @ResponseBody String juso(String roadFullAddr){
//        System.out.println("테스트 : "+ roadFullAddr);
//        return "ok";
//    }

//    유저 & 회사 로그인 확인
    @RequestMapping("getSessionId")
    public RestResponseDto getSessionId(HttpSession session) {
        RestResponseDto restResponseDto = new RestResponseDto();
        restResponseDto.setResult("success");

        UserInfoDto loginUser = (UserInfoDto) session.getAttribute("loginUser");
        CompanyInfoDto loginCompany = (CompanyInfoDto) session.getAttribute("loginCompany");

        if (loginCompany != null) {
            restResponseDto.add("id", loginCompany.getId());
            restResponseDto.add("role", "company");
        } else if (loginUser != null) {
            restResponseDto.add("id", loginUser.getId());
            restResponseDto.add("role", "user");
        } else {
            restResponseDto.add("id", null);
        }

    //        /api/company/getSessionId
        return restResponseDto;
    }

//    공고 리스트
    @RequestMapping("getRecruitList")
    public RestResponseDto getRecruitList() {
        RestResponseDto restResponseDto = new RestResponseDto();
        restResponseDto.setResult("success");

        restResponseDto.add("recruitList", mainService.getAllRecruitBoard());
        restResponseDto.add("recruitCount", mainService.getAllRecruitBoardCount());
//        /api/main/getRecruitList
        return restResponseDto;
    }

//    채팅방 검색
//    아래 채팅방 생성 함수에서 try - catch 로 처리해서 생략가능
//    @RequestMapping("checkRoom")
//    public RestResponseDto checkRoom(@RequestParam("loginId") int loginId, @RequestParam("postId") int postId) {
//        RestResponseDto restResponseDto = new RestResponseDto();
//        restResponseDto.setResult("success");
//
//        restResponseDto.add("count", mainService.countByCheckRoom(loginId, postId));
////        /api/main/checkRoom?loginId=${loginId}&postId=${postId}
//        return restResponseDto;
//    }

//    채팅방 생성
    @RequestMapping("createChatRoom")
    public RestResponseDto createChatRoom(@RequestParam("loginId") int loginId, @RequestParam("postId") int postId) {
        RestResponseDto restResponseDto = new RestResponseDto();
        restResponseDto.setResult("success");

        try {
            Integer roomId = mainService.chatRoomCreate(loginId, postId);
            restResponseDto.add("roomId", roomId);
        } catch (DataIntegrityViolationException e) {
            // 중복 제약조건 위반 → 기존 방 ID 조회해서 반환
            Integer existingRoomId = mainService.findRoomId(loginId, postId);
            restResponseDto.add("roomId", existingRoomId);
        }

//        /api/main/createChatRoom
        return restResponseDto;
    }

//    채팅방 목록 (유저)
    @RequestMapping("getChatRoomListByUser")
    public RestResponseDto getChatRoomListByUser(@RequestParam("userId") int userId) {
        RestResponseDto restResponseDto = new RestResponseDto();
        restResponseDto.setResult("success");

        restResponseDto.add("chatList", mainService.getChatRoomListByUserId(userId));

//      /api/main/getChatRoomListByUser?userId=
        return restResponseDto;
    }
}
