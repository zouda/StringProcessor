package com.dag;

import java.util.ArrayList;

/*
 * Node in DAG
 */
public class Node {
    //private int[] label; 
    private int dim;
    private double size = 0;
    private ArrayList<Edge> PathList;
    public boolean SizeCompleted = false;
    public boolean Isolated = false;
    private int index;
    
    public Node(){
        PathList = new ArrayList<Edge>();
    }
    
    public Node(int num){
        PathList = new ArrayList<Edge>();
//        label = new int[1];
//        label[0] = num;
        dim = 1;
    }
    
    public Node(Node n1, Node n2){
        PathList = new ArrayList<Edge>();
//        label = new int[n1.dim+n2.dim];
//        for (int i = 0; i < n1.dim; i++){
//            label[i] = n1.label[i];
//        }
//        for (int i = n1.dim; i < n1.dim+n2.dim; i++){
//            label[i] = n2.label[i-n1.dim];
//        }
        dim = n1.dim+n2.dim; 
    }
    
    public int getPathSize(){
        return this.PathList.size();
    }
    
    public Edge getPathAt(int pos){
        return this.PathList.get(pos);
    }
    
    public void setDim(int dim){
        this.dim = dim;
    }
    
    public int getDim(){
        return this.dim;
    }
    
//    public void setLabel(int[] label){
//        this.label = label.clone();
//    }
//    
//    public int[] getLabel(){
//        return this.label;
//    }
    
    public void setSize(double size){
        this.size = size;
    }
    
    public double getSize(){
        return this.size;
    }

    public void addPath(Edge e) {
        this.PathList.add(e);
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
