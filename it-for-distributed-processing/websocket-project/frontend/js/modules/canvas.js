import { sendStringMessage } from "./ws-client.js";
let frameCounter = 0;
let strokeFrame;
let isDrawing = false;
export function createCanvas() {
    window.addEventListener('DOMContentLoaded', () => {
        const parent = document.getElementById('canvas-flexbox');
        const canvas = document.getElementById('drawing-canvas');
        if (!canvas) {
            return;
        }
        const ctx = canvas.getContext('2d');
        if (!ctx) {
            console.warn("Canvas context not supported");
            return;
        }
        let pos = { x: 0, y: 0 };
        resize();
        window.addEventListener('load', resize);
        window.addEventListener('resize', resize);
        document.addEventListener('mousemove', draw);
        document.addEventListener('mousedown', setPosition);
        document.addEventListener('mouseup', sendFrames);
        document.addEventListener('mouseenter', setPosition);
        function resize() {
            if (!parent) {
                console.warn("canvas-container is null");
                return;
            }
            canvas.width = parent.offsetWidth;
            canvas.height = parent.offsetHeight;
        }
        function setPosition(e) {
            pos.x = e.clientX - canvas.offsetLeft;
            pos.y = e.clientY - canvas.offsetTop;
        }
        function clear() {
            ctx === null || ctx === void 0 ? void 0 : ctx.clearRect(0, 0, canvas.width, canvas.height);
        }
        function draw(e) {
            if (e.buttons !== 1)
                return;
            if (ctx === null) {
                console.warn("ctx null in canvas.js draw() func");
                return;
            }
            if (!isDrawing) {
                isDrawing = true;
                ctx.lineWidth = 5;
                ctx.lineCap = 'round';
                ctx.strokeStyle = '#c0392b';
                console.log("isDrawing = true");
                initializeStrokeFrame(ctx.lineWidth, ctx.lineCap, ctx.strokeStyle);
            }
            frameCounter++;
            ctx.beginPath();
            const from = {
                x: pos.x,
                y: pos.y
            };
            ctx.moveTo(from.x, from.y);
            setPosition(e);
            const to = {
                x: pos.x,
                y: pos.y
            };
            ctx.lineTo(to.x, to.y);
            const frame = {
                frameId: frameCounter,
                from: from,
                to: to
            };
            strokeFrame.points.push(frame);
            //logFrame(frame);
            ctx.stroke(); // draw
        }
        function initializeStrokeFrame(lineWidth, lineCap, strokeStyle) {
            strokeFrame = {
                lineWidth: lineWidth,
                lineCap: lineCap,
                strokeStyle: strokeStyle,
                points: []
            };
        }
        function sendFrames() {
            if (!isDrawing)
                return;
            console.log("isDrawing = false");
            sendStringMessage(JSON.stringify(strokeFrame));
            isDrawing = false;
            console.log(`Sent ${strokeFrame.points.length} frames`);
        }
    });
}
