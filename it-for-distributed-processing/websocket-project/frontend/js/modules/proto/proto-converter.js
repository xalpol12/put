var __awaiter = (this && this.__awaiter) || function (thisArg, _arguments, P, generator) {
    function adopt(value) { return value instanceof P ? value : new P(function (resolve) { resolve(value); }); }
    return new (P || (P = Promise))(function (resolve, reject) {
        function fulfilled(value) { try { step(generator.next(value)); } catch (e) { reject(e); } }
        function rejected(value) { try { step(generator["throw"](value)); } catch (e) { reject(e); } }
        function step(result) { result.done ? resolve(result.value) : adopt(result.value).then(fulfilled, rejected); }
        step((generator = generator.apply(thisArg, _arguments || [])).next());
    });
};
import { MessageType } from "../model/ws-message.js";
export function decodeProtobufMessage(binaryMessage) {
    return __awaiter(this, void 0, void 0, function* () {
        const root = yield protobuf.load("./js/modules/proto/messages.proto");
        const CustomMessage = root.lookupType("com.xalpol12.websocket.CustomMessage");
        const decodedMessage = CustomMessage.decode(binaryMessage);
        const decodedObject = CustomMessage.toObject(decodedMessage, {
            defaults: true,
            enums: String,
            longs: String,
            bytes: String,
            arrays: true,
            objects: true,
            oneofs: true
        });
        console.log(`Decoded message: ${JSON.stringify(decodedObject)}`);
        return decodedObject;
    });
}
export function encodeProtobufMessage(message) {
    return __awaiter(this, void 0, void 0, function* () {
        console.log(`CustomMessage type: ${message.messageType}; payload: ${JSON.stringify(message.payload)}`);
        const root = yield protobuf.load("./js/modules/proto/messages.proto");
        const CustomMessageType = root.lookupType("com.xalpol12.websocket.CustomMessage");
        const messageObject = CustomMessageType.create({
            messageType: message.messageType
        });
        if (message.messageType == MessageType.HANDSHAKE) {
            // const HandshakeMesssageType = root.lookupType("com.xalpol12.websocket.HandshakePayload");
            // const payload = HandshakeMesssageType.create(message.payload);
            // const encodedMessage = HandshakeMesssageType.encode(payload).finish();
            // console.log('Encoded message bytes:', Array.from(encodedMessage));
            messageObject.handshakePayload = message.payload;
        }
        const errMsg = CustomMessageType.verify(messageObject);
        if (errMsg) {
            throw new Error(`Protobuf verification failed: ${errMsg}`);
        }
        // Encode the message
        const encodedMessage = CustomMessageType.encode(messageObject).finish();
        console.log('Encoded message bytes:', Array.from(encodedMessage));
        return encodedMessage;
    });
}
// Helper function to get the correct oneof payload name
function getOneofPayloadName(messageType) {
    switch (messageType) {
        case MessageType.HANDSHAKE:
            return "handshakePayload";
        case MessageType.DRAWING:
            return "drawingPayload";
        case MessageType.CHAT_MESSAGE:
            return "chatMessagePayload";
        case MessageType.GAME_TIMER:
            return "gameTimerPayload";
        case MessageType.NEW_WORD:
            return "newWordPayload";
        case MessageType.GAME_SCORE:
            return "gameScorePayload";
        case MessageType.CLEAR_BOARD:
            return "clearBoardPayload";
        default:
            throw new Error(`Unsupported message type: ${messageType}`);
    }
}
