package com.dag;

import java.util.ArrayList;

import com.Tool;

public class Route {
    private ArrayList<Edge> EdgeList;

    public Route(){
        EdgeList = new ArrayList<Edge>();
    }
    
    public ArrayList<Edge> getEdgeList() {
        return EdgeList;
    }

    public void setEdgeList(ArrayList<Edge> edgeList) {
        EdgeList = edgeList;
    }

    public void add(Edge e) {
        this.EdgeList.add(e);
    }
    
    public Edge get(int index){
        return this.EdgeList.get(index);
    }
    
    public void remove(Edge e){
        this.EdgeList.remove(e);
    }
    
    public void remove(int index){
        this.EdgeList.remove(index);
    }
    
    public Route clone(){
        Route r = new Route();
        for (int i = 0; i < this.EdgeList.size(); i++){
            r.add(this.EdgeList.get(i));
        }
        return r;
    }
    
    public int size(){
        return this.EdgeList.size();
    }
    
    public void Print(){
        if (this.size() > 1){
            Tool.print("Concatenate(");
        }
        for (int i = 0; i < this.size(); i++){
            if (i > 0)
                Tool.print(" + ");
            this.get(i).getExpressionGroup().getExpressionAt(0).Print();
        }
        if (this.size() > 1){
            Tool.print(")");
        }   
        Tool.println("");
    }
}
