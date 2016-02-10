package com.regex;

import java.util.ArrayList;
import com.Token.Tok;

public class RegexGroup {
    public ArrayList<Regex> RegexList;
    
    public RegexGroup(){
        RegexList = new ArrayList<Regex>();
    }
    
    public void addRegex(Regex r){
        RegexList.add(r);
    }

    public void addVoidRegex() {
        RegexList.add(new Regex(Tok.VoidTok));
    }
    
    public int getSize(){
        return RegexList.size();
    }
    
    public Regex getRegexAt(int pos){
        return RegexList.get(pos);
    }
}
