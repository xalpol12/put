package com.xalpol12.wsserver.model;

import lombok.Data;
import org.springframework.web.socket.TextMessage;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

@Data
public class GameSession {

    private final Map<String, UserData> usersData = new ConcurrentHashMap<>();
    private final List<TextMessage> drawnFrames = new CopyOnWriteArrayList<>();

    public void addUserToSession(String clientId) {
        usersData.put(clientId, new UserData());
    }

    public UserData getUserData(String clientId) {
        return usersData.get(clientId);
    }

    public void addToDrawnFrames(TextMessage message) {
        drawnFrames.add(message);
    }

    public boolean isDrawnFramesEmpty() {
        return drawnFrames.isEmpty();
    }
}
