var __awaiter = (this && this.__awaiter) || function (thisArg, _arguments, P, generator) {
    function adopt(value) { return value instanceof P ? value : new P(function (resolve) { resolve(value); }); }
    return new (P || (P = Promise))(function (resolve, reject) {
        function fulfilled(value) { try { step(generator.next(value)); } catch (e) { reject(e); } }
        function rejected(value) { try { step(generator["throw"](value)); } catch (e) { reject(e); } }
        function step(result) { result.done ? resolve(result.value) : adopt(result.value).then(fulfilled, rejected); }
        step((generator = generator.apply(thisArg, _arguments || [])).next());
    });
};
export function postSession(userId, sessionId) {
    return __awaiter(this, void 0, void 0, function* () {
        const b = { userId: userId, sessionId: sessionId };
        const response = yield fetch('http://localhost:8081/api/v1/sessions', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Accept': 'application/json',
            },
            body: JSON.stringify(b),
        });
        if (!response.ok) {
            throw new Error(`Session ${sessionId} already exists!`);
        }
        return response.json();
    });
}
export function postClient(userId, sessionId) {
    return __awaiter(this, void 0, void 0, function* () {
        const b = { userId: userId };
        const response = yield fetch(`http://localhost:8081/api/v1/sessions/${sessionId}`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Accept': 'application/json',
            },
            body: JSON.stringify(b),
        });
        if (!response.ok) {
            throw new Error(`Session ${sessionId} does not exist!`);
        }
        return response.json();
    });
}
