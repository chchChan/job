package com.home.job.main.service;

import com.home.job.main.dto.ActualWorkDto;
import com.home.job.main.dto.BusinessDto;
import com.home.job.main.entity.ActualWork;
import com.home.job.main.entity.Business;
import com.home.job.main.projections.ActualWorkProjections;
import com.home.job.main.repository.ActualWorkRepository;
import com.home.job.main.repository.BusinessRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MainService {
    @Autowired
    private BusinessRepository businessRepository;

    @Autowired
    private ActualWorkRepository actualWorkRepository;

//    근무지 등록 (insert)
    public void businessCreate(BusinessDto businessDto) {
//        DTO → Entity 변환
        Business business = businessDto.toEntity();

        businessRepository.save(business);
    }

//    근무지 목록 가져오기
    public List<Business> getAllBusiness(int userId) {
        return businessRepository.findAllByUserInfoId(userId);
    }

//    근무시간 등록 (insert)
    public void actualWorkCreate(ActualWorkDto actualWorkDto) {
        ActualWork actualWork = actualWorkDto.toEntity();

        actualWorkRepository.save(actualWork);
    }

//    근무시간 가져오기
    public List<ActualWorkProjections> getAllActualWork(int userId) {
        return actualWorkRepository.findAllByUserInfoId(userId);
    }

}
