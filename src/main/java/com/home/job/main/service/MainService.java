package com.home.job.main.service;

import com.home.job.main.dto.BusinessDto;
import com.home.job.main.entity.Business;
import com.home.job.main.repository.BusinessRepository;
import com.home.job.user.dto.UserInfoDto;
import com.home.job.user.entity.FindPwAnswer;
import com.home.job.user.entity.UserInfo;
import com.home.job.user.repository.FindPwQuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MainService {
    @Autowired
    private BusinessRepository businessRepository;

//    근무지 등록 (insert)
    public void businessCreate(BusinessDto businessDto) {
//        DTO → Entity 변환
        Business business = businessDto.toEntity();

        businessRepository.save(business);
    }

}
