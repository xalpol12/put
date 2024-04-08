export function createCanvas(): void {
    let canvas = document.createElement('canvas');
    document.body.appendChild(canvas);
    console.log("canvas element created");

    canvas.style.position = 'fixed';

    let ctx = canvas.getContext('2d');
    resize();

    let pos = { x: 0, y: 0 }

    window.addEventListener('resize', resize);
    document.addEventListener('mousemove', draw);
    document.addEventListener('mousedown', setPosition);
    document.addEventListener('mouseenter', setPosition);

    function setPosition(e: MouseEvent) {
        pos.x = e.clientX;
        pos.y = e.clientY;

    }

    function resize() {
        if (ctx === null) {
            console.warn("ctx null in canvas.js resize() func")
            return;
        }
        ctx.canvas.width = window.innerWidth;
        ctx.canvas.height = window.innerHeight;
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
}

