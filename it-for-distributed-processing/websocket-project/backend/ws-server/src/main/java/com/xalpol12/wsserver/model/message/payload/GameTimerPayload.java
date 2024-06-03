package com.xalpol12.wsserver.model.message.payload;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GameTimerPayload implements Payload {
    private int time;

    public GameTimerPayload(int time) {
        this.time = time;
    }
}
