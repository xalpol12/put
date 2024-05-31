import { CredentialsResponse } from "./model/credentials-response.js";

export async function postSession(userId: string, sessionId: string): Promise<CredentialsResponse> {
    const response = await fetch('http://localhost:8080/api/v1/sessions', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({ userId, sessionId }),
    });

    if (!response.ok) {
        throw new Error('Error while calling POST /sessions');
    }

    return response.json();
}

export async function postClient(userId: string, sessionId: string): Promise<CredentialsResponse> {
    const response = await fetch(`http://localhost:8080/api/v1/sessions/${sessionId}`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({ userId }),
    });

    if (!response.ok) {
        throw new Error(`Error while calling POST /sessions/${sessionId}`);
    }

    return response.json();
}
