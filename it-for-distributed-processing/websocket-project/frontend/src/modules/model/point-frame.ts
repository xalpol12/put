export interface StrokeFrame {
    lineWidth: number,
    lineCap: CanvasLineCap,
    strokeStyle: StrokeStyle,
    points: PointFrame[]
}

export interface PointFrame {
    frameId: number,
    from: Point,
    to: Point
};

export type StrokeStyle =
    | string
    | CanvasGradient
    | CanvasPattern;

export interface Point {
    x: number;
    y: number;
}
