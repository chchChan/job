package com.home.job.main.repository;

import com.home.job.main.entity.Business;
import com.home.job.user.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BusinessRepository extends JpaRepository<Business, Long> {
}
