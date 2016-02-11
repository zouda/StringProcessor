package com.expression;

import java.util.ArrayList;
import com.Sample;

/*
 * Group of Expression at (L, R) of Input String
 */
public class ExpressionGroup {
    public ArrayList<Expression> ExpressionList;
    public Sample sample;
    public int L, R;
	
    public ExpressionGroup(){
        ExpressionList = new ArrayList<Expression>();
    }
	
    public ExpressionGroup(Sample sample, int L, int R){
        ExpressionList = new ArrayList<Expression>();
        this.sample = sample;
        this.L = L;
        this.R = R;
    }
	
    public void addExpression(Expression e){
        ExpressionList.add(e);
    }
}
