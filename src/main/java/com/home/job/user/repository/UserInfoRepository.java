package com.home.job.user.repository;

import com.home.job.user.dto.UserInfoDto;
import com.home.job.user.entity.UserInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserInfoRepository extends JpaRepository<UserInfo, Long> {
//        JPA는 엔티티 별칭 필수사용
//        아이디 중복 체크
        @Query("select count(u) from UserInfo u where u.accountId = :accountId")
        int countByAccountId(@Param("accountId") String accountId);

//        로그인
        @Query("select u from UserInfo u where u.accountId = :accountId and u.accountPw = :accountPw")
//        JPQL 생성자 표현식..
//        @Query("select new com.home.job.user.dto.UserInfoDto(u.id, u.accountId, u.accountPw, u.name, u.age, u.phone, u.createdAt) " +
//                "from UserInfo u " +
//                "where u.accountId = :accountId and u.accountPw = :accountPw")
        UserInfo userInfoByIdAndPw(@Param("accountId") String accountId, @Param("accountPw") String accountPw);

//        아이디 찾기
        @Query("select u.accountId from UserInfo u " +
                "where u.name = :name and u.phone = :phone")
        String accountIdByNameAndPhone(@Param("name") String name, @Param("phone") String phone);
}
