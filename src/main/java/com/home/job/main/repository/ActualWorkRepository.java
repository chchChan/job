package com.home.job.main.repository;

import com.home.job.main.entity.ActualWork;
import com.home.job.main.projections.ActualWorkProjections;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ActualWorkRepository extends JpaRepository<ActualWork, Long> {
//    List<ActualWork> findAllByUserInfoId(int userId);

    @Query("select aw.id as id, aw.userInfoId as userInfoId, aw.businessId as businessId, aw.workDay as workDay, " +
            "aw.startTime as startTime, aw.endTime as endTime, bu.businessName as businessName, bu.hourlyRate as hourlyRate " +
            "from ActualWork aw " +
            "join Business bu on aw.businessId = bu.id " +
            "where aw.userInfoId = :userId")
    List<ActualWorkProjections> findAllByUserInfoId(@Param("userId")int userId);
}
