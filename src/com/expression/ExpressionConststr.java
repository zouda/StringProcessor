package com.expression;

import com.Tool;


/*
 * ConstStr Expression 
 */
public class ExpressionConststr extends Expression {
	
    private String ConstStr = null;
	
    public ExpressionConststr(String s) {
        ConstStr = s;
    }
	
    public void setConstStr(String s){
        ConstStr = s;
    }
	
    public String getConstStr(){
        return ConstStr;
    }
    
    @Override
    public void Print(){
        if (ConstStr == null){
            Tool.error("error: undefined ConstStr");
            return;
        }
        Tool.print("ConstrStr(\"");
        Tool.print(ConstStr);
        Tool.print("\")");
    }
}
