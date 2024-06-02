import { sendChatMessage } from "./game-ws-client.js";
import { ChatMessagePayload } from "./model/ws-message.js";

let messages: ChatMessagePayload[] = [];
let chatDiv: HTMLElement;
let userId: string;

export async function initChat() {
    chatDiv = document.getElementById('chatHistory')!;
    document.getElementById('sendButton')?.addEventListener('click', onChatButtonClick);
    userId = sessionStorage.getItem('userId')!;
}

function onChatButtonClick() {
    const chatInput = document.getElementById('chatInput') as HTMLInputElement;
    const message = chatInput.value.trim();
    if (message) {
        sendMessage(message);
        chatInput.value = '';
    } else {
        alert("Message cannot be empty");
    }
}

export function addMessage(m: ChatMessagePayload) {
    messages.push(m);
    const messageElement = document.createElement('div');
    messageElement.classList.add('chat-message');

    if (m.sender === userId) {
        messageElement.classList.add('user');
    } else if (m.sender === 'SERVER') {
        messageElement.classList.add('server');
    } else {
        messageElement.classList.add('other');
    }

    messageElement.textContent = `${m.sender}: ${m.content}`;
    chatDiv.appendChild(messageElement);
    chatDiv.scrollTop = chatDiv.scrollHeight;
}

function sendMessage(m: string) {
    const message: ChatMessagePayload = {
        sender: userId!,
        content: m
    };
    sendChatMessage(message);
}
