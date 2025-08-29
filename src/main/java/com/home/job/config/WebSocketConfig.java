package com.home.job.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket // WebSocket 기능 활성화.. 이거 있어야 핸들러 동작
@RequiredArgsConstructor // final 또는 @NonNull 붙은 필드만 대상으로 하는 생성자를 자동 생성
public class WebSocketConfig implements WebSocketConfigurer {

    private final WebSocketHandler webSocketHandler;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
//        chatRoom → 브라우저에서 new WebSocket("ws://localhost:8888/chatRoom")로 연결
        registry.addHandler(webSocketHandler, "/chatRoom").setAllowedOrigins("*");
//        setAllowedOrigins("*") 허용 도메인을 지정.. 현재 테스트이기때문에 *로 모든 도메인을 허용
    }
}