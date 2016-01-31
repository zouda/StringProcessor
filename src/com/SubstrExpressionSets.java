package com;

import java.util.ArrayList;
import com.expression.*;


public class SubstrExpressionSets {
    public ArrayList<Expression> ExpressionList;
    public Sample sample;
    public int LeftIndex, RightIndex;
	
    public SubstrExpressionSets(){
        ExpressionList = new ArrayList<Expression>();
    }
	
    public SubstrExpressionSets(Sample sample, int LeftIndex, int RightIndex){
        ExpressionList = new ArrayList<Expression>();
        this.sample = sample;
        this.LeftIndex = LeftIndex;
        this.RightIndex = RightIndex;
    }
	
    void addExpression(Expression e){
        ExpressionList.add(e);
    }
}
