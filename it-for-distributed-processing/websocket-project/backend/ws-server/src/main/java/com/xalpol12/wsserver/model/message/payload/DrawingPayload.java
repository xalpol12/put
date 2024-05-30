package com.xalpol12.wsserver.model.message.payload;

import lombok.Data;
import org.springframework.web.socket.TextMessage;

@Data
public class DrawingPayload {
    TextMessage drawingFrame;
}
