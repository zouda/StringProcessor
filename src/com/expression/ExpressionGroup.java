package com.expression;

import java.util.ArrayList;
import com.Sample;

public class ExpressionGroup {
    public ArrayList<Expression> ExpressionList;
    public Sample sample;
    public int LeftIndex, RightIndex;
	
    public ExpressionGroup(){
        ExpressionList = new ArrayList<Expression>();
    }
	
    public ExpressionGroup(Sample sample, int LeftIndex, int RightIndex){
        ExpressionList = new ArrayList<Expression>();
        this.sample = sample;
        this.LeftIndex = LeftIndex;
        this.RightIndex = RightIndex;
    }
	
    public void addExpression(Expression e){
        ExpressionList.add(e);
    }
}
