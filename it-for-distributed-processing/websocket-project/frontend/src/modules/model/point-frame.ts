export interface PointFrame {
    frameId: number,
    lineWidth: number,
    lineCap: CanvasLineCap,
    strokeStyle: StrokeStyle,
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
