package com.dag;

import java.util.ArrayList;

import com.Tool;
import com.bool.Bool;

public class DAGGroup {
    private ArrayList<DAG> DAGList;
    private ArrayList<Bool> BoolList;
    
    public DAGGroup(){
        DAGList = new ArrayList<DAG>();
        BoolList = new ArrayList<Bool>();
    }
    
    public boolean ExistCompPair(){
        for (int i = 0; i < DAGList.size()-1; i++){
            for (int j = i+1; j < DAGList.size(); j++){
                if (DAG.Comp(DAGList.get(i), DAGList.get(j)))
                    return true;
            }
        }
        return false;
    }
    
    private int CS1(int i, int j){
        int score = 0;
        DAG d1 = DAGList.get(i);
        DAG d2 = DAGList.get(j);
        for (int k = 0; k < DAGList.size(); k++){
            if (k != i && k != j){
                boolean c1 = DAG.Comp(DAGList.get(k), d1);
                boolean c2 = DAG.Comp(DAGList.get(k), d2);
                if (c1 != c2){
                    continue;
                }
                DAG d = DAG.IntersectDAG(d1, d2);
                boolean c3 = DAG.Comp(DAGList.get(k), d);
                if (c1 != c3){
                    continue;
                }
                score++;
            }
        }
        return score;
    }
    
    private double CS2(int i, int j){
        DAG d1 = DAGList.get(i);
        DAG d2 = DAGList.get(j);
        DAG d = DAG.IntersectDAG(d1, d2);
        double size = Tool.Max_Double(d1.getSize(), d2.getSize());
        double score = d.getSize()/size;
        return score;
    }
    
    public DAGPair FindLargestCSPair(){
        DAGPair dp = new DAGPair();
        int maxCS1 = -1, mark_i = -1, mark_j = -1;
        double maxCS2 = -1;
        for (int i = 0; i < DAGList.size()-1; i++){
            for (int j = i+1; j < DAGList.size(); j++){
                if (DAG.Comp(DAGList.get(i), DAGList.get(j))){
                    int cs1 = CS1(i, j);
                    double cs2 = CS2(i, j);
                    if ((cs1 > maxCS1) || (cs1 == maxCS1 && cs2 > maxCS2)){
                        maxCS1 = cs1;
                        maxCS2 = cs2;
                        mark_i = i;
                        mark_j = j;
                    }
                }   
            }
        }
        if (maxCS1 == -1){
            Tool.error("error: can not find larget CS pair");
            return null;
        }
        dp.setDAGIndex1(mark_i);
        dp.setDAGIndex2(mark_j);
        return dp;
    }

    public void removeDAGAt(int pos) {
        DAGList.remove(pos);
    }

    public void addDAG(DAG newDAG) {
        this.DAGList.add(newDAG);
    }

    public DAG getDAGAt(int pos) {
        return this.DAGList.get(pos);
    }

    public int getDAGNumber() {
        return this.DAGList.size();
    }

    public void addBoolClassifier(Bool b) {
        this.BoolList.add(b);
    }
    
    public Bool getBoolAt(int pos){
        return this.BoolList.get(pos);
    }
}

