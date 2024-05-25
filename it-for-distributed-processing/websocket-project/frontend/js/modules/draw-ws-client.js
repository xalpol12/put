import { drawFromFrame } from "./canvas.js";
import { sendStringMessage } from "./ws-service.js";
let ws;
export function initDrawConnection() {
    ws = new WebSocket('ws://localhost:8081/draw');
    ws.onopen = (e) => { console.log("Opened /draw connection " + e.type); };
    ws.onmessage = (e) => {
        const strokeFrame = JSON.parse(e.data);
        logFrame(strokeFrame);
        drawFromFrame(strokeFrame);
    };
    ws.onerror = (e) => { console.log(e.type); };
    ws.onclose = (e) => {
        console.log(`Code: ${e.code}, reason: ${e.reason}`);
        ws.close();
    };
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
export function sendStrokeFrame(strokeFrame) {
    sendStringMessage(ws, JSON.stringify(strokeFrame));
}
