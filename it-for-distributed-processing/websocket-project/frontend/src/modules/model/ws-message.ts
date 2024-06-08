export enum MessageType {
    HANDSHAKE = 0,        // to server
    DRAWING = 1,            // bidirectional
    CHAT_MESSAGE = 2,  // bidirectional
    GAME_TIMER = 3,      // from server
    NEW_WORD = 4,          // from server
    GAME_SCORE = 5,      // from server
    CLEAR_BOARD = 6
}

export namespace MessageType {
    const stringToEnumMap: { [key: string]: MessageType } = {
        "HANDSHAKE": MessageType.HANDSHAKE,
        "DRAWING": MessageType.DRAWING,
        "CHAT_MESSAGE": MessageType.CHAT_MESSAGE,
        "GAME_TIMER": MessageType.GAME_TIMER,
        "NEW_WORD": MessageType.NEW_WORD,
        "GAME_SCORE": MessageType.GAME_SCORE,
        "CLEAR_BOARD": MessageType.CLEAR_BOARD
    };

    export function fromString(key: MessageType): MessageType | undefined {
        return stringToEnumMap[key];
    }
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

export interface ClearBoardPayload {
    sessionId: string;
}

export type Payload =
    | HandshakePayload
    | DrawingPayload
    | ChatMessagePayload
    | GameTimerPayload
    | NewWordPayload
    | GameScorePayload
    | ClearBoardPayload;


export interface CustomMessage {
    messageType: MessageType;
    payload: Payload;
}
