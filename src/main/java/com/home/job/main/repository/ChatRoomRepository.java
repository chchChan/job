package com.home.job.main.repository;

import com.home.job.main.entity.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Integer> {
//    JpaRepository.count() 메서드 자체가 long을 반환
    long countByUserInfoIdAndRecruitBoardId(int loginId, int postId);
}
