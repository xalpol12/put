var __awaiter = (this && this.__awaiter) || function (thisArg, _arguments, P, generator) {
    function adopt(value) { return value instanceof P ? value : new P(function (resolve) { resolve(value); }); }
    return new (P || (P = Promise))(function (resolve, reject) {
        function fulfilled(value) { try { step(generator.next(value)); } catch (e) { reject(e); } }
        function rejected(value) { try { step(generator["throw"](value)); } catch (e) { reject(e); } }
        function step(result) { result.done ? resolve(result.value) : adopt(result.value).then(fulfilled, rejected); }
        step((generator = generator.apply(thisArg, _arguments || [])).next());
    });
};
import { sendChatMessage } from "./game-ws-client.js";
let messages = [];
let chatDiv;
let userId;
let inputContainer;
let chatInput;
let sendButton;
export function initChat() {
    return __awaiter(this, void 0, void 0, function* () {
        chatDiv = document.getElementById('chatHistory');
        inputContainer = document.querySelector('.input-container');
        chatInput = document.getElementById('chatInput');
        sendButton = document.getElementById('sendButton');
        sendButton.addEventListener('click', onChatButtonClick);
        userId = sessionStorage.getItem('userId');
    });
}
function onChatButtonClick() {
    const chatInput = document.getElementById('chatInput');
    const message = chatInput.value.trim();
    if (message) {
        sendMessage(message);
        chatInput.value = '';
    }
    else {
        alert("Message cannot be empty");
    }
}
export function addMessage(m) {
    messages.push(m);
    const messageElement = document.createElement('div');
    messageElement.classList.add('chat-message');
    if (m.sender === userId) {
        messageElement.classList.add('user');
    }
    else if (m.sender === 'SERVER') {
        messageElement.classList.add('server');
        toggleChat(false);
    }
    else {
        messageElement.classList.add('other');
    }
    messageElement.textContent = `${m.sender}: ${m.content}`;
    chatDiv.appendChild(messageElement);
    chatDiv.scrollTop = chatDiv.scrollHeight;
}
function sendMessage(m) {
    const message = {
        sender: userId,
        content: m
    };
    sendChatMessage(message);
}
export function toggleChat(enable) {
    chatInput.disabled = !enable;
    sendButton.disabled = !enable;
    inputContainer.style.display = enable ? 'block' : 'none';
}
