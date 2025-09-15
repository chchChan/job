package com.home.job.webSocket.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.home.job.main.dto.ChatDetailDto;
import com.home.job.main.entity.ChatDetail;
import com.home.job.main.repository.ChatDetailRepository;
import com.home.job.webSocket.dto.ChatMessageDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class ChatWebSocketHandler extends TextWebSocketHandler {

//    private final List<WebSocketSession> sessions = new ArrayList<>();
    private final ObjectMapper objectMapper = new ObjectMapper();

    // 방 번호(Integer) → 방에 접속한 WebSocket 세션 집합
    private final Map<Integer, Set<WebSocketSession>> roomSessions = new ConcurrentHashMap<>();

    @Autowired
    private ChatDetailRepository chatDetailRepository;

    //    방번호
    private Integer getRoomIdFromSession(WebSocketSession session) {
        String query = session.getUri().getQuery(); // "id=1"
        if (query == null || !query.contains("=")) {
            return null; // 방번호 없으면 null 반환
        }
        return Integer.valueOf(query.split("=")[1]);
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        Integer roomId = getRoomIdFromSession(session); // URL, 파라미터 등에서 방번호 추출

        roomSessions.putIfAbsent(roomId, ConcurrentHashMap.newKeySet());
        roomSessions.get(roomId).add(session);

        System.out.println("연결 성공: " + session.getId() + " / 방: " + roomId);
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) {
        try {
            ChatMessageDto chatMessage = objectMapper.readValue(message.getPayload(), ChatMessageDto.class);
            System.out.println("받은 메시지: " + chatMessage);

            ChatDetailDto chatDetailDto = new ChatDetailDto();
            chatDetailDto.setRoomId(chatMessage.getRoomId());
            chatDetailDto.setSenderId(chatMessage.getSenderId());
            chatDetailDto.setSenderType(chatMessage.getSenderType());
            chatDetailDto.setMessage(chatMessage.getMessage());
//            chatDetailDto.setIsReading("N");

            ChatDetail chatDetail = chatDetailDto.toEntity();
            chatDetailRepository.save(chatDetail);

            chatDetailDto.setCreatedAt(chatDetail.getCreatedAt());

//          JSON 변환 시 LocalDateTime 직렬화
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new com.fasterxml.jackson.datatype.jsr310.JavaTimeModule());
            mapper.disable(com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATES_AS_TIMESTAMPS); // ISO 문자열로

            String json = mapper.writeValueAsString(chatDetailDto); // DTO → JSON 문자열
            session.sendMessage(new TextMessage(json));

            // 브로드캐스트
            broadcastToRoom(chatMessage.getRoomId(), json, session);

        } catch (Exception e) {
            e.printStackTrace(); // 여기 로그 꼭 확인
            try {
                session.sendMessage(new TextMessage("역직렬화 실패: " + e.getMessage()));
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }

//    @Override
//    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
//        sessions.remove(session);
//        System.out.println("연결 종료: " + session.getId());
//    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        Integer roomId = getRoomIdFromSession(session);
        Set<WebSocketSession> sessionsInRoom = roomSessions.get(roomId);
        if (sessionsInRoom != null) {
            sessionsInRoom.remove(session);
            if (sessionsInRoom.isEmpty()) {
                roomSessions.remove(roomId);
            }
        }
        System.out.println("연결 종료: " + session.getId() + " / 방: " + roomId);
    }

    // 브로드캐스트 : 한 명이 보낸 메시지를 같은 방(Room)에 있는 모든 사람에게 똑같이 보내는 것
    private void broadcastToRoom(int roomId, String message, WebSocketSession senderSession) throws IOException {
        Set<WebSocketSession> sessionsInRoom = roomSessions.get(roomId);
        if (sessionsInRoom != null) {
            for (WebSocketSession s : sessionsInRoom) {
                // 자기 세션도 보내면 클라이언트에 두 개 뜸.. 자기제외
                if (s.isOpen() && s != senderSession) {
                    s.sendMessage(new TextMessage(message));
                }
            }
        }
    }

}
