var __awaiter = (this && this.__awaiter) || function (thisArg, _arguments, P, generator) {
    function adopt(value) { return value instanceof P ? value : new P(function (resolve) { resolve(value); }); }
    return new (P || (P = Promise))(function (resolve, reject) {
        function fulfilled(value) { try { step(generator.next(value)); } catch (e) { reject(e); } }
        function rejected(value) { try { step(generator["throw"](value)); } catch (e) { reject(e); } }
        function step(result) { result.done ? resolve(result.value) : adopt(result.value).then(fulfilled, rejected); }
        step((generator = generator.apply(thisArg, _arguments || [])).next());
    });
};
import { createCanvas } from "./modules/canvas.js";
import { initChat } from "./modules/chat.js";
import { postClient, postSession } from "./modules/fetch.js";
import { initScore } from "./modules/score.js";
import { initTimerDisplay } from "./modules/timer.js";
document.addEventListener('DOMContentLoaded', () => {
    if (isSessionStorageEmpty()) {
        loadPage('homepage.html');
    }
    else {
        loadPage('gamepage.html');
    }
});
function isSessionStorageEmpty() {
    return !sessionStorage.getItem('userId') || !sessionStorage.getItem('sessionId');
}
function loadPage(page) {
    var _a, _b;
    return __awaiter(this, void 0, void 0, function* () {
        const response = yield fetch(`./html/${page}`);
        const content = yield response.text();
        document.getElementById('container').innerHTML = content;
        if (page === "homepage.html") {
            (_a = document.getElementById('joinButton')) === null || _a === void 0 ? void 0 : _a.addEventListener('click', onJoinButtonClick);
            (_b = document.getElementById('hostButton')) === null || _b === void 0 ? void 0 : _b.addEventListener('click', onHostButtonClick);
        }
        else if (page === "gamepage.html") {
            const userId = sessionStorage.getItem('userId');
            const sessionId = sessionStorage.getItem('sessionId');
            console.log("Invoked createCanvas");
            createCanvas(userId, sessionId);
            initChat();
            initScore();
            initTimerDisplay(userId);
        }
        console.log(`${page} loaded`);
    });
}
function getInputValues() {
    const userIdInput = document.getElementById('userIdInput');
    const sessionIdInput = document.getElementById('sessionIdInput');
    const userId = userIdInput.value;
    const sessionId = sessionIdInput.value;
    const inputValues = {
        uId: userId,
        sId: sessionId
    };
    return inputValues;
}
function validateForms() {
    const inputValues = getInputValues();
    if (!inputValues.uId || !inputValues.sId) {
        alert("Empty fields are not acceptable!");
        return null;
    }
    console.log(`User id: ${inputValues.uId}, session id: ${inputValues.sId}`);
    return inputValues;
}
function saveInSessionStorage(credentials) {
    sessionStorage.setItem('userId', credentials.userId);
    sessionStorage.setItem('sessionId', credentials.sessionId);
    console.log(`Saved user id: ${credentials.userId} and session id: ${credentials.sessionId} in session storage`);
}
function onJoinButtonClick() {
    console.log("Join button clicked");
    const inputValues = validateForms();
    if (!inputValues)
        return;
    postClient(inputValues.uId, inputValues.sId)
        .then((credentials) => {
        saveInSessionStorage(credentials);
        loadPage('gamepage.html');
    })
        .catch((e) => { console.error(e); });
}
function onHostButtonClick() {
    console.log("Host button clicked");
    const inputValues = validateForms();
    if (!inputValues)
        return;
    postSession(inputValues.uId, inputValues.sId)
        .then((credentials) => {
        saveInSessionStorage(credentials);
        loadPage('gamepage.html');
    })
        .catch((e) => { console.error(e); });
}
