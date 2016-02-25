package com.bool;

import java.util.ArrayList;

public class MatchGroup {
    private ArrayList<Match> MatchList;
    
    public MatchGroup(){
        this.MatchList = new ArrayList<Match>();
    }
    
    public int getSize(){
        return this.MatchList.size();
    }
    
    public Match getMatchAt(int pos){
        return this.MatchList.get(pos);
    }
    
    public void addMatch(Match m){
        MatchList.add(m);
    }
}
