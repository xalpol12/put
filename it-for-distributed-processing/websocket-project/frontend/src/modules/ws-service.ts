import { CustomMessage } from "./model/ws-message";

export function sendMessageAsString(ws: WebSocket, m: CustomMessage) {
    if (ws && ws.readyState === WebSocket.OPEN) {
        ws.send(JSON.stringify(m));
        console.log(`message: ${m} sent!`);
    } else {
        console.error('WebSocket connection not open.');
    }
}
