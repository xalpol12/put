package com.xalpol12.wsserver.model.message.payload;

import lombok.Data;

@Data
public class HandshakePayload implements Payload {
    private String userId;
    private String sessionId;
}
