package com.xalpol12.wsserver.service;

import com.xalpol12.wsserver.model.UserData;
import lombok.Getter;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

@Getter
@Service
public class GameSessionService {

    private final Map<String, UserData> usersData = new ConcurrentHashMap<>(); // do not track /join and /draw sessions as the

    private final List<TextMessage> drawnFrames = new CopyOnWriteArrayList<>();

    public void addUser(String clientId) {
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
