package com.position;

/*
 * CPos operator
 */
public class CPos extends Position {
    private int pos;
    
    public CPos(){
        
    }
    
    public CPos(int pos){
        this.pos = pos;
    }
    
    public int getPos(){
        return pos;
    }
}
