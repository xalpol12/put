import { GameScorePayload } from "./model/ws-message.js";

let scoreList: HTMLElement;
let score: GameScorePayload[] = [];

export async function initScore() {
    scoreList = document.getElementById('scoreList')!;
}

export function addScoreEntry(m: GameScorePayload) {
    score.push(m);
    const listItem = document.createElement('li');
    listItem.textContent = `${m.userId}: ${m.score}`;
    scoreList.appendChild(listItem);
}
