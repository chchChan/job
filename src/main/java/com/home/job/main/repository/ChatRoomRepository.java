package com.home.job.main.repository;

import com.home.job.main.entity.ChatRoom;
import com.home.job.main.projections.ChatRoomProjections;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Integer> {
//    JpaRepository.count() 메서드 자체가 long을 반환
    long countByUserInfoIdAndRecruitBoardId(int loginId, int postId);

    @Query("select c.id from ChatRoom c where c.userInfoId = :userInfoId and c.recruitBoardId = :recruitBoardId")
    Optional<Integer> findIdByUserInfoIdAndRecruitBoardId(@Param("userInfoId") int userInfoId,
                                                          @Param("recruitBoardId") int recruitBoardId);

    @Query("select cr.id as roomId, cr.recruitBoardId as recruitBoardId," +
            "       rb.companyInfoId as companyInfoId, rb.title as title," +
            "       ci.name as name from ChatRoom cr " +
            "join RecruitBoard rb on cr.recruitBoardId = rb.id " +
            "join CompanyInfo ci on rb.companyInfoId = ci.id " +
            "where cr.id = :roomId")
    ChatRoomProjections findChatRoomByRoomId(@Param("userId") int roomId);
}
