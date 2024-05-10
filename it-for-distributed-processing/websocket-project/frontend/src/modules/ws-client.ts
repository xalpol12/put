import { drawFromFrame } from "./canvas.js";
import { StrokeFrame } from "./model/point-frame.js";

let ws: WebSocket;
let clientId: string;

export function initWS() {
    ws = new WebSocket('ws://localhost:8081/name');

    ws.onopen = function(e) { onConnect(e) };
    ws.onmessage = function(e) { onMessage(e) };
    ws.onclose = function(e) { onClose(e) };
    ws.onerror = function(e) { onError(e) };

    function onConnect(e: Event) {
        console.log(e.type);
    }

    function onMessage(e: MessageEvent) {
        if (clientId === null || clientId === undefined) {
            clientId = e.data;
            console.log(`clientId = ${clientId}`);
        } else {
            const strokeFrame: StrokeFrame = JSON.parse(e.data);
            if (strokeFrame.senderId !== clientId) {
                drawFromFrame(strokeFrame);
                //logFrame(strokeFrame);
            }
        }
    }

    function logFrame(strokeFrame: StrokeFrame) {
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

    function onError(e: Event) {
        console.log(e.type);

    }

    function onClose(e: CloseEvent) {
        console.log(`Code: ${e.code}, reason: ${e.reason}`);
        ws.close();
    }
}

export function sendStringMessage(m: string) {
    if (ws && ws.readyState === WebSocket.OPEN) {
        ws.send(m);
        console.log(`message: ${m} sent!`);
    } else {
        console.error('WebSocket connection not open.');
    }
}

export function getClientId(): string | undefined {
    if (clientId === null || clientId === undefined) {
        return undefined;
    } else return clientId;
}
