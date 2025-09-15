package com.home.job.webSocket.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.home.job.main.dto.ChatDetailDto;
import com.home.job.main.entity.ChatDetail;
import com.home.job.main.repository.ChatDetailRepository;
import com.home.job.webSocket.dto.ChatMessageDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class ListWebSocketHandler extends TextWebSocketHandler {

    private final ObjectMapper objectMapper = new ObjectMapper();

    // userId → 해당 사용자의 목록 페이지 세션들
    private final Map<Integer, Set<WebSocketSession>> userSessions = new ConcurrentHashMap<>();

//    @Autowired
//    private ChatDetailRepository chatDetailRepository;

//    유저 id
    private Integer getUserIdFromSession(WebSocketSession session) {
        String query = session.getUri().getQuery(); // id=123
        if (query == null || !query.contains("=")) return null;
        return Integer.valueOf(query.split("=")[1]);
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        Integer userId = getUserIdFromSession(session);
        if (userId == null) return;

        userSessions.putIfAbsent(userId, ConcurrentHashMap.newKeySet());
        userSessions.get(userId).add(session);

        System.out.println("목록 페이지 연결됨 ✅ userId=" + userId);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        Integer userId = getUserIdFromSession(session);
        if (userId == null) return;

        Set<WebSocketSession> sessions = userSessions.get(userId);
        if (sessions != null) {
            sessions.remove(session);
            if (sessions.isEmpty()) {
                userSessions.remove(userId);
            }
        }
        System.out.println("목록 페이지 연결 종료 ❌ userId=" + userId);
    }

//    목록 브로드캐스트
    public void notifyUser(Integer userId, String jsonMessage) throws IOException {
        Set<WebSocketSession> sessions = userSessions.get(userId);
        if (sessions != null) {
            for (WebSocketSession s : sessions) {
                if (s.isOpen()) {
                    s.sendMessage(new TextMessage(jsonMessage));
                }
            }
        }
    }
}
