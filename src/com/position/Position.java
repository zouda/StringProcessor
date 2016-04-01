package com.position;

import com.Tool;

/*
 * Position operator, which includes Pos & CPos
 */
public class Position {
    public void Print(){
        Tool.print("Print of Position(should be override by Pos && CPos)\n");
    }

    public boolean equals(Position p) {
        if ((this instanceof CPos) && (p instanceof CPos)){
            CPos p1 = (CPos)this;
            CPos p2 = (CPos)p;
            if (p1.getPos() == p2.getPos())
                return true;
        }
        if ((this instanceof Pos) && (p instanceof Pos)){
            Pos p1 = (Pos)this;
            Pos p2 = (Pos)p;
            if (p1.equals(p2))
                return true;
        }
        return false;
    }
}
