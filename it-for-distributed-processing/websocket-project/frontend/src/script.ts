import { createCanvas } from "./modules/canvas.js";
import { postClient, postSession } from "./modules/fetch.js";
import { CredentialsResponse } from "./modules/model/credentials-response.js";

document.addEventListener('DOMContentLoaded', () => {
    if (isSessionStorageEmpty()) {
        loadPage('homepage.html');
    } else {
        loadPage('gamepage.html');
    }
})

function isSessionStorageEmpty(): boolean {
    return !sessionStorage.getItem('userId') || !sessionStorage.getItem('sessionId');
}

async function loadPage(page: any) {
    const response = await fetch(`./html/${page}`);
    const content = await response.text();
    document.getElementById('container')!.innerHTML = content;

    if (page === "homepage.html") {
        document.getElementById('joinButton')?.addEventListener('click', onJoinButtonClick);
        document.getElementById('hostButton')?.addEventListener('click', onHostButtonClick);
    }
    else if (page === "gamepage.html") {
        const userId = sessionStorage.getItem('userId');
        const sessionId = sessionStorage.getItem('sessionId');
        console.log("Invoked createCanvas");
        createCanvas(userId!, sessionId!);
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
