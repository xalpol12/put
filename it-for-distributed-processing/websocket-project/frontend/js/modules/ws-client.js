import { drawFromFrame } from "./canvas.js";
let ws;
let clientId;
export function initWS() {
    ws = new WebSocket('ws://localhost:8081/name');
    ws.onopen = function (e) { onConnect(e); };
    ws.onmessage = function (e) { onMessage(e); };
    ws.onclose = function (e) { onClose(e); };
    ws.onerror = function (e) { onError(e); };
    function onConnect(e) {
        console.log(e.type);
    }
    function onMessage(e) {
        if (clientId === null || clientId === undefined) {
            clientId = e.data;
            console.log(`clientId = ${clientId}`);
        }
        else {
            const strokeFrame = JSON.parse(e.data);
            if (strokeFrame.senderId !== clientId) {
                drawFromFrame(strokeFrame);
                //logFrame(strokeFrame);
            }
        }
    }
    function logFrame(strokeFrame) {
        console.log(`Sender ID: ${strokeFrame.senderId}`);
        console.log(`Line Width: ${strokeFrame.lineWidth}`);
        console.log(`Line Cap: ${strokeFrame.lineCap}`);
        console.log(`Stroke Style: ${strokeFrame.strokeStyle}`);
        strokeFrame.points.forEach((pointFrame) => {
            console.log(`Frame ID: ${pointFrame.frameId}`);
            console.log(`From: (${pointFrame.from.x}, ${pointFrame.from.y})`);
            console.log(`To: (${pointFrame.to.x}, ${pointFrame.to.y})`);
        });
    }
    function onError(e) {
        console.log(e.type);
    }
    function onClose(e) {
        console.log(`Code: ${e.code}, reason: ${e.reason}`);
        ws.close();
    }
}
export function sendStringMessage(m) {
    if (ws && ws.readyState === WebSocket.OPEN) {
        ws.send(m);
        console.log(`message: ${m} sent!`);
    }
    else {
        console.error('WebSocket connection not open.');
    }
}
export function getClientId() {
    if (clientId === null || clientId === undefined) {
        return undefined;
    }
    else
        return clientId;
}
