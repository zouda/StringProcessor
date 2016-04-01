package com.position;

import com.Tool;
import com.regex.*;

/*
 * Pos operator
 */
public class Pos extends Position {
    private Regex r1 = null, r2 = null;
    private int c;
    
    public Pos(){
        
    } 
    
    public Pos(Regex r1, Regex r2, int c){
        this.r1 = r1;
        this.r2 = r2;
        this.c = c;
    }
    
    public Regex getRegex1(){
        return this.r1;
    }
    
    public Regex getRegex2(){
        return this.r2;
    }
    
    public int getC(){
        return this.c;
    }
    
    
    public void Print(){
        if (r1 == null || r2 == null){
            Tool.error("error: undefined Pos");
            return;
        }
        Tool.print("Pos(");
        r1.Print();
        Tool.print(",");
        r2.Print();
        Tool.print(",");
        Tool.print(c);
        Tool.print(")");
    }
    
    public boolean equals(Pos p){
        if ((this.r1.equals(p.r1)) && (this.r2.equals(p.r2)))
            return true;
        return false;
    }
}
