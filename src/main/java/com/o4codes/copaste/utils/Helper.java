package com.o4codes.copaste.utils;

public class Helper {

    public static boolean checkStringForLetters(String s){
        return s.matches(".*[a-zA-Z]+.*");
    }

    public static boolean checkStringForNumbers(String string) {
        return string.matches("[0-9]+");
    }

    public static String getIPAddressFromURL(String url){
        String[] urlParts = url.split(":");
        return urlParts[0];
    }

    public static String getPortFromURL(String url){
        String[] urlParts = url.split(":");
        return urlParts[1];
    }

}
