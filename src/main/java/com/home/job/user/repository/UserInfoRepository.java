package com.home.job.user.repository;

import com.home.job.user.entity.UserInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserInfoRepository extends JpaRepository<UserInfo, Long> {

//    userinfo 테이블에 account_id 개수 (아이디 중복체크)
        @Query("select count(u) from UserInfo u where u.accountId = :accountId")
        int countByAccountId(@Param("accountId") String accountId);
}
