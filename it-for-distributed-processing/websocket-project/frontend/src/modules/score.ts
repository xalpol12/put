import { GameScorePayload } from "./model/ws-message.js";

let scoreList: HTMLElement;
let scores: GameScorePayload[] = [];

export async function initScore() {
    scoreList = document.getElementById('scoreList')!;
}

export function addScoreEntry(m: GameScorePayload) {
    const existingScore = scores.find(score => score.userId === m.userId);

    if (existingScore) {
        existingScore.score = m.score;
    } else {
        scores.push(m);
    }

    scores.sort((a, b) => b.score - a.score);

    scoreList.innerHTML = '';

    scores.forEach(score => {
        const listItem = document.createElement('li');

        const userIdSpan = document.createElement('span');
        userIdSpan.className = 'user-id';
        userIdSpan.textContent = score.userId;

        const userScoreSpan = document.createElement('span');
        userScoreSpan.className = 'user-score';
        userScoreSpan.textContent = score.score.toString();

        listItem.appendChild(userIdSpan);
        listItem.appendChild(userScoreSpan);
        scoreList.appendChild(listItem);
    });
}
