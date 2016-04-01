package com.expression;

import com.Tool;
import com.dag.DAG;

/*
 * Loop Expression
 */
public class ExpressionLoop extends Expression {
    private DAG d;
    
    public ExpressionLoop(DAG d) {
        this.d = d;
    }
    
    public DAG getDAG(){
        return this.d;
    }
    
    @Override
    public void Print(){
        Tool.print("Loop(");
        d.Print(false, true);
        Tool.print(")");
    }
}
