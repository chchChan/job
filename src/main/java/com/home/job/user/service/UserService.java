package com.home.job.user.service;

import com.home.job.user.dto.FindPasswordDto;
import com.home.job.user.dto.UserInfoDto;
import com.home.job.user.entity.FindPwAnswer;
import com.home.job.user.entity.FindPwQuestion;
import com.home.job.user.entity.UserInfo;
import com.home.job.user.projections.FindPwProjections;
import com.home.job.user.repository.FindPwAnswerRepository;
import com.home.job.user.repository.FindPwQuestionRepository;
import com.home.job.user.repository.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService {
    @Autowired
    private FindPwQuestionRepository findPwQuestionRepository;

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Autowired
    private FindPwAnswerRepository findPwAnswerRepository;

//    비밀번호 찾기 질문 select
    public List<FindPwQuestion> getAllQuestions() {
        return findPwQuestionRepository.findAll(); // Spring Data JPA .. findAll : 모든 데이터를 조회
    }

//    회원가입 insert
    public void accountCreate(UserInfoDto userInfoDto, int pwQuestion, String pwAnswer) {
//        DTO → Entity 변환
        UserInfo userInfo = userInfoDto.toEntity();

        userInfoRepository.save(userInfo); // save : insert, update

//        System.out.println("아이디...가져오나?" + userInfo.getId());
        FindPwAnswer answer = new FindPwAnswer();
        answer.setUserInfoId(userInfo.getId());
        answer.setFindPwQuestionId(pwQuestion);
        answer.setAnswer(pwAnswer);

        findPwAnswerRepository.save(answer);
    }

//    아이디 중복체크
    public int countByAccountId(String accountId) {
        return userInfoRepository.countByAccountId(accountId);
    }

//    로그인
    public UserInfoDto findByIdAndPw(UserInfoDto params) {
        UserInfo userInfo = userInfoRepository.userInfoByIdAndPw(params.getAccountId(), params.getAccountPw());
        return UserInfoDto.fromEntity(userInfo);
    }

//    아이디 찾기
    public String findByNameAndPhone(UserInfoDto params) {
        return userInfoRepository.accountIdByNameAndPhone(params.getName(), params.getPhone());
    }

//    비밀번호 찾기
    public Optional<FindPwProjections> findPasswordByAccountId(String accountId) {
        return userInfoRepository.passwordFindByAccountId(accountId);
    }
}
