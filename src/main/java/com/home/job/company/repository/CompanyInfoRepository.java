package com.home.job.company.repository;

import com.home.job.company.entity.CompanyInfo;
import com.home.job.user.entity.UserInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CompanyInfoRepository extends JpaRepository<CompanyInfo, Long> {

//    아이디 중복검사
    @Query("select count(c) from CompanyInfo c where c.accountId = :accountId")
    int countByAccountId(@Param("accountId") String accountId);

//    로그인
    @Query("select c from CompanyInfo c where c.accountId = :accountId and c.accountPw = :accountPw")
    CompanyInfo findByAccountIdAndAccountPw(@Param("accountId") String accountId, @Param("accountPw") String accountPw);
}
