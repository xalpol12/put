// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: messages.proto

package com.xalpol12.wsserver.protos;

/**
 * Protobuf enum {@code com.xalpol12.websocket.MessageType}
 */
public enum MessageType
    implements com.google.protobuf.ProtocolMessageEnum {
  /**
   * <code>HANDSHAKE = 0;</code>
   */
  HANDSHAKE(0),
  /**
   * <code>DRAWING = 1;</code>
   */
  DRAWING(1),
  /**
   * <code>CHAT_MESSAGE = 2;</code>
   */
  CHAT_MESSAGE(2),
  /**
   * <code>GAME_TIMER = 3;</code>
   */
  GAME_TIMER(3),
  /**
   * <code>NEW_WORD = 4;</code>
   */
  NEW_WORD(4),
  /**
   * <code>GAME_SCORE = 5;</code>
   */
  GAME_SCORE(5),
  /**
   * <code>CLEAR_BOARD = 6;</code>
   */
  CLEAR_BOARD(6),
  UNRECOGNIZED(-1),
  ;

  /**
   * <code>HANDSHAKE = 0;</code>
   */
  public static final int HANDSHAKE_VALUE = 0;
  /**
   * <code>DRAWING = 1;</code>
   */
  public static final int DRAWING_VALUE = 1;
  /**
   * <code>CHAT_MESSAGE = 2;</code>
   */
  public static final int CHAT_MESSAGE_VALUE = 2;
  /**
   * <code>GAME_TIMER = 3;</code>
   */
  public static final int GAME_TIMER_VALUE = 3;
  /**
   * <code>NEW_WORD = 4;</code>
   */
  public static final int NEW_WORD_VALUE = 4;
  /**
   * <code>GAME_SCORE = 5;</code>
   */
  public static final int GAME_SCORE_VALUE = 5;
  /**
   * <code>CLEAR_BOARD = 6;</code>
   */
  public static final int CLEAR_BOARD_VALUE = 6;


  public final int getNumber() {
    if (this == UNRECOGNIZED) {
      throw new java.lang.IllegalArgumentException(
          "Can't get the number of an unknown enum value.");
    }
    return value;
  }

  /**
   * @param value The numeric wire value of the corresponding enum entry.
   * @return The enum associated with the given numeric wire value.
   * @deprecated Use {@link #forNumber(int)} instead.
   */
  @java.lang.Deprecated
  public static MessageType valueOf(int value) {
    return forNumber(value);
  }

  /**
   * @param value The numeric wire value of the corresponding enum entry.
   * @return The enum associated with the given numeric wire value.
   */
  public static MessageType forNumber(int value) {
    switch (value) {
      case 0: return HANDSHAKE;
      case 1: return DRAWING;
      case 2: return CHAT_MESSAGE;
      case 3: return GAME_TIMER;
      case 4: return NEW_WORD;
      case 5: return GAME_SCORE;
      case 6: return CLEAR_BOARD;
      default: return null;
    }
  }

  public static com.google.protobuf.Internal.EnumLiteMap<MessageType>
      internalGetValueMap() {
    return internalValueMap;
  }
  private static final com.google.protobuf.Internal.EnumLiteMap<
      MessageType> internalValueMap =
        new com.google.protobuf.Internal.EnumLiteMap<MessageType>() {
          public MessageType findValueByNumber(int number) {
            return MessageType.forNumber(number);
          }
        };

  public final com.google.protobuf.Descriptors.EnumValueDescriptor
      getValueDescriptor() {
    if (this == UNRECOGNIZED) {
      throw new java.lang.IllegalStateException(
          "Can't get the descriptor of an unrecognized enum value.");
    }
    return getDescriptor().getValues().get(ordinal());
  }
  public final com.google.protobuf.Descriptors.EnumDescriptor
      getDescriptorForType() {
    return getDescriptor();
  }
  public static final com.google.protobuf.Descriptors.EnumDescriptor
      getDescriptor() {
    return com.xalpol12.wsserver.protos.CustomMessageProtos.getDescriptor().getEnumTypes().get(0);
  }

  private static final MessageType[] VALUES = values();

  public static MessageType valueOf(
      com.google.protobuf.Descriptors.EnumValueDescriptor desc) {
    if (desc.getType() != getDescriptor()) {
      throw new java.lang.IllegalArgumentException(
        "EnumValueDescriptor is not for this type.");
    }
    if (desc.getIndex() == -1) {
      return UNRECOGNIZED;
    }
    return VALUES[desc.getIndex()];
  }

  private final int value;

  private MessageType(int value) {
    this.value = value;
  }

  // @@protoc_insertion_point(enum_scope:com.xalpol12.websocket.MessageType)
}

