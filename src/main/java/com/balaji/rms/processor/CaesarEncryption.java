package com.balaji.rms.processor;

import static java.lang.Character.isLowerCase;
import static java.lang.Character.isUpperCase;

public class CaesarEncryption {

    private static final int ALPHA_COUNT = 26;
    private static final int ROTATE_BY = 2;

    public String encrypt(String message, int rotateBy) {
        rotateBy %= ALPHA_COUNT;
        char[] seq = message.toCharArray();
        rotate(seq, rotateBy);
        return new String(seq);
    }

    private void rotate(char[] seq, int rotateBy) {
        for (int i = 0; i < seq.length; i++) {
            if (isLowerCase(seq[i])) {
                seq[i] = rotateChar(seq[i], rotateBy, 'a', 'z');
            } else if (isUpperCase(seq[i])) {
                seq[i] = rotateChar(seq[i], rotateBy, 'A', 'Z');
            }
        }
    }

    private char rotateChar(char c, int rotateBy, char firstChar, char lastChar) {
        c += rotateBy;
        if (c < firstChar) {
            return (char) (c + ALPHA_COUNT);
        }
        if (c > lastChar) {
            return (char) (c - ALPHA_COUNT);
        }
        return c;
    }
}
