package com.home.job.webSocket.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.home.job.webSocket.dto.ChatMessageDto;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.*;

@Component
public class ChatWebSocketHandler extends TextWebSocketHandler {

    private final List<WebSocketSession> sessions = new ArrayList<>();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        sessions.add(session);
        System.out.println("연결 성공: " + session.getId());
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        // JSON 문자열을 ChatMessage 객체로 변환
        ChatMessageDto chatMessage = objectMapper.readValue(message.getPayload(), ChatMessageDto.class);
        System.out.println("수신 메시지: " + chatMessage.getMessage());

        // 받은 메시지를 같은 방에 있는 모든 세션으로 브로드캐스트
        for (WebSocketSession s : sessions) {
            if (s.isOpen()) {
                s.sendMessage(new TextMessage(objectMapper.writeValueAsString(chatMessage)));
            }
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        sessions.remove(session);
        System.out.println("연결 종료: " + session.getId());
    }
}
