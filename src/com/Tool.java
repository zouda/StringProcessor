package com;

import java.io.*;

/*
 * Tool methods
 */
public class Tool {
    public static BufferedWriter bw;
    
    public static int Max_Int(int x, int y){
        if (x > y)
            return x;
        else
            return y;
    }
    
    public static double Max_Double(double x, double y) {
        if (x > y)
            return x;
        else
            return y;
    }
    
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
        try {
            bw.write(s);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void println(String s){
        System.out.println(s);
    }

    public static void print(int num) {
        System.out.print(num);
        try {
            bw.write(String.valueOf(num));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void startFileWriting(){
        try {
            FileWriter fw = new FileWriter(new File(Global.OUTPUT_FILE_PATH),false);
            fw.close();
            bw = new BufferedWriter(new FileWriter(new File(Global.OUTPUT_FILE_PATH),true));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void endFileWriting(){
        try {
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
