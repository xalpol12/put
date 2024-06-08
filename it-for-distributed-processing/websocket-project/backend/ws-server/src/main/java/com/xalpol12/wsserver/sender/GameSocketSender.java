package com.xalpol12.wsserver.sender;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xalpol12.wsserver.protos.CustomMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class GameSocketSender {
    private static final ObjectMapper mapper = new ObjectMapper();

    public static BinaryMessage wrapCustomMessage(CustomMessage message) {
        return new BinaryMessage(message.toByteArray());
    }

    public void sendMessage(WebSocketSession session, TextMessage message) throws IOException {
        session.sendMessage(message);
    }

    public void sendMessages(WebSocketSession session, List<BinaryMessage> messages) throws IOException {
        for (BinaryMessage m : messages) {
            session.sendMessage(m);
        }
    }
}
