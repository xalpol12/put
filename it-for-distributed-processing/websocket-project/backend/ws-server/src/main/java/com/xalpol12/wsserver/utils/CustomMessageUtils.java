package com.xalpol12.wsserver.utils;

import com.xalpol12.wsserver.protos.*;

public class CustomMessageUtils {

    public static CustomMessage createChatMessage(ChatMessagePayload c) {
        return CustomMessage.newBuilder()
                .setMessageType(MessageType.CHAT_MESSAGE)
                .setChatMessagePayload(c)
                .build();
    }

    public static CustomMessage createDrawingMessage(DrawingPayload d) {
        return CustomMessage.newBuilder()
                .setMessageType(MessageType.DRAWING)
                .setDrawingPayload(d)
                .build();
    }

    public static CustomMessage createGameTimerMessage(GameTimerPayload gt) {
        return CustomMessage.newBuilder()
                .setMessageType(MessageType.GAME_TIMER)
                .setGameTimerPayload(gt)
                .build();
    }

    public static CustomMessage createNewWordMessage(NewWordPayload nw) {
        return CustomMessage.newBuilder()
                .setMessageType(MessageType.NEW_WORD)
                .setNewWordPayload(nw)
                .build();
    }

    public static CustomMessage createGameScoreMessage(GameScorePayload gs) {
        return CustomMessage.newBuilder()
                .setMessageType(MessageType.GAME_SCORE)
                .setGameScorePayload(gs)
                .build();
    }

    public static CustomMessage createClearBoardMessage(ClearBoardPayload cb) {
        return CustomMessage.newBuilder()
                .setMessageType(MessageType.CLEAR_BOARD)
                .setClearBoardPayload(cb)
                .build();
    }
}
