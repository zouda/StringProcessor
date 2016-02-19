package com.dag;


/*
 * pair of DAGs
 */
public class DAGPair {
    private int index1;
    private int index2;
    
    public void setDAGIndex1(int i) {
        this.index1 = i; 
    }
    
    public void setDAGIndex2(int i) {
        this.index2 = i;
    }
    
    public int getIndex1(){
        return this.index1;
    }
    
    public int getIndex2(){
        return this.index2;
    }
    
}
