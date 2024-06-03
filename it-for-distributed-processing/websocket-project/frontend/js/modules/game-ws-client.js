import { drawFromFrame } from "./canvas.js";
import { addMessage } from "./chat.js";
import { MessageType } from "./model/ws-message.js";
import { sendMessageAsString } from "./ws-service.js";
let ws;
export function initWebsocket() {
    return new Promise((resolve, reject) => {
        ws = new WebSocket('ws://localhost:8081/game');
        ws.onopen = (e) => {
            console.log("Opened /game connection " + e.type);
            resolve();
        };
        ws.onmessage = (e) => {
            processMessage(e);
        };
        ws.onerror = (e) => {
            console.log(e.type);
            reject();
        };
        ws.onclose = (e) => {
            console.log(`Code: ${e.code}, reason: ${e.reason}`);
            ws.close();
        };
    });
    function processMessage(e) {
        console.log(e.data);
        const message = JSON.parse(e.data);
        switch (message.messageType) {
            case MessageType.DRAWING:
                handleDrawing(message.payload);
                break;
            case MessageType.CHAT_MESSAGE:
                handleChatMessage(message.payload);
                break;
            case MessageType.GAME_TIMER:
                handleGameTimer(message.payload);
                break;
            case MessageType.NEW_WORD:
                handleNewWord(message.payload);
                break;
            case MessageType.GAME_SCORE:
                handleGameScore(message.payload);
                break;
        }
    }
    function handleDrawing(e) {
        const strokeFrame = JSON.parse(e.drawingFrame);
        logFrame(strokeFrame);
        drawFromFrame(strokeFrame);
    }
    function handleChatMessage(e) {
        addMessage(e);
    }
    function handleGameTimer(e) {
        console.log(e.time);
    }
    function handleNewWord(e) {
        console.log(`New word: ${e.newWord}; drawer: ${e.newDrawer}`);
    }
    function handleGameScore(e) {
        console.log(`Game score ${e.userId}:${e.score}`);
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
export function sendChatMessage(m) {
    const message = {
        messageType: MessageType.CHAT_MESSAGE,
        payload: m,
    };
    sendMessageAsString(ws, message);
    console.log(`Chat message with content ${m.content} sent to server`);
}
