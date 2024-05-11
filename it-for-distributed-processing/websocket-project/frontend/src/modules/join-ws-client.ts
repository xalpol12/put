import { sendStringMessage } from "./ws-service.js";
import { drawFromFrame } from "./canvas.js";

export async function joinSessionAndGetClientId() {
    return new Promise<string>((resolve, reject) => {
        let ws = new WebSocket('ws://localhost:8081/join');

        ws.onmessage = (e: MessageEvent) => {
            const clientId = e.data;
            ws.close();
            resolve(clientId);
        }

        ws.onerror = (error) => {
            reject(error);
        }
    })
}

export function rejoinSession(clientId: string) {
    let ws = new WebSocket('ws://localhost:8081/rejoin');

    ws.onopen = () => {
        sendStringMessage(ws, clientId);
    }

    ws.onmessage = (e: MessageEvent) => {
        drawFromFrame(JSON.parse(e.data));
    }
}
