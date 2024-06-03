package com.xalpol12.wsserver.utils;

import java.util.Objects;
import java.util.Random;

public class WordPool {
    private static String[] words;

    static {
        words = new String[]{
                "pies",
                "kot",
                "patelnia",
                "koń",
                "okulary",
                "gitara",
                "ptak",
                "telefon",
        };
    }

    public static String getNextWord(String previousWord) {
        String word;
        do {
            int index = new Random().nextInt(words.length);
            word = words[index];
        }
        while (Objects.equals(word, previousWord));
        return word;
    }
}
