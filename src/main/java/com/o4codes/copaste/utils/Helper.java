package com.o4codes.copaste.utils;

import com.o4codes.copaste.services.ClipBoardService;

import java.awt.*;

import static java.lang.System.out;

public class Helper {

    public static boolean checkStringForLetters(String s){
        return s.matches(".*[a-zA-Z]+.*");
    }

    public static boolean checkStringForNumbers(String string) {
        return string.matches("[0-9]+");
    }



}
