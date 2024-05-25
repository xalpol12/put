import { sendStringMessage } from "./ws-service.js";
import { drawFromFrame } from "./canvas.js";

export function joinSession(clientId: string) {
    let ws = new WebSocket('ws://localhost:8081/join');

    ws.onopen = () => {
        sendStringMessage(ws, clientId);
        console.log("Opened /join session");
    }

    ws.onmessage = (e: MessageEvent) => {
        drawFromFrame(JSON.parse(e.data));
    }

    ws.onerror = (e: Event) => {
        console.log(e);
    }
}

export function createSession(sessionId: string) {
    console.log("Create session hit");
}
