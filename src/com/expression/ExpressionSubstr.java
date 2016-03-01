package com.expression;

import com.Tool;
import com.position.PositionGroup;

/*
 * Substring Expression 
 */
public class ExpressionSubstr extends Expression{
    public PositionGroup pg1, pg2;
    
    public ExpressionSubstr(PositionGroup pg1, PositionGroup pg2){
        this.pg1 = pg1;
        this.pg2 = pg2;
    }

    public PositionGroup getPositionGroup1() {
        return this.pg1;
    }
    
    public PositionGroup getPositionGroup2() {
        return this.pg2;
    }
    
    public void Print(){
        Tool.print("SubStr(v1, ");
        pg1.getPositionAt(0).Print();
        Tool.print(",");
        pg2.getPositionAt(0).Print();
        Tool.print(")");
    }
}
