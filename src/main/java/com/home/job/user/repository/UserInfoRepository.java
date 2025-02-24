package com.home.job.user.repository;

import com.home.job.user.dto.UserInfoDto;
import com.home.job.user.entity.UserInfo;
import com.home.job.user.projections.FindPwProjections;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserInfoRepository extends JpaRepository<UserInfo, Long> {
//        JPA는 엔티티 별칭 필수사용
//        아이디 중복 체크
        @Query("select count(u) from UserInfo u where u.accountId = :accountId")
        int countByAccountId(@Param("accountId") String accountId);

//        로그인
//        @Query("select u from UserInfo u where u.accountId = :accountId and u.accountPw = :accountPw")
//        Optional<UserInfo> userInfoByIdAndPw(@Param("accountId") String accountId, @Param("accountPw") String accountPw);
        @Query("select new com.home.job.user.dto.UserInfoDto(u.id, u.accountId, u.accountPw, u.name, u.age, u.phone, u.createdAt) " +
                "from UserInfo u " +
                "where u.accountId = :accountId and u.accountPw = :accountPw")
        UserInfoDto userInfoByIdAndPw(@Param("accountId") String accountId, @Param("accountPw") String accountPw);

//        아이디 찾기
        @Query("select u.accountId from UserInfo u " +
                "where u.name = :name and u.phone = :phone")
        String accountIdByNameAndPhone(@Param("name") String name, @Param("phone") String phone);

//        비밀번호 찾기 (new .. JPQL 생성자 표현식)
//        생성자 표현식 장점 : 타입 안정성, 명확한 매핑, 필요한 데이터만 조회
//        생성자 표현식 단점 : 유지보수 번거로움, 쿼리 하드코딩
//        @Query("select new com.home.job.user.dto.FindPasswordDto(ui.id, ui.accountId, fpq.question) " +
        @Query("select ui.id as id, ui.accountId as accountId, fpq.question as question " +
                "from UserInfo ui " +
                "join FindPwAnswer fpa on ui.id = fpa.userInfoId " +
                "join FindPwQuestion fpq on fpa.findPwQuestionId = fpq.id " +
                "where ui.accountId = :accountId")
        Optional<FindPwProjections> passwordFindByAccountId(@Param("accountId") String accountId);

}
