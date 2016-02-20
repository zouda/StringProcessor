package com.expression;

import com.Sample;
import com.Tool;
import com.position.Position;
import com.position.PositionGroup;


/*
 * Substring Expression 
 */
public class ExpressionSubstr extends Expression{
    public Position p1, p2;
    public Sample sample;
    
    public ExpressionSubstr(Sample s, Position p1, Position p2){
        this.p1 = p1;
        this.p2 = p2;
        this.sample = s;
    }
    
    public void Print(){
        Tool.print("SubStr(v1, ");
        p1.Print();
        Tool.print(",");
        p2.Print();
        Tool.print(")");
    }
}
