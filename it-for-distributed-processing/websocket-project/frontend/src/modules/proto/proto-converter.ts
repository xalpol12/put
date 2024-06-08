import { CustomMessage, MessageType } from "../model/ws-message.js";

export async function decodeProtobufMessage(binaryMessage: Uint8Array): Promise<any> {
    const root = await protobuf.load("./js/modules/proto/messages.proto");
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
}

export async function encodeProtobufMessage(message: CustomMessage): Promise<Uint8Array> {
    console.log(`CustomMessage type: ${message.messageType}; payload: ${JSON.stringify(message.payload)}`);

    const root = await protobuf.load("./js/modules/proto/messages.proto");
    const CustomMessageType = root.lookupType("com.xalpol12.websocket.CustomMessage");

    // Ensure payload is correctly constructed
    const messageObject: any = {
        messageType: message.messageType,
    };

    // Assign the appropriate payload field based on messageType
    const payloadField = getOneofPayloadName(message.messageType);
    messageObject[payloadField] = message.payload;

    // Verify the message structure
    const errMsg = CustomMessageType.verify(messageObject);
    if (errMsg) {
        throw new Error(`Protobuf verification failed: ${errMsg}`);
    }

    // Encode the message
    const encodedMessage = CustomMessageType.encode(messageObject).finish();
    console.log('Encoded message bytes:', Array.from(encodedMessage));

    return encodedMessage;
}

// Helper function to get the correct oneof payload name
function getOneofPayloadName(messageType: MessageType): string {
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

