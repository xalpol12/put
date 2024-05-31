import { postClient, postSession } from "./modules/fetch.js";
import { CredentialsResponse } from "./modules/model/credentials-response.js";

document.addEventListener('DOMContentLoaded', () => {
    loadPage('homepage.html');
})

async function loadPage(page: any) {
    const response = await fetch(`./html/${page}`);
    const content = await response.text();
    document.getElementById('container')!.innerHTML = content;

    if (page === "homepage.html") {
        document.getElementById('joinButton')?.addEventListener('click', onJoinButtonClick);
        document.getElementById('hostButton')?.addEventListener('click', onHostButtonClick);
    }
    console.log(`${page} loaded`);
}



function getInputValues() {
    const userIdInput = document.getElementById('userIdInput') as HTMLInputElement;
    const sessionIdInput = document.getElementById('sessionIdInput') as HTMLInputElement;
    const userId = userIdInput.value;
    const sessionId = sessionIdInput.value;
    const inputValues = {
        uId: userId,
        sId: sessionId
    }
    return inputValues;
}

function validateForms() {
    const inputValues = getInputValues();
    if (!inputValues.uId || !inputValues.sId) {
        alert("Empty fields are not acceptable!")
        return null;
    }
    console.log(`User id: ${inputValues.uId}, session id: ${inputValues.sId}`);
    return inputValues
}

function saveInSessionStorage(credentials: CredentialsResponse) {
    sessionStorage.setItem('userId', credentials.userId);
    sessionStorage.setItem('sessionId', credentials.sessionId);
    console.log(`Saved user id: ${credentials.userId} and session id: ${credentials.sessionId} in session storage`);
}

function onJoinButtonClick() {
    console.log("Join button clicked");
    const inputValues = validateForms();
    if (!inputValues) return;
    postClient(inputValues.uId, inputValues.sId)
        .then((credentials) => {
            saveInSessionStorage(credentials)
            loadPage('gamepage.html')
        })
        .catch((e) => { console.error(e) });
}

function onHostButtonClick() {
    console.log("Host button clicked");
    const inputValues = validateForms();
    if (!inputValues) return;
    postSession(inputValues.uId, inputValues.sId)
        .then((credentials) => {
            saveInSessionStorage(credentials)
            loadPage('gamepage.html')
        })
        .catch((e) => { console.error(e) });
}


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
