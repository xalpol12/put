package com.xalpol12.wsserver.config;

import com.xalpol12.wsserver.handler.DrawingSocketHandler;
import com.xalpol12.wsserver.handler.JoinSocketHandler;
import lombok.RequiredArgsConstructor;
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

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(joinSocketHandler, "/join").setAllowedOrigins("*");
        registry.addHandler(drawingSocketHandler, "/draw").setAllowedOrigins("*");
    }

}
