export function sendMessageAsString(ws, m) {
    if (ws && ws.readyState === WebSocket.OPEN) {
        ws.send(JSON.stringify(m));
        console.log(`message: ${m} sent!`);
    }
    else {
        console.error('WebSocket connection not open.');
    }
}
export function sendMessageAsBinary(ws, data) {
    if (ws && ws.readyState === WebSocket.OPEN) {
        ws.send(data);
        console.log(`message: ${data} sent!`);
    }
    else {
        console.error('WebSocket connection not open.');
    }
}
