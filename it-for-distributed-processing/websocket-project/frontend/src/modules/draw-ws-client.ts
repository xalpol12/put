import { drawFromFrame } from "./canvas.js";
import { StrokeFrame } from "./model/point-frame.js";
import { sendStringMessage } from "./ws-service.js";

let ws: WebSocket;

export function initDrawConnection() {
    ws = new WebSocket('ws://localhost:8081/draw');

    ws.onopen = (e: Event) => { console.log(e.type); }
    ws.onmessage = (e: MessageEvent) => {
        const strokeFrame: StrokeFrame = JSON.parse(e.data);
        logFrame(strokeFrame);
        drawFromFrame(strokeFrame);
    };
    ws.onerror = (e: Event) => { console.log(e.type) };
    ws.onclose = (e: CloseEvent) => {
        console.log(`Code: ${e.code}, reason: ${e.reason}`);
        ws.close();
    }

    function logFrame(strokeFrame: StrokeFrame) {
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

export function sendStrokeFrame(strokeFrame: StrokeFrame) {
    sendStringMessage(ws, JSON.stringify(strokeFrame));
}
