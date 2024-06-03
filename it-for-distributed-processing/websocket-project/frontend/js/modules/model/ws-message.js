export var MessageType;
(function (MessageType) {
    MessageType["HANDSHAKE"] = "HANDSHAKE";
    MessageType["DRAWING"] = "DRAWING";
    MessageType["CHAT_MESSAGE"] = "CHAT_MESSAGE";
    MessageType["GAME_TIMER"] = "GAME_TIMER";
    MessageType["NEW_WORD"] = "NEW_WORD";
    MessageType["GAME_SCORE"] = "GAME_SCORE"; // from server
})(MessageType || (MessageType = {}));
