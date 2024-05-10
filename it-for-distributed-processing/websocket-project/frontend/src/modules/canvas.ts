import { Point, PointFrame, StrokeFrame, StrokeStyle } from "./model/point-frame.js";
import { sendStringMessage, getClientId } from "./ws-client.js";

let frameCounter = 0;
let strokeFrame: StrokeFrame;
let isDrawing = false;

export function createCanvas(): void {
    window.addEventListener('DOMContentLoaded', () => {
        const parent = document.getElementById('canvas-flexbox');
        const canvas = document.getElementById('drawing-canvas') as HTMLCanvasElement;
        if (!canvas) {
            return;
        }

        const ctx = canvas.getContext('2d');
        if (!ctx) {
            console.warn("Canvas context not supported");
            return;
        }

        let pos = { x: 0, y: 0 }
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

        function setPosition(e: MouseEvent) {
            pos.x = e.clientX - canvas.offsetLeft;
            pos.y = e.clientY - canvas.offsetTop;
        }

        function clear() {
            ctx?.clearRect(0, 0, canvas.width, canvas.height);
        }

        function draw(e: MouseEvent) {
            if (e.buttons !== 1) return;
            if (ctx === null) {
                console.warn("ctx null in canvas.js draw() func")
                return;
            }

            frameCounter++;
            ctx.beginPath();
            const from = {
                x: pos.x,
                y: pos.y
            };
            ctx.moveTo(from.x, from.y)
            setPosition(e);

            const to = {
                x: pos.x,
                y: pos.y
            };
            ctx.lineTo(to.x, to.y)

            const frame: PointFrame = {
                frameId: frameCounter,
                to: to
            }

            if (!isDrawing) { // init new frame when drawing started
                isDrawing = true;
                ctx.lineWidth = 5;
                ctx.lineCap = 'round';
                ctx.strokeStyle = '#c0392b';
                initializeStrokeFrame(ctx.lineWidth, ctx.lineCap, ctx.strokeStyle, from);
                console.log("isDrawing = true");
            }
            strokeFrame.points.push(frame);
            ctx.stroke();
        }

        function initializeStrokeFrame(lineWidth: number, lineCap: CanvasLineCap, strokeStyle: StrokeStyle, from: Point) {
            let id = getClientId();
            if (id === undefined) {
                console.error("Client id:", id);
                return;
            }
            strokeFrame = {
                senderId: id,
                lineWidth: lineWidth,
                lineCap: lineCap,
                strokeStyle: strokeStyle,
                from: from,
                points: []
            }
        }

        function sendFrames() {
            if (!isDrawing) return;
            console.log("isDrawing = false");
            sendStringMessage(JSON.stringify(strokeFrame));
            isDrawing = false;
            console.log(`Sent ${strokeFrame.points.length} frames`);
        }
    });
}

export function drawFromFrame(strokeFrame: StrokeFrame) {
    const canvas = document.getElementById('drawing-canvas') as HTMLCanvasElement;
    if (!canvas) {
        console.warn("Canvas element 'drawing-canvas' is null");
        return;
    }

    const ctx = canvas.getContext('2d');
    if (!ctx) {
        console.warn("Canvas context not supported");
        return;
    }

    ctx.lineWidth = strokeFrame.lineWidth;
    ctx.lineCap = strokeFrame.lineCap;
    ctx.strokeStyle = strokeFrame.strokeStyle;

    const points = strokeFrame.points;
    if (points.length > 0) {
        ctx.beginPath();
        ctx.moveTo(strokeFrame.from.x, strokeFrame.from.y);
        for (let i = 1; i < points.length; i++) {
            ctx.lineTo(points[i].to.x, points[i].to.y);
        }
        ctx.stroke();
    }
}
