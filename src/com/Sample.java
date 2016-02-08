package com;

import java.util.ArrayList;

import com.position.*;
import com.regex.*;
import com.Token.*;
/*
 * Sample
 */

public class Sample {
    public String Input = "";
    public String Output = "";
    public ArrayList<PositionGroup> PositionGroupList;
    
    public Sample(){
        PositionGroupList = new ArrayList<PositionGroup>();
    }
    
    public Sample(String input, String output){
        PositionGroupList = new ArrayList<PositionGroup>();
        this.Input = input;
        this.Output = output;
    }
    
    public void setInput(String s){
        this.Input = s;
    }
	
    public void setOutput(String s){
        this.Output = s;
    }
	
    public String getInput(){
        return this.Input;
    }
	
    public String getOutput(){
        return this.Output;
    }
    
    //check: the pos is not inside one token 
    private boolean isTokenInterruptable(int pos){
        if ((pos == 0) || (pos == Input.length()))
            return true;
        if (Tool.isSameKindToken(Input.charAt(pos-1), Input.charAt(pos)))
            return false;
        return false;
    }
    
    //generate position groups for input string
    public void generatePositionGroups(){
        for (int i = 0; i < Input.length()+1; i++){
            PositionGroup pg = generatePositionGroupAt(i);
            PositionGroupList.add(pg);
        }
    }
    
    //generate position group for input string at pos
    private PositionGroup generatePositionGroupAt(int pos){
        PositionGroup pg = new PositionGroup();
        pg.AddPosition(new CPos(pos));
        pg.AddPosition(new CPos(-(Input.length()-pos)));
        
        if (isTokenInterruptable(pos)){
            RegexGroup rg1 = ParseLeft(pos);
            RegexGroup rg2 = ParseRight(pos);
            for (int i = 0; i < rg1.getRegexNumber(); i++)
                for (int j = 0; j < rg2.getRegexNumber(); j++){
                    //for every r1 in rg1, every r2 in rg2,set r = (r1,r2), check if r is the c match of input.
                    //add Pos(r1,r2,c) to pg.
                }
        }
        return pg;
    }
    
    private RegexGroup ParseLeft(int pos){
        RegexGroup rg = new RegexGroup();
        for (int i = pos-1; i >= 0; i--){
            Regex r = MatchRegexWithToken(Input, i, pos);
            //TODO: MatchRegexWithTokenseq
            if (r != null)
                rg.addRegex(r);
        }
        rg.addVoidRegex();
        return rg;
    }
    
    private RegexGroup ParseRight(int pos){
        RegexGroup rg = new RegexGroup();
        for (int i = pos+1; i <= Input.length(); i++){
            Regex r = MatchRegexWithToken(Input, pos, i);
            if (r != null)
                rg.addRegex(r);
        }
        rg.addVoidRegex();
        return rg;
    }
    
    private Regex MatchRegexWithToken(String s, int Lindex, int Rindex){
        Regex r = new Regex();
        String ss = s.substring(Lindex,Rindex);
        Tok tok = Token.getTokenType(ss);
        if (tok == null)
            return null;
        if (tok == Tok.NumTok){
            if (Lindex != 0){
                if (Tool.isNumber(s.charAt(Lindex-1)))
                    return null;
            }
            if (Rindex != s.length()){
                if (Tool.isNumber(s.charAt(Rindex)))
                    return null;
            }
        }
        if (tok == Tok.UpperTok){
            if (Lindex != 0){
                if (Tool.isUppercaseAlphabet(s.charAt(Lindex-1)))
                    return null;
            }
            if (Rindex != s.length()){
                if (Tool.isUppercaseAlphabet(s.charAt(Rindex)))
                    return null;
            }
        }
        if (tok == Tok.LowerTok){
            if (Lindex != 0){
                if (Tool.isLowercaseAlphabet(s.charAt(Lindex-1)))
                    return null;
            }
            if (Rindex != s.length()){
                if (Tool.isLowercaseAlphabet(s.charAt(Rindex)))
                    return null;
            }
        }
        if (tok == Tok.AlphaTok){
            if (Lindex != 0){
                if (Tool.isAlphabet(s.charAt(Lindex-1)))
                    return null;
            }
            if (Rindex != s.length()){
                if (Tool.isAlphabet(s.charAt(Rindex)))
                    return null;
            }
        }
        r.setTok(tok);
        return r;
    }
    
    public PositionGroup getPositionGroupAt(int pos){
        return PositionGroupList.get(pos);
    }
}
