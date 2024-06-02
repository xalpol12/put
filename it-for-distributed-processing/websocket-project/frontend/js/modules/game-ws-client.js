import { drawFromFrame } from "./canvas.js";
import { MessageType } from "./model/ws-message.js";
import { sendMessageAsString } from "./ws-service.js";
let ws;
export function initDrawConnection() {
    ws = new WebSocket('ws://localhost:8081/game');
    ws.onopen = (e) => { console.log("Opened /game connection " + e.type); };
    ws.onmessage = (e) => {
        processMessage(e);
    };
    ws.onerror = (e) => { console.log(e.type); };
    ws.onclose = (e) => {
        console.log(`Code: ${e.code}, reason: ${e.reason}`);
        ws.close();
    };
    function processMessage(e) {
        const message = JSON.parse(e.data);
        switch (message.messageType) {
            case MessageType.DRAWING:
                handleDrawing(message.payload);
                break;
            case MessageType.CHAT_MESSAGE:
                handleChatMessage(message.payload);
                break;
            case MessageType.GAME_DATA:
                handleGameData(message.payload);
                break;
            case MessageType.GAME_TIMER:
                handleGameTimer(message.payload);
                break;
        }
    }
    function handleDrawing(e) {
        const strokeFrame = JSON.parse(e.drawingFrame);
        logFrame(strokeFrame);
        drawFromFrame(strokeFrame);
    }
    function handleChatMessage(e) {
        console.log(e);
    }
    function handleGameData(e) {
        console.log(e);
    }
    function handleGameTimer(e) {
        console.log(e);
    }
    function logFrame(strokeFrame) {
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
export function sendHandshake(userId, sessionId) {
    const handshake = {
        messageType: MessageType.HANDSHAKE,
        payload: {
            userId: userId,
            sessionId: sessionId,
        }
    };
    sendMessageAsString(ws, handshake);
    console.log(`Handshake with ${handshake.payload} sent to server`);
}
export function sendDrawing(strokeFrame) {
    const drawing = {
        messageType: MessageType.DRAWING,
        payload: {
            drawingFrame: JSON.stringify(strokeFrame),
        }
    };
    sendMessageAsString(ws, drawing);
    console.log(`Drawing with senderId: ${strokeFrame.senderId} sent to server`);
}
export function sendChatMessage() {
}
