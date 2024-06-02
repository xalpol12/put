export var MessageType;
(function (MessageType) {
    MessageType[MessageType["HANDSHAKE"] = 0] = "HANDSHAKE";
    MessageType[MessageType["DRAWING"] = 1] = "DRAWING";
    MessageType[MessageType["CHAT_MESSAGE"] = 2] = "CHAT_MESSAGE";
    MessageType[MessageType["GAME_DATA"] = 3] = "GAME_DATA";
    MessageType[MessageType["GAME_TIMER"] = 4] = "GAME_TIMER";
})(MessageType || (MessageType = {}));
