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
(function (MessageType) {
    const stringToEnumMap = {
        "HANDSHAKE": MessageType.HANDSHAKE,
        "DRAWING": MessageType.DRAWING,
        "CHAT_MESSAGE": MessageType.CHAT_MESSAGE,
        "GAME_TIMER": MessageType.GAME_TIMER,
        "NEW_WORD": MessageType.NEW_WORD,
        "GAME_SCORE": MessageType.GAME_SCORE,
        "CLEAR_BOARD": MessageType.CLEAR_BOARD
    };
    function fromString(key) {
        return stringToEnumMap[key];
    }
    MessageType.fromString = fromString;
})(MessageType || (MessageType = {}));
