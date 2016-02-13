package com;

/*
 * Tool methods
 */
public class Tool {
    
    public static boolean isNumber(char c){
        if ('0' <= c && c <= '9')
            return true;
        return false;
    }
    
    public static boolean isNumber(String s){
        for (int i = 0; i < s.length(); i++){
            if (!isNumber(s.charAt(i)))
                return false;
        }
        return true;
    }
    
    public static boolean isLowercaseAlphabet(char c){
        if ('a' <= c && c <= 'z')
            return true;
        return false;
    }
    
    public static boolean isLowercaseAlphabet(String s){
        for (int i = 0; i < s.length(); i++){
            if (!isLowercaseAlphabet(s.charAt(i)))
                return false;
        }
        return true;
    }
    
    public static boolean isUppercaseAlphabet(char c){
        if ('A' <= c && c <= 'Z')
            return true;
        return false;
    }
    
    public static boolean isUppercaseAlphabet(String s){
        for (int i = 0; i < s.length(); i++){
            if (!isUppercaseAlphabet(s.charAt(i)))
                return false;
        }
        return true;
    }
    
    public static boolean isAlphabet(char c){
        if ('A' <= c && c <= 'Z' || 'a' <= c && c <= 'z')
            return true;
        return false;
    }
    
    public static boolean isAlphabet(String s){
        for (int i = 0; i < s.length(); i++){
            if (!isAlphabet(s.charAt(i)))
                return false;
        }
        return true;
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

    public static void error(String s) {
        System.out.println(s);
    }
    
    public static void print(String s){
        System.out.print(s);
    }

    public static void print(int num) {
        System.out.print(num);
    }
}
