package com.o4codes.clipshare.utils;

public class Helper {
    public static boolean checkStringForLetters(String string) {
        char ch;
        boolean letterFlag = false;
        for (int i = 0; i < string.length(); i++) {
            ch = string.charAt( i );
            if (Character.isLetter( ch )) {
                letterFlag = true;
                break;
            }
        }
        return letterFlag;
    }
}
