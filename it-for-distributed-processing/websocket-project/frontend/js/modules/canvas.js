import { initDrawConnection, sendStrokeFrame } from "./draw-ws-client.js";
import { joinSession } from "./join-ws-client.js";
let parent;
let canvas;
let ctx;
let id;
let pos;
let frameCounter = 0;
let strokeFrame;
let isDrawing = false;
let isCanvasReady = false;
let frameQueue = [];
export function createCanvas(clientId) {
    parent = document.getElementById('canvas-flexbox');
    canvas = document.getElementById('drawing-canvas');
    if (!canvas) {
        console.error("Canvas element not supported!");
        return;
    }
    else {
        console.log("Canvas element found");
    }
    ctx = canvas.getContext('2d');
    if (!ctx) {
        console.warn("Canvas context not supported");
        return;
    }
    id = clientId;
    pos = { x: 0, y: 0 };
    resize();
    window.addEventListener('load', resize);
    window.addEventListener('resize', resize);
    document.addEventListener('mousemove', draw);
    document.addEventListener('mousedown', setPosition);
    document.addEventListener('mouseup', sendFrame);
    document.addEventListener('mouseenter', setPosition);
    isCanvasReady = true;
    processFrameQueue();
    console.log("Initialized canvas successfully!");
    joinSession(clientId);
    initDrawConnection();
}
function resize() {
    if (!parent || !canvas) {
        console.warn("canvas-container is null");
        return;
    }
    canvas.width = parent.offsetWidth;
    canvas.height = parent.offsetHeight;
}
function setPosition(e) {
    if (!canvas)
        return;
    pos.x = e.clientX - canvas.offsetLeft;
    pos.y = e.clientY - canvas.offsetTop;
}
function clear() {
    if (!canvas)
        return;
    ctx === null || ctx === void 0 ? void 0 : ctx.clearRect(0, 0, canvas.width, canvas.height);
}
function draw(e) {
    if (e.buttons !== 1)
        return;
    if (ctx === null) {
        console.warn("ctx null in canvas.js draw() func");
        return;
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
        to: to
    };
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
function initializeStrokeFrame(lineWidth, lineCap, strokeStyle, from) {
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
    };
}
function sendFrame() {
    if (!isDrawing)
        return;
    isDrawing = false;
    console.log("isDrawing = false");
    sendStrokeFrame(strokeFrame);
    console.log(`Sent ${strokeFrame.points.length} frames`);
}
export function drawFromFrame(strokeFrame) {
    if (!isCanvasReady) {
        console.log("Canvas not ready, queueing frame");
        frameQueue.push(strokeFrame);
        return;
    }
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
function processFrameQueue() {
    while (frameQueue.length > 0) {
        const frame = frameQueue.shift();
        if (frame) {
            drawFromFrame(frame);
        }
    }
}
