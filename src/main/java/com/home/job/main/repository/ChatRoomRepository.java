package com.home.job.main.repository;

import com.home.job.main.entity.ChatRoom;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Integer> {
//    JpaRepository.count() 메서드 자체가 long을 반환
    long countByUserInfoIdAndRecruitBoardId(int loginId, int postId);

    @Query("select c.id from ChatRoom c where c.userInfoId = :userInfoId and c.recruitBoardId = :recruitBoardId")
    Optional<Integer> findIdByUserInfoIdAndRecruitBoardId(@Param("userInfoId") int userInfoId,
                                                          @Param("recruitBoardId") int recruitBoardId);
}
