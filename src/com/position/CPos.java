package com.position;

import com.Tool;

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
    
    @Override
    public void Print(){
        Tool.print("CPos(");
        Tool.print(pos);
        Tool.print(")\n");
    }
}
