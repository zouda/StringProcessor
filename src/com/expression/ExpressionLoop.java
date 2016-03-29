package com.expression;

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
}
