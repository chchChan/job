package com.home.job.main.repository;

import com.home.job.main.entity.ChatDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ChatDetailRepository extends JpaRepository<ChatDetail, Integer> {
    List<ChatDetail> findChatListByRoomId(int roomId);

    @Modifying(clearAutomatically = true)
//    데이터 변경 쿼리를 쓰려면 @Modifying을 붙여야 실행
    @Query("update ChatDetail set isReading = 'Y' " +
            "where roomId = :roomId " +
            "and isReading = 'N' ")
    void updateIsReadingByRoomId(int roomId);
}
