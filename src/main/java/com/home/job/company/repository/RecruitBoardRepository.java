package com.home.job.company.repository;

import com.home.job.company.entity.CompanyInfo;
import com.home.job.company.entity.RecruitBoard;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RecruitBoardRepository extends JpaRepository<RecruitBoard, Long> {

}
