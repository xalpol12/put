export enum MessageType {
    HANDSHAKE = "HANDSHAKE", // to server
    DRAWING = "DRAWING", // bidirectional
    CHAT_MESSAGE = "CHAT_MESSAGE", // bidirectional
    GAME_DATA = "GAME_DATA", // from server
    GAME_TIMER = "GAME_TIMER", // from server
}

export interface HandshakePayload {
    userId: string;
    sessionId: string;
}

export interface DrawingPayload {
    drawingFrame: string
}

export interface ChatMessagePayload {

}

export interface GameDataPayload {

}

export interface GameTimerPayload {

}

export type Payload =
    | HandshakePayload
    | DrawingPayload
    | ChatMessagePayload
    | GameDataPayload
    | GameTimerPayload;

export interface CustomMessage {
    messageType: MessageType;
    payload: Payload;
}
