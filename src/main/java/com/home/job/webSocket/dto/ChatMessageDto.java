package com.home.job.webSocket.dto;

import lombok.Data;

@Data
public class ChatMessageDto {
//    private String type;
    private int roomId;
    private String senderType; // user / company
    private int senderId;
    private String message;
}
