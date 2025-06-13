package com.home.job.company.repository;

import com.home.job.company.entity.RecruitBoard;
import com.home.job.company.projections.RecruitBoardSelectProjections;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RecruitBoardRepository extends JpaRepository<RecruitBoard, Long> {
    @Query("select rb.id as id, rb.companyInfoId as companyInfoId, ci.name as companyName, ci.postcode as companyPostcode, " +
            "ci.roadAddress as companyRoadAddress, ci.detailAddress as companyDetailAddress, rb.title as title, rb.hourlyRate as hourlyRate, " +
            "rb.period as period, rb.workday as workday, rb.startTime as startTime, rb.endTime as endTime, rb.boardText as boardText, rb.createdAt as createdAt " +
            "from RecruitBoard rb " +
            "join CompanyInfo ci on rb.companyInfoId = ci.id " +
            "order by rb.id desc")
    List<RecruitBoardSelectProjections> selectAllRecruitBoard();
}
