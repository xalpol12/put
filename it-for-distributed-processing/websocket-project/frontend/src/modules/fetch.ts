import { CredentialsResponse } from "./model/credentials-response.js";

export async function postSession(userId: string, sessionId: string): Promise<CredentialsResponse> {
    const b = { userId: userId, sessionId: sessionId };
    const response = await fetch('http://localhost:8081/api/v1/sessions', {
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
}

export async function postClient(userId: string, sessionId: string): Promise<CredentialsResponse> {
    const b = { userId: userId }
    const response = await fetch(`http://localhost:8081/api/v1/sessions/${sessionId}`, {
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
}
