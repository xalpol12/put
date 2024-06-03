import { GameTimerPayload, NewWordPayload } from "./model/ws-message.js";

let timerElement: HTMLElement;
let wordElement: HTMLElement;
let userId: string;

export async function initTimerDisplay(id: string) {
    timerElement = document.getElementById('timer')!;
    wordElement = document.getElementById('word')!;
    userId = id;

}

export function updateTimer(m: GameTimerPayload) {
    timerElement.textContent = `Time left: ${m.time}s`;
}

export function updateWord(m: NewWordPayload) {
    if (m.newDrawer === userId) {
        wordElement.textContent = `Word: ${m.newWord}`;
        wordElement.style.display = 'block';
    }
}
