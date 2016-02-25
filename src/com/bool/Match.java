package com.bool;

import com.regex.Regex;

public class Match {
    private Regex r;
    private int c;
    
    public Match(Regex r, int c){
        this.r = r;
        this.c = c;
    }

    public Regex getRegex() {
       return this.r;
    }
    
    public int getC(){
        return this.c;
    }
}
