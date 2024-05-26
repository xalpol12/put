package com.xalpol12.wsserver.model.internal;

import java.util.Random;

public class WordGenerator {
    private static final String[] WORD_LIST;
    private static final Random random;

    static {
        WORD_LIST = new String[] {
                "Word 1",
                "Word 2"
        };
        random = new Random();
    }

    public static String getNewWord() {
        int index = random.nextInt(0, WORD_LIST.length);
        return WORD_LIST[index];
    }
}
