package com;

public class Tool {
    
    public static boolean isNumber(char c){
        if ('0' <= c && c <= '9')
            return true;
        return false;
    }
    
    public static boolean isLowercaseAlphabet(char c){
        if ('a' <= c && c <= 'z')
            return true;
        return false;
    }
    
    public static boolean isUppercaseAlphabet(char c){
        if ('A' <= c && c <= 'Z')
            return true;
        return false;
    }
    
    public static boolean isSameKindToken(char c1, char c2){
        if (isUppercaseAlphabet(c1) && isUppercaseAlphabet(c2)){
            return true;
        }
        if (isLowercaseAlphabet(c1) && isLowercaseAlphabet(c2)){
            return true;
        }
        if (isNumber(c1) && isNumber(c2)){
            return true;
        }
        return false;
    }
}
