export function sendStringMessage(ws, m) {
    if (ws && ws.readyState === WebSocket.OPEN) {
        ws.send(m);
        console.log(`message: ${m} sent!`);
    }
    else {
        console.error('WebSocket connection not open.');
    }
}
