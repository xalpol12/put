import { clearCanvas, drawFromFrame } from "./canvas.js";
import { addMessage } from "./chat.js";
import { MessageType } from "./model/ws-message.js";
import { decodeProtobufMessage, encodeProtobufMessage } from "./proto/proto-converter.js";
import { addScoreEntry } from "./score.js";
import { updateTimer, updateWord } from "./timer.js";
import { sendMessageAsBinary } from "./ws-service.js";
let ws;
export function initWebsocket() {
    return new Promise((resolve, reject) => {
        ws = new WebSocket('ws://localhost:8081/game');
        ws.binaryType = "arraybuffer";
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
        const binaryData = new Uint8Array(e.data);
        decodeProtobufMessage(binaryData)
            .then((message) => {
            const messageType = MessageType.fromString(message.messageType);
            if (messageType === undefined) {
                console.error("Unknown message type:", message.messageType);
                return;
            }
            if (message.messageType == "DRAWING") {
                handleDrawing(message.drawingPayload);
            }
            else if (message.messageType == "CHAT_MESSAGE") {
                handleChatMessage(message.chatMessagePayload);
            }
            else if (message.messageType == "GAME_TIMER") {
                handleGameTimer(message.gameTimerPayload);
            }
            else if (message.messageType == "NEW_WORD") {
                handleNewWord(message.newWordPayload);
            }
            else if (message.messageType == "GAME_SCORE") {
                handleGameScore(message.gameScorePayload);
            }
            else if (message.messageType == "CLEAR_BOARD") {
                handleClearBoard();
            }
            else {
                console.error("Unknown message type:", message.messageType);
            }
        })
            .catch((error) => {
            console.error("Error decoding Protobuf message:", error);
        });
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
        updateTimer(e);
    }
    function handleNewWord(e) {
        updateWord(e);
        console.log(`New word: ${e.newWord}; drawer: ${e.newDrawer}`);
    }
    function handleGameScore(e) {
        addScoreEntry(e);
        console.log(`Game score ${e.userId}:${e.score}`);
    }
    function handleClearBoard() {
        clearCanvas();
        console.log(`Clear board message received`);
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
    sendMessage(handshake);
}
export function sendDrawing(strokeFrame) {
    const drawing = {
        messageType: MessageType.DRAWING,
        payload: {
            drawingFrame: JSON.stringify(strokeFrame),
        }
    };
    sendMessage(drawing);
}
export function sendChatMessage(m) {
    const message = {
        messageType: MessageType.CHAT_MESSAGE,
        payload: m,
    };
    sendMessage(message);
}
function sendMessage(m) {
    encodeProtobufMessage(m).then((serializedMessage) => {
        sendMessageAsBinary(ws, serializedMessage);
        console.log(`Message with ${m.payload} sent to server`);
    });
}
