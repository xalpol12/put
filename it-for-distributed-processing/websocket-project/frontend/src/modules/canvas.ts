export function createCanvas(): void {
    window.addEventListener('DOMContentLoaded', () => {
        const canvas = document.getElementById('drawing-canvas') as HTMLCanvasElement;
        if (!canvas) {
            console.warn("Canvas element not found");
            return;
        }

        let ctx = canvas.getContext('2d');
        if (!ctx) {
            console.warn("Canvas context not supported");
            return;
        }

        resize();

        let pos = { x: 0, y: 0 }

        window.addEventListener('resize', resize);
        document.addEventListener('mousemove', draw);
        document.addEventListener('mousedown', setPosition);
        document.addEventListener('mouseenter', setPosition);

        function setPosition(e: MouseEvent) {
            const rect = canvas.getBoundingClientRect();
            pos.x = e.clientX - rect.left;
            pos.y = e.clientY - rect.top;

        }

        function resize() {
            canvas.width = window.innerWidth;
            canvas.height = window.innerHeight;
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

