import { clearCanvas, drawFromFrame } from "./canvas.js";
import { addMessage } from "./chat.js";
import { StrokeFrame } from "./model/point-frame.js";
import { ChatMessagePayload, CustomMessage, DrawingPayload, NewWordPayload, GameTimerPayload, HandshakePayload, MessageType, GameScorePayload } from "./model/ws-message.js";
import { addScoreEntry } from "./score.js";
import { updateTimer, updateWord } from "./timer.js";
import { sendMessageAsString } from "./ws-service.js";

let ws: WebSocket;

export function initWebsocket() {
    return new Promise<void>((resolve, reject) => {
        ws = new WebSocket('ws://localhost:8081/game');

        ws.onopen = (e: Event) => {
            console.log("Opened /game connection " + e.type);
            resolve();
        }
        ws.onmessage = (e: MessageEvent) => {
            processMessage(e);
        };
        ws.onerror = (e: Event) => {
            console.log(e.type);
            reject();
        };
        ws.onclose = (e: CloseEvent) => {
            console.log(`Code: ${e.code}, reason: ${e.reason}`);
            ws.close();
        }
    });

    function processMessage(e: MessageEvent) {
        console.log(e.data);
        const message: CustomMessage = JSON.parse(e.data);
        switch (message.messageType) {
            case MessageType.DRAWING:
                handleDrawing(message.payload as DrawingPayload);
                break;
            case MessageType.CHAT_MESSAGE:
                handleChatMessage(message.payload as ChatMessagePayload);
                break;
            case MessageType.GAME_TIMER:
                handleGameTimer(message.payload as GameTimerPayload);
                break;
            case MessageType.NEW_WORD:
                handleNewWord(message.payload as NewWordPayload);
                break;
            case MessageType.GAME_SCORE:
                handleGameScore(message.payload as GameScorePayload);
                break;
            case MessageType.CLEAR_BOARD:
                handleClearBoard();
                break;
        }
    }

    function handleDrawing(e: DrawingPayload) {
        const strokeFrame: StrokeFrame = JSON.parse(e.drawingFrame);
        logFrame(strokeFrame);
        drawFromFrame(strokeFrame);
    }

    function handleChatMessage(e: ChatMessagePayload) {
        addMessage(e);
    }

    function handleGameTimer(e: GameTimerPayload) {
        updateTimer(e);
    }

    function handleNewWord(e: NewWordPayload) {
        updateWord(e);
        console.log(`New word: ${e.newWord}; drawer: ${e.newDrawer}`);
    }

    function handleGameScore(e: GameScorePayload) {
        addScoreEntry(e);
        console.log(`Game score ${e.userId}:${e.score}`);
    }

    function handleClearBoard() {
        clearCanvas();
        console.log(`Clear board message received`);
    }

    function logFrame(strokeFrame: StrokeFrame) {
        console.log(`Sender ID: ${strokeFrame.senderId}`);
        console.log(`Line Width: ${strokeFrame.lineWidth}`);
        console.log(`Line Cap: ${strokeFrame.lineCap}`);
        console.log(`Stroke Style: ${strokeFrame.strokeStyle}`);
        strokeFrame.points.forEach((pointFrame) => {
            console.log(`Frame ID: ${pointFrame.frameId}`);
            console.log(`To: (${pointFrame.to.x}, ${pointFrame.to.y})`);
        });
    }
}

export function sendHandshake(userId: string, sessionId: string) {
    const handshake: CustomMessage = {
        messageType: MessageType.HANDSHAKE,
        payload: {
            userId: userId,
            sessionId: sessionId,
        } as HandshakePayload
    };
    sendMessageAsString(ws, handshake);
    console.log(`Handshake with ${handshake.payload} sent to server`);
}

export function sendDrawing(strokeFrame: StrokeFrame) {
    const drawing: CustomMessage = {
        messageType: MessageType.DRAWING,
        payload: {
            drawingFrame: JSON.stringify(strokeFrame),
        } as DrawingPayload
    };
    sendMessageAsString(ws, drawing);
    console.log(`Drawing with senderId: ${strokeFrame.senderId} sent to server`);
}

export function sendChatMessage(m: ChatMessagePayload) {
    const message: CustomMessage = {
        messageType: MessageType.CHAT_MESSAGE,
        payload: m,
    };
    sendMessageAsString(ws, message);
    console.log(`Chat message with content ${m.content} sent to server`);

}
