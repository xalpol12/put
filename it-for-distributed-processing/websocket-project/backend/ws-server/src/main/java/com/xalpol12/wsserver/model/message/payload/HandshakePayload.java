package com.xalpol12.wsserver.model.message.payload;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class HandshakePayload implements Payload {
    private String userId;
    private String sessionId;
}
