export function decodeProtobufMessage(binaryMessage) {
    const root = protobuf.loadSync("./messages.proto");
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
    return decodedObject;
}
export function encodeProtobufMessage(message) {
    const root = protobuf.loadSync("./messages.proto");
    const CustomMessage = root.lookupType("com.xalpol12.websocket.CustomMessage");
    const errMsg = CustomMessage.verify(message);
    if (errMsg) {
        throw new Error(`Protobuf verification failed: ${errMsg}`);
    }
    const encodedMessage = CustomMessage.encode(message).finish();
    return encodedMessage;
}
