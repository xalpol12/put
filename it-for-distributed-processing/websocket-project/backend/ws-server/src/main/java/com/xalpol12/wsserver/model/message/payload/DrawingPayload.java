package com.xalpol12.wsserver.model.message.payload;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DrawingPayload implements Payload {
    String drawingFrame;

    public DrawingPayload(String msg) {
        drawingFrame = msg;
    }
}
