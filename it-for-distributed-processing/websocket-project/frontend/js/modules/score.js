var __awaiter = (this && this.__awaiter) || function (thisArg, _arguments, P, generator) {
    function adopt(value) { return value instanceof P ? value : new P(function (resolve) { resolve(value); }); }
    return new (P || (P = Promise))(function (resolve, reject) {
        function fulfilled(value) { try { step(generator.next(value)); } catch (e) { reject(e); } }
        function rejected(value) { try { step(generator["throw"](value)); } catch (e) { reject(e); } }
        function step(result) { result.done ? resolve(result.value) : adopt(result.value).then(fulfilled, rejected); }
        step((generator = generator.apply(thisArg, _arguments || [])).next());
    });
};
let scoreList;
let scores = [];
export function initScore() {
    return __awaiter(this, void 0, void 0, function* () {
        scoreList = document.getElementById('scoreList');
    });
}
export function addScoreEntry(m) {
    const existingScore = scores.find(score => score.userId === m.userId);
    if (existingScore) {
        existingScore.score = m.score;
    }
    else {
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
