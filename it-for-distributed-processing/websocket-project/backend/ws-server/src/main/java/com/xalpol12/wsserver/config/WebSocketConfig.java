package com.xalpol12.wsserver.config;

import com.xalpol12.wsserver.handler.DrawingSocketHandler;
import com.xalpol12.wsserver.handler.JoinSocketHandler;
import com.xalpol12.wsserver.handler.RejoinSocketHandler;
import com.xalpol12.wsserver.service.SessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
@RequiredArgsConstructor
public class WebSocketConfig implements WebSocketConfigurer {

    private final DrawingSocketHandler drawingSocketHandler;
    private final JoinSocketHandler joinSocketHandler;
    private final RejoinSocketHandler rejoinSocketHandler;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(joinSocketHandler, "/join").setAllowedOrigins("*");
        registry.addHandler(rejoinSocketHandler, "/rejoin").setAllowedOrigins("*");
        registry.addHandler(drawingSocketHandler, "/draw").setAllowedOrigins("*");
    }

}
