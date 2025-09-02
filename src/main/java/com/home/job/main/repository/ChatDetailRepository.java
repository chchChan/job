package com.home.job.main.repository;

import com.home.job.main.entity.Business;
import com.home.job.main.entity.ChatDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatDetailRepository extends JpaRepository<ChatDetail, Integer> {
    List<ChatDetail> findChatListByRoomId(int roomId);
}
