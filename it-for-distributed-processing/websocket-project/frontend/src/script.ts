import { createCanvas } from "./modules/canvas.js"
import { initDrawConnection } from "./modules/draw-ws-client.js";
import { joinSession, createSession } from "./modules/join-ws-client.js";

async function loadPage(page: any) {
    const response = await fetch(`./html/${page}`);
    const content = await response.text();
    document.getElementById('container')!.innerHTML = content;

    if (page === 'homepage.html') {
        document.getElementById('joinButton')?.addEventListener('click', () => loadPage('gamepage.html'));
        document.getElementById('hostButton')?.addEventListener('click', () => loadPage('gamepage.html'));
    }
}

loadPage('homepage.html');

// window.addEventListener('DOMContentLoaded', () => {
//     document.getElementById('joinButton')?.addEventListener('click', showJoinPrompt);
//     document.getElementById('hostButton')?.addEventListener('click', showHostPrompt);
// 
//     const clientId = sessionStorage.getItem('clientId');
//     const sessionId = sessionStorage.getItem('sessionId');
// 
//     if (clientId && sessionId) {
//         console.log(`Found clientId and sessionId in sessionStorage`);
//         joinRoom(clientId, sessionId);
//     } else {
//         console.log(`ClientId and SessionId not found`);
//         showPage('home-page');
//     }
// })
// 
// function joinRoom(clientId: string, sessionId: string) {
//     if (!clientId || !sessionId) {
//         alert("Client ID or Session ID not found.");
//         return;
//     }
// 
//     createCanvas(clientId);
//     joinSession(clientId);
//     initDrawConnection();
//     showPage('game-page');
// }
// 
// function showPage(pageId: string) {
//     //Hide all pages
//     const pages = document.querySelectorAll('.page');
//     pages.forEach(page => page.classList.remove('active'));
// 
//     const selectedPage = document.getElementById(pageId);
//     if (selectedPage) {
//         selectedPage.classList.add('active');
//         console.log(`${pageId} activated`);
//     }
// }
// 
// function showJoinPrompt() {
//     const clientIdInput = prompt("Client ID:");
//     const sessionIdInput = prompt("Session ID:");
// 
//     if (clientIdInput && sessionIdInput) {
//         sessionStorage.setItem('clientId', clientIdInput);
//         sessionStorage.setItem('sessionId', sessionIdInput);
//         joinRoom(clientIdInput, sessionIdInput);
//     } else {
//         alert("Client ID or Session ID cannot be empty.");
//     }
// }
// 
// function showHostPrompt() {
//     const clientIdInput = prompt("Client ID:");
//     const sessionIdInput = prompt("New Session ID:");
// 
//     if (clientIdInput && sessionIdInput) {
//         sessionStorage.setItem('clientId', clientIdInput);
//         sessionStorage.setItem('sessionId', sessionIdInput);
//         createSession(sessionIdInput);
//         joinRoom(clientIdInput, sessionIdInput);
//     } else {
//         alert("Client ID or Session ID cannot be empty.");
//     }
// }
