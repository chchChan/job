package com.home.job.main.dto;

import com.home.job.main.entity.ChatDetail;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChatDetailDto {
    private int id;

    private int roomId;

    private String message;

    private int senderId;

    private String senderType;

    private String isReading;

    private LocalDateTime createdAt;

    // DTO → Entity
    public ChatDetail toEntity() {
        return ChatDetail.builder()
                .roomId(this.roomId)
                .message(this.message)
                .senderId(this.senderId)
                .senderType(this.senderType)
//                .isReading(this.isReading) // null 들어옴
                .createdAt(this.createdAt)
                .build();
    }

    // Entity -> DTO
    public static ChatDetailDto toDto(ChatDetail chatDetail) {
        return ChatDetailDto.builder()
                .id(chatDetail.getId())
                .roomId(chatDetail.getRoomId())
                .message(chatDetail.getMessage())
                .senderId(chatDetail.getSenderId())
                .senderType(chatDetail.getSenderType())
                .isReading(chatDetail.getIsReading())
                .createdAt(chatDetail.getCreatedAt())
                .build();
    }
}
