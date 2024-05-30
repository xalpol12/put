package com.xalpol12.wsserver.sender;

import com.xalpol12.wsserver.model.message.CustomMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class GameSocketSender {

    public TextMessage wrapCustomMessage(CustomMessage message) {
        return new TextMessage(message.toString());
    }

    public void sendMessage(WebSocketSession session, TextMessage message) throws IOException {
        session.sendMessage(message);
    }

    public void sendMessages(WebSocketSession session, List<TextMessage> messages) throws IOException {
        for (TextMessage m : messages) {
            session.sendMessage(m);
        }
    }
}
