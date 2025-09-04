package com.home.job.main.projections;

import java.time.LocalDateTime;

public interface UserChatRoomListProjections {
    int getRoomId();
    int getCompanyId();
    String getTitle();
    String getCompanyProfile();
    String getMessage();
    int getUnreadCount();
    LocalDateTime getCreatedAt();
}
