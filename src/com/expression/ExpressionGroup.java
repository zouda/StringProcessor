package com.expression;

import java.util.ArrayList;
import com.Sample;

/*
 * Group of expressions at Input(L, R)
 */
public class ExpressionGroup {
    private ArrayList<Expression> ExpressionList;
	
    public ExpressionGroup(){
        ExpressionList = new ArrayList<Expression>();
    }
	
    public ExpressionGroup(Sample sample, int L, int R){
        ExpressionList = new ArrayList<Expression>();
    }
	
    public void addExpression(Expression e){
        ExpressionList.add(e);
    }
    
    public Expression getExpressionAt(int pos){
        return ExpressionList.get(pos);
    }
    
    public int getSize(){
        return ExpressionList.size();
    }
    
    public void Print(){
        for (int i = 0; i < ExpressionList.size(); i++){
            ExpressionList.get(i).Print();
        }
    }
}
