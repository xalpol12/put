import { createCanvas, initDrawConnection } from "./modules/canvas.js";
import { joinSession, createSession } from "./modules/join-ws-client.js";
window.addEventListener('DOMContentLoaded', () => {
    var _a, _b;
    (_a = document.getElementById('joinButton')) === null || _a === void 0 ? void 0 : _a.addEventListener('click', showJoinPrompt);
    (_b = document.getElementById('hostButton')) === null || _b === void 0 ? void 0 : _b.addEventListener('click', showHostPrompt);
});
window.onload = () => {
    const clientId = sessionStorage.getItem('sessionId');
    const sessionId = sessionStorage.getItem('sessionId');
    if (clientId && sessionId) {
        joinRoom(clientId, sessionId);
    }
    else {
        showPage('home');
    }
};
function joinRoom(clientId, sessionId) {
    if (!clientId || !sessionId) {
        alert("Client ID or Session ID not found.");
        return;
    }
    joinSession(clientId);
    createCanvas(clientId);
    initDrawConnection();
    showPage('canvas-chat');
}
function showPage(pageId) {
    //Hide all pages
    const pages = document.querySelectorAll('.page');
    pages.forEach(page => page.classList.remove('active'));
    const selectedPage = document.getElementById(pageId);
    if (selectedPage) {
        selectedPage.classList.add('active');
    }
}
function showJoinPrompt() {
    const clientIdInput = prompt("Client ID:");
    const sessionIdInput = prompt("Session ID:");
    if (clientIdInput && sessionIdInput) {
        sessionStorage.setItem('clientId', clientIdInput);
        sessionStorage.setItem('sessionId', sessionIdInput);
        joinRoom(clientIdInput, sessionIdInput);
    }
    else {
        alert("Client ID or Session ID cannot be empty.");
    }
}
function showHostPrompt() {
    const clientIdInput = prompt("Client ID:");
    const sessionIdInput = prompt("New Session ID:");
    if (clientIdInput && sessionIdInput) {
        sessionStorage.setItem('clientId', clientIdInput);
        sessionStorage.setItem('sessionId', sessionIdInput);
        createSession(sessionIdInput);
        joinRoom(clientIdInput, sessionIdInput);
    }
    else {
        alert("Client ID or Session ID cannot be empty.");
    }
}
