import { PointFrame } from "./model/point-frame.js";
import { sendStringMessage } from "./ws-client.js";

let frameCounter = 0;
let frameBuffer: PointFrame[] = [];
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
            if (!isDrawing) {
                isDrawing = true;
                console.log("isDrawing = true");
            }

            frameCounter++;
            ctx.beginPath();

            ctx.lineWidth = 5;
            ctx.lineCap = 'round';
            ctx.strokeStyle = '#c0392b';

            const from = {
                x: pos.x,
                y: pos.y
            };
            ctx.moveTo(from.x, from.y) // from
            setPosition(e);

            const to = {
                x: pos.x,
                y: pos.y
            };
            ctx.lineTo(to.x, to.y)

            const frame = {
                frameId: frameCounter,
                lineWidth: ctx.lineWidth,
                lineCap: ctx.lineCap,
                strokeStyle: ctx.strokeStyle,
                from: from,
                to: to
            }
            frameBuffer.push(frame);
            //logFrame(frame);
            ctx.stroke(); // draw
        }

        function logFrame(frame: PointFrame) {
            console.log(`Frame #${frame.frameId}, lineWidth: ${frame.lineWidth}, lineCap: ${frame.lineCap}, strokeStyle: ${frame.strokeStyle}, 
                            from: (${frame.from.x}, ${frame.from.y}); to: (${frame.to.x}, ${frame.to.y})`)

        }

        function sendFrames() {
            if (!isDrawing) return;
            console.log("isDrawing = false");
            sendStringMessage(JSON.stringify(frameBuffer));
            isDrawing = false;
            console.log(`Sent ${frameBuffer.length} frames`);
            frameBuffer = [];
        }
    });
}

