import { initWebsocket, sendDrawing, sendHandshake } from "./game-ws-client.js";
import { Point, PointFrame, StrokeFrame, StrokeStyle } from "./model/point-frame.js";

let parent: HTMLElement;
let canvas: HTMLCanvasElement | null;
let ctx: CanvasRenderingContext2D | null;
let uId: string;
let sId: string;
let pos: Point;
let frameCounter = 0;
let strokeFrame: StrokeFrame;
let isDrawing = false;
let isDrawingEnabled = true;

export async function createCanvas(userId: string, sessionId: string) {
    return new Promise<void>(async (resolve, reject) => {
        try {
            parent = document.getElementById('canvas-flexbox')!;
            canvas = document.getElementById('drawing-canvas') as HTMLCanvasElement;
            if (!canvas) {
                console.error("Canvas element not supported!");
                return;
            } else {
                console.log("Canvas element found");
            }

            ctx = canvas.getContext('2d');
            if (!ctx) {
                console.warn("Canvas context not supported");
                return;
            }

            uId = userId;
            sId = sessionId;
            pos = { x: 0, y: 0 }
            resize();

            window.addEventListener('load', resize);
            window.addEventListener('resize', resize);
            document.addEventListener('mousemove', draw);
            document.addEventListener('mousedown', setPosition);
            document.addEventListener('mouseup', sendFrame);
            document.addEventListener('mouseenter', setPosition);

            console.log("Before initWebsocket");
            await initWebsocket();
            console.log("After initWebsocket");
            sendHandshake(uId, sId);

            console.log("Initialized canvas successfully!");
            resolve();
        } catch (error) {
            reject(error);
        }
    });
}

function resize() {
    if (!parent || !canvas) {
        console.warn("canvas-container is null");
        return;
    }
    canvas.width = parent.offsetWidth;
    canvas.height = parent.offsetHeight;
}

function setPosition(e: MouseEvent) {
    if (!canvas || !isDrawingEnabled) return;
    pos.x = e.clientX - canvas.offsetLeft;
    pos.y = e.clientY - canvas.offsetTop;
}

export function clearCanvas() {
    if (!canvas) return;
    ctx?.clearRect(0, 0, canvas.width, canvas.height);
}

function draw(e: MouseEvent) {
    if (e.buttons !== 1 || !isDrawingEnabled) return;
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
    if (uId === undefined) {
        console.error("Client id:", uId);
        return;
    }
    strokeFrame = {
        senderId: uId,
        lineWidth: lineWidth,
        lineCap: lineCap,
        strokeStyle: strokeStyle,
        from: from,
        points: []
    }
}

function sendFrame() {
    if (!isDrawing || !isDrawingEnabled) return;
    isDrawing = false;
    console.log("isDrawing = false");
    sendDrawing(strokeFrame);
    console.log(`Sent ${strokeFrame.points.length} frames`);
}

export function drawFromFrame(strokeFrame: StrokeFrame) {
    if (!canvas) {
        console.warn("Canvas element 'drawing-canvas' is null");
        return;
    }

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

    console.log(`Drawn from frame: ${strokeFrame}`);
}

export function toggleDrawing(enable: boolean) {
    isDrawingEnabled = enable;
    if (!enable) {
        isDrawing = false;
    }
}
