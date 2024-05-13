import { sendStringMessage } from "./ws-service.js";
import { drawFromFrame } from "./canvas.js";
export function joinSession(clientId) {
    let ws = new WebSocket('ws://localhost:8081/join');
    ws.onopen = () => {
        sendStringMessage(ws, clientId);
    };
    ws.onmessage = (e) => {
        drawFromFrame(JSON.parse(e.data));
    };
    ws.onerror = (e) => {
        console.log(e);
    };
}
export function createSession(sessionId) {
    console.log("Create session hit");
}
