package com.position;

import com.regex.*;

public class Pos extends Position {
    private Regex r1, r2;
    private int c;
    
    public Pos(){
        
    } 
    
    public Pos(Regex r1, Regex r2, int c){
        this.r1 = r1;
        this.r2 = r2;
        this.c = c;
    }
}
