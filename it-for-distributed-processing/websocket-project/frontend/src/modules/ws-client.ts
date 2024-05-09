let ws: WebSocket;
let clientId: string;

export function initWS() {
    ws = new WebSocket('ws://localhost:8081/name');

    ws.onopen = function(e) { onConnect(e) };
    ws.onmessage = function(e) { onMessage(e) };
    ws.onclose = function(e) { onClose(e) };
    ws.onerror = function(e) { onError(e) };

    function onConnect(e: Event) {
        console.log(e.type);
    }

    function onMessage(e: MessageEvent) {
        if (clientId === null || clientId === undefined) {
            clientId = e.data;
            console.log(`clientId = ${clientId}`);
        } else {
            if (e.data.senderId != clientId) { //assuming frame is StrokeFrame type
                console.log(`${e.type} ${e.data}`);
            }
        }
    }

    function onError(e: Event) {
        console.log(e.type);

    }

    function onClose(e: CloseEvent) {
        console.log(`Code: ${e.code}, reason: ${e.reason}`);
        ws.close();
    }
}

export function sendStringMessage(m: string) {
    if (ws && ws.readyState === WebSocket.OPEN) {
        ws.send(m);
        console.log(`message: ${m} sent!`);
    } else {
        console.error('WebSocket connection not open.');
    }
}

export function getClientId(): string | undefined {
    if (clientId === null || clientId === undefined) {
        return undefined;
    } else return clientId;
}
