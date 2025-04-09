package com.home.job.main.repository;

import com.home.job.main.entity.Business;
import com.home.job.user.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BusinessRepository extends JpaRepository<Business, Long> {
//    특정 조건이 포함된 메서드는 JpaRepository가 기본으로 제공하지 않음
    List<Business> findAllByUserInfoId(int userId);
}
