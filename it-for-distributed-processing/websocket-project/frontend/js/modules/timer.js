var __awaiter = (this && this.__awaiter) || function (thisArg, _arguments, P, generator) {
    function adopt(value) { return value instanceof P ? value : new P(function (resolve) { resolve(value); }); }
    return new (P || (P = Promise))(function (resolve, reject) {
        function fulfilled(value) { try { step(generator.next(value)); } catch (e) { reject(e); } }
        function rejected(value) { try { step(generator["throw"](value)); } catch (e) { reject(e); } }
        function step(result) { result.done ? resolve(result.value) : adopt(result.value).then(fulfilled, rejected); }
        step((generator = generator.apply(thisArg, _arguments || [])).next());
    });
};
import { toggleDrawing } from "./canvas.js";
import { toggleChat } from "./chat.js";
let timerElement;
let wordElement;
let userId;
let isInitialPhase = true;
export function initTimerDisplay(id) {
    return __awaiter(this, void 0, void 0, function* () {
        timerElement = document.getElementById('timer');
        wordElement = document.getElementById('word');
        userId = id;
    });
}
export function updateTimer(m) {
    timerElement.textContent = `Time left: ${m.time}s`;
    if (m.time === 0 && !isInitialPhase) {
        toggleChat(true);
        toggleDrawing(false);
        wordElement.textContent = ``;
        wordElement.style.display = 'hidden';
    }
}
export function updateWord(m) {
    if (m.newDrawer === userId) {
        toggleChat(false);
        toggleDrawing(true);
        wordElement.textContent = `Word: ${m.newWord}`;
        wordElement.style.display = 'block';
        if (isInitialPhase)
            isInitialPhase = false;
    }
    else {
        toggleChat(true);
        toggleDrawing(false);
        wordElement.textContent = ``;
        wordElement.style.display = 'hidden';
    }
}
