export var MessageType;
(function (MessageType) {
    MessageType["HANDSHAKE"] = "HANDSHAKE";
    MessageType["DRAWING"] = "DRAWING";
    MessageType["CHAT_MESSAGE"] = "CHAT_MESSAGE";
    MessageType["GAME_DATA"] = "GAME_DATA";
    MessageType["GAME_TIMER"] = "GAME_TIMER";
})(MessageType || (MessageType = {}));
