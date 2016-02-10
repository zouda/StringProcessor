package com.regex;

import java.util.ArrayList;
import com.Token.*;

/*
 * Regular Expression
 */

public class Regex {
    private ArrayList<Tok> TokenSeq;  
    
    public Regex(){
        TokenSeq = new ArrayList<Tok>();
    }
    
    public Regex(Tok tok){
        TokenSeq = new ArrayList<Tok>();
        TokenSeq.add(tok);
    }
    
    public void addTok(Tok tok){
        TokenSeq.add(tok);
    }
    
    public void addRegex(Regex r){
        TokenSeq.addAll(r.TokenSeq);
    }
    
    public int getSize(){
        return TokenSeq.size();
    }
    
    public Tok getTokenAt(int pos){
        return TokenSeq.get(pos);
    }
    
    public Regex clone(){
        Regex c = new Regex();
        for (int i = 0; i < TokenSeq.size(); i++){
            c.addTok(TokenSeq.get(i));
        }
        return c;
    }
    
    public boolean isVoid(){
        if (TokenSeq.size() == 1 && TokenSeq.get(0) == Tok.VoidTok)
            return true;
        return false;
    }
    
    public void setToken(int pos, Tok tok){
        TokenSeq.set(pos, tok);
    }
}
