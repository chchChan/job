package com.home.job.webSocket.dto;

import lombok.Data;

@Data
public class ChatMessageDto {
    private String roomId;
    private String senderType; // user / company
    private String senderId;
    private String message;
}
