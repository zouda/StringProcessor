package com.dag;

import com.expression.ExpressionGroup;

/*
 * Edge in DAG
 */
public class Edge {
    private ExpressionGroup eg;
    private Node sNode;
    private Node tNode;
    
    public void setExpressionGroup(ExpressionGroup eg) {
        this.eg = eg;
    }
    
    public ExpressionGroup getExpressionGroup() {
        return eg;
    }

    public void setEdge(Node s, Node t){
        sNode = s;
        tNode = t;
    }
    
    public Node getSource(){
        return sNode;
    }
    
    public Node getTarget(){
        return tNode;
    }
}
