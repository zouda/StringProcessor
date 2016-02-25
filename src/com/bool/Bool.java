package com.bool;

import java.util.ArrayList;

public class Bool {
    
    private ArrayList<MatchGroup> MatchGroupList;
    
    public Bool(){
    }
    
    public void addConjunct(MatchGroup mg){
        this.MatchGroupList.add(mg);
    }
}
