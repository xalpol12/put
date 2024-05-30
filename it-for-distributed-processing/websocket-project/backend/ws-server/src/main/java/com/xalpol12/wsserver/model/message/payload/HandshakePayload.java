package com.xalpol12.wsserver.model.message.payload;

import lombok.Data;

@Data
public class HandshakePayload {
    private String clientId;
    private String sessionId;
}
