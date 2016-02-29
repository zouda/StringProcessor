package com.bool;

import java.util.ArrayList;

public class Bool {
    private ArrayList<MatchGroup> MatchGroupList;
    
    public Bool(){
        MatchGroupList = new ArrayList<MatchGroup>();
    }
    
    public void addConjunct(MatchGroup mg){
        this.MatchGroupList.add(mg);
    }
    
    public MatchGroup getConjuctAt(int pos){
        return this.MatchGroupList.get(pos);
    }
    
    public int getConjunctNumber(){
        return this.MatchGroupList.size();
    }
}
