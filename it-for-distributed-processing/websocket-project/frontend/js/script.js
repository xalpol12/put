import { createCanvas } from "./modules/canvas.js";
import { joinSessionAndGetClientId, rejoinSession } from "./modules/join-ws-client.js";
let clientId = localStorage.getItem('clientId');
if (!clientId) {
    joinSessionAndGetClientId()
        .then(id => {
        clientId = id;
        localStorage.setItem('clientId', id);
        console.log(`clientId = ${id}`);
        createCanvas(clientId);
    })
        .catch(error => {
        console.error("Error joining websocket session: ", error);
    });
}
else {
    createCanvas(clientId);
    rejoinSession(clientId);
}
