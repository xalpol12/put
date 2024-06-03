export enum MessageType {
    HANDSHAKE = "HANDSHAKE",        // to server
    DRAWING = "DRAWING",            // bidirectional
    CHAT_MESSAGE = "CHAT_MESSAGE",  // bidirectional
    GAME_TIMER = "GAME_TIMER",      // from server
    NEW_WORD = "NEW_WORD",          // from server
    GAME_SCORE = "GAME_SCORE"       // from server
}

export interface HandshakePayload {
    userId: string;
    sessionId: string;
}

export interface DrawingPayload {
    drawingFrame: string
}

export interface ChatMessagePayload {
    sender: string;
    content: string;
}

export interface GameTimerPayload {
    time: number;
}

export interface NewWordPayload {
    newWord: string;
    newDrawer: string;
}

export interface GameScorePayload {
    userId: string;
    score: number;
}

export type Payload =
    | HandshakePayload
    | DrawingPayload
    | ChatMessagePayload
    | GameTimerPayload
    | NewWordPayload
    | GameScorePayload;

export interface CustomMessage {
    messageType: MessageType;
    payload: Payload;
}
