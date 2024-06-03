package com.xalpol12.wsserver.model.message.payload;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GameScorePayload implements Payload {
    private String userId;
    private Integer score;
}
