package com.expression;

/*
 * ConstStr Expression 
 */

public class ExpressionConststr extends Expression {
	
    public String ConstStr = null;
	
    public ExpressionConststr(String s) {
        ConstStr = s;
    }
	
    public void setConstStr(String s){
        ConstStr = s;
    }
	
    public String getConstStr(){
        return ConstStr;
    }
}
