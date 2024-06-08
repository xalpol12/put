import { toggleDrawing } from "./canvas.js";
import { toggleChat } from "./chat.js";
import { GameTimerPayload, NewWordPayload } from "./model/ws-message.js";

let timerElement: HTMLElement;
let wordElement: HTMLElement;
let userId: string;
let isInitialPhase = true;

export async function initTimerDisplay(id: string) {
    timerElement = document.getElementById('timer')!;
    wordElement = document.getElementById('word')!;
    userId = id;

}

export function updateTimer(m: GameTimerPayload) {
    timerElement.textContent = `Time left: ${m.time}s`;
    if (m.time === 0 && !isInitialPhase) {
        toggleChat(true);
        toggleDrawing(false);
        wordElement.textContent = ``;
        wordElement.style.display = 'hidden';
    }
}

export function updateWord(m: NewWordPayload) {
    if (m.newDrawer === userId) {
        toggleChat(false);
        toggleDrawing(true);
        wordElement.textContent = `Word: ${m.newWord}`;
        wordElement.style.display = 'block';
        if (isInitialPhase) isInitialPhase = false;
    } else {
        toggleChat(true);
        toggleDrawing(false);
        wordElement.textContent = ``;
        wordElement.style.display = 'hidden';
    }
}
