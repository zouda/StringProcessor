package com.dag;

import com.Global;

/*
 * Node in DAG
 */
public class Node {
    private int[] label; 
    private int dim;
    private int size = 0;
    
    public Node(){
        label = new int[Global.MAX_LABEL_NUMBER];
    }
    
    public Node(int num){
        label = new int[Global.MAX_LABEL_NUMBER];
        label[0] = num;
        dim = 1;
    }
    
    public Node(Node n1, Node n2){
        label = new int[n1.dim+n2.dim];
        for (int i = 0; i < n1.dim; i++){
            label[i] = n1.label[i];
        }
        for (int i = n1.dim; i < n1.dim+n2.dim; i++){
            label[i] = n2.label[i-n1.dim];
        }
        dim = n1.dim+n2.dim; 
    }
    
    public void setDim(int dim){
        this.dim = dim;
    }
    
    public int getDim(){
        return this.dim;
    }
    
    public void setLabel(int[] label){
        this.label = label.clone();
    }
    
    public int[] getLabel(){
        return this.label;
    }
    
    public void setSize(int size){
        this.size = size;
    }
    
    public int getSize(){
        return this.size;
    }
}
