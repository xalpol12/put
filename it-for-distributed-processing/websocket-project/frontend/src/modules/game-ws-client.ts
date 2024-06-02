import { drawFromFrame } from "./canvas.js";
import { StrokeFrame } from "./model/point-frame.js";
import { ChatMessagePayload, CustomMessage, DrawingPayload, GameDataPayload, GameTimerPayload, HandshakePayload, MessageType } from "./model/ws-message.js";
import { sendMessageAsString } from "./ws-service.js";

let ws: WebSocket;

export function initDrawConnection() {
    ws = new WebSocket('ws://localhost:8081/game');

    ws.onopen = (e: Event) => { console.log("Opened /game connection " + e.type); }
    ws.onmessage = (e: MessageEvent) => {
        processMessage(e);
    };
    ws.onerror = (e: Event) => { console.log(e.type) };
    ws.onclose = (e: CloseEvent) => {
        console.log(`Code: ${e.code}, reason: ${e.reason}`);
        ws.close();
    }

    function processMessage(e: MessageEvent) {
        const message: CustomMessage = JSON.parse(e.data);
        switch (message.messageType) {
            case MessageType.DRAWING:
                handleDrawing(message.payload as DrawingPayload);
                break;
            case MessageType.CHAT_MESSAGE:
                handleChatMessage(message.payload as ChatMessagePayload);
                break;
            case MessageType.GAME_DATA:
                handleGameData(message.payload as GameDataPayload);
                break;
            case MessageType.GAME_TIMER:
                handleGameTimer(message.payload as GameTimerPayload);
                break;
        }
    }

    function handleDrawing(e: DrawingPayload) {
        const strokeFrame: StrokeFrame = JSON.parse(e.drawingFrame);
        logFrame(strokeFrame);
        drawFromFrame(strokeFrame);
    }

    function handleChatMessage(e: ChatMessagePayload) {
        console.log(e);
    }

    function handleGameData(e: GameDataPayload) {
        console.log(e);
    }

    function handleGameTimer(e: GameTimerPayload) {
        console.log(e);
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

export function sendChatMessage() {

}
