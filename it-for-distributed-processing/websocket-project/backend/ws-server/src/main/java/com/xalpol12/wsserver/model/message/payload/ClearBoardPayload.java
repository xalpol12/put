package com.xalpol12.wsserver.model.message.payload;

import lombok.Data;

@Data
public class ClearBoardPayload implements Payload {
    private String sessionId;

    public ClearBoardPayload(String sessionId) {
        this.sessionId = sessionId;
    }
}
