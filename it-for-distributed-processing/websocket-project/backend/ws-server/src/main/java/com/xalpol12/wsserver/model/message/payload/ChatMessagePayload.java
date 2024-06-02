package com.xalpol12.wsserver.model.message.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessagePayload implements Payload {
    private String sender;
    private String content;
}
