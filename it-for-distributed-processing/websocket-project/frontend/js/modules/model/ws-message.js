export var MessageType;
(function (MessageType) {
    MessageType[MessageType["HANDSHAKE"] = 0] = "HANDSHAKE";
    MessageType[MessageType["DRAWING"] = 1] = "DRAWING";
    MessageType[MessageType["CHAT_MESSAGE"] = 2] = "CHAT_MESSAGE";
    MessageType[MessageType["GAME_TIMER"] = 3] = "GAME_TIMER";
    MessageType[MessageType["NEW_WORD"] = 4] = "NEW_WORD";
    MessageType[MessageType["GAME_SCORE"] = 5] = "GAME_SCORE";
    MessageType[MessageType["CLEAR_BOARD"] = 6] = "CLEAR_BOARD";
})(MessageType || (MessageType = {}));
