var __awaiter = (this && this.__awaiter) || function (thisArg, _arguments, P, generator) {
    function adopt(value) { return value instanceof P ? value : new P(function (resolve) { resolve(value); }); }
    return new (P || (P = Promise))(function (resolve, reject) {
        function fulfilled(value) { try { step(generator.next(value)); } catch (e) { reject(e); } }
        function rejected(value) { try { step(generator["throw"](value)); } catch (e) { reject(e); } }
        function step(result) { result.done ? resolve(result.value) : adopt(result.value).then(fulfilled, rejected); }
        step((generator = generator.apply(thisArg, _arguments || [])).next());
    });
};
function loadPage(page) {
    var _a, _b;
    return __awaiter(this, void 0, void 0, function* () {
        const response = yield fetch(`./html/${page}`);
        const content = yield response.text();
        document.getElementById('container').innerHTML = content;
        if (page === 'homepage.html') {
            (_a = document.getElementById('joinButton')) === null || _a === void 0 ? void 0 : _a.addEventListener('click', () => loadPage('gamepage.html'));
            (_b = document.getElementById('hostButton')) === null || _b === void 0 ? void 0 : _b.addEventListener('click', () => loadPage('gamepage.html'));
        }
    });
}
loadPage('gamepage.html');
export {};
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
