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

            ctx.beginPath();

            ctx.lineWidth = 5;
            ctx.lineCap = 'round';
            ctx.strokeStyle = '#c0392b';

            ctx.moveTo(pos.x, pos.y) // from
            setPosition(e);
            ctx.lineTo(pos.x, pos.y) // to

            ctx.stroke(); // draw
        }
    });
}

