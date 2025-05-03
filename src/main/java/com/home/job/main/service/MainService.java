package com.home.job.main.service;

import com.home.job.main.dto.ActualWorkDto;
import com.home.job.main.dto.BusinessDto;
import com.home.job.main.entity.ActualWork;
import com.home.job.main.entity.Business;
import com.home.job.main.projections.ActualWorkProjections;
import com.home.job.main.repository.ActualWorkRepository;
import com.home.job.main.repository.BusinessRepository;
import jakarta.transaction.Transactional;
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

//    근무시간 수정 (update)
    @Transactional // 트랜잭션 범위를 정해주는 어노테이션 update, delete 는 필요
    public void actualWorkUpdate(ActualWorkDto actualWorkDto) {
        ActualWork actualWork = actualWorkRepository.findById(actualWorkDto.getId())
                .orElseThrow(() -> new RuntimeException("근무시간 정보가 없습니다."));
//     findById()는 객체가 아닌 Optional<T>를 리턴하므로 .orElseThrow / .orELse 예외처리 필요

        actualWork.setStartTime(actualWorkDto.getStartTime());
        actualWork.setEndTime(actualWorkDto.getEndTime());
    }

//    근무시간 삭제 (delete)
    @Transactional
    public void actualWorkDelete(int actualWorkId) {
        ActualWork actualWork = actualWorkRepository.findById(actualWorkId)
                .orElseThrow(() -> new RuntimeException("근무시간 정보가 없습니다."));

        actualWorkRepository.delete(actualWork);
    }

}
