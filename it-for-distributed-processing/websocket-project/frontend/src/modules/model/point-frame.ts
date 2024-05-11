export interface StrokeFrame {
    senderId: string,
    lineWidth: number,
    lineCap: CanvasLineCap,
    strokeStyle: StrokeStyle,
    from: Point,
    points: PointFrame[]
}

export interface PointFrame {
    frameId: number,
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
