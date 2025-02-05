package com.home.job.user.repository;

import com.home.job.user.dto.UserInfoDto;
import com.home.job.user.entity.UserInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserInfoRepository extends JpaRepository<UserInfo, Long> {
//        JPA는 엔티티 별칭 필수사용
//        userinfo 테이블에 account_id 개수 (아이디 중복체크)
        @Query("select count(u) from UserInfo u where u.accountId = :accountId")
        int countByAccountId(@Param("accountId") String accountId);

//        @Query("select u from UserInfo u where u.accountId = :accountId and u.accountPw = :accountPw")
//        JPQL 생성자 표현식.. 쓰는 이유 : Entity를 Dto로 반환하지 못해서.. 이렇게 하거나 service에서 하나하나 넣어줘야함
        @Query("select new com.home.job.user.dto.UserInfoDto(u.id, u.accountId, u.accountPw, u.name, u.age, u.phone, u.createdAt) " +
                "from UserInfo u " +
                "where u.accountId = :accountId and u.accountPw = :accountPw")
        UserInfoDto userInfoByIdAndPw(@Param("accountId") String accountId, @Param("accountPw") String accountPw);
}
