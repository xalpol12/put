import { createCanvas, initDrawConnection } from "./modules/canvas.js";
import { joinSession } from "./modules/join-ws-client.js";
function joinRoom() {
    const clientIdInput = document.getElementById('clientIdInput');
    const sessionIdInput = document.getElementById('sessionIdInput');
    const clientId = clientIdInput.value;
    const sessionId = sessionIdInput.value;
    if (!clientId || !sessionId) {
        alert("Please provide both Client ID and Session ID.");
        return;
    }
    // Hide lobby container
    const lobbyContainer = document.getElementById('lobby-container');
    if (lobbyContainer) {
        lobbyContainer.style.display = 'none';
    }
    const canvasFlexbox = document.getElementById('canvas-flexbox');
    if (canvasFlexbox) {
        canvasFlexbox.style.display = 'block';
    }
    joinSession(clientId);
    createCanvas(clientId);
    initDrawConnection();
}
//let clientId = localStorage.getItem('clientId');
//while (!clientId) {
//    clientId = prompt("Enter your id:");
//    if (clientId) {
//        localStorage.setItem('clientId', clientId);
//        console.log(`clientId set to ${clientId}`);
//    } else {
//        console.error("No clientId set! Set clientId");
//    }
//}
