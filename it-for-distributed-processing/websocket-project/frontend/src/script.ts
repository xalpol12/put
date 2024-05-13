import { createCanvas, initDrawConnection } from "./modules/canvas.js"
import { joinSession, createSession } from "./modules/join-ws-client.js";

window.addEventListener('DOMContentLoaded', () => {
    document.getElementById('joinButton')?.addEventListener('click', showJoinPrompt);
    document.getElementById('hostButton')?.addEventListener('click', showHostPrompt);
})

window.onload = () => {
    const clientId = sessionStorage.getItem('sessionId');
    const sessionId = sessionStorage.getItem('sessionId');

    if (clientId && sessionId) {
        joinRoom(clientId, sessionId);
    } else {
        showPage('home');
    }
}

function joinRoom(clientId: string, sessionId: string) {
    if (!clientId || !sessionId) {
        alert("Client ID or Session ID not found.");
        return;
    }

    joinSession(clientId);
    createCanvas(clientId);
    initDrawConnection();
    showPage('canvas-chat');
}

function showPage(pageId: string) {
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
    } else {
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
    } else {
        alert("Client ID or Session ID cannot be empty.");
    }
}
