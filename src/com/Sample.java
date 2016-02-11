package com;

import java.util.ArrayList;
import com.position.*;
import com.regex.*;
import com.Token.*;

/*
 * Sample
 */
public class Sample {
    private String Input = "";
    private String Output = "";
    private ArrayList<PositionGroup> PositionGroupList;
    
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
        pg.AddPosition(new CPos(-(Input.length()-pos+1)));
        
        if (isTokenInterruptable(pos)){
            RegexGroup rg1 = ParseLeft(pos);
            RegexGroup rg2 = ParseRight(pos);
            for (int i = 0; i < rg1.getSize(); i++)
                for (int j = 0; j < rg2.getSize(); j++){
                    Regex r1 = rg1.getRegexAt(i);
                    Regex r2 = rg2.getRegexAt(j);
                    if (r1.isVoid() && r2.isVoid())
                        continue;
                    Regex r12;
                    if (r1.isVoid()){
                        r12 = r2.clone();
                    }else if (r2.isVoid()){
                        r12 = r1.clone();
                    }else {
                        r12 = r1.clone();
                        r12.addRegex(r2);
                    }
                    RegexOccurrence occur = getOccurrenceNumber(r12, pos);
                    int occur_num = occur.occur_num;
                    int appear = occur.appear;
                    pg.AddPosition(new Pos(r1, r2, appear));
                    pg.AddPosition(new Pos(r1, r2, appear-occur_num-1));
                }
        }
        return pg;
    }
    
    public class RegexOccurrence{
        public int occur_num;
        public int appear;
        
        public RegexOccurrence(int o, int a){
            occur_num = o;
            appear = a;
        }
    }
    
    private RegexOccurrence getOccurrenceNumber(Regex r, int r_pos){
        if (r.getTokenAt(0) == Tok.StartTok)
            return (new RegexOccurrence(1,1));
        if (r.getTokenAt(r.getSize()-1) == Tok.EndTok)
            return (new RegexOccurrence(1,1));
        int count = 0;
        int appear = 0;
        for (int start_pos = 0; start_pos < Input.length(); start_pos++){
            int start = start_pos;
            int index = 0;
            while (true){
                boolean success = true;
                int i;
                for (i = start+1; i <= Input.length(); i++){
                    Regex temp = MatchStringWithToken(Input, start, i);
                    if (temp != null){
                        if (temp.getTokenAt(0) == r.getTokenAt(index)){
                            index++;
                            start = i;
                        } 
                        else{
                            success = false;
                        }
                        break;
                    }
                }
                if (success == false)
                    break;
                if (index == r.getSize()){
                    count++;
                    if (start_pos == r.getL() && i == r.getR())
                        appear = count;
                    break;
                }
                if (i > Input.length()){
                    break;
                }
            }
        }
        if (count == 0){
            Tool.error("error: Occurance = 0 (function: getOccurrenceNumber)");
            System.out.println(r_pos);
        }
        if (appear == 0){
            Tool.error("error: Appear = 0 (function: getOccurrenceNumber)");
            System.out.println(r_pos);
        }
        return (new RegexOccurrence(count, appear));
    }
    
    private RegexGroup ParseLeft(int pos){
        RegexGroup rg = new RegexGroup();
        for (int i = pos-1; i >= 0; i--){
            Regex r = MatchStringWithTokenSeq(Input, i, pos);
            if (r != null){
                rg.addRegex(r);
                if (i == 0){
                    Regex temp = r.clone();
                    temp.setToken(0, Tok.StartTok);
                    rg.addRegex(temp);
                }
            }
        }
        rg.addVoidRegex();
        return rg;
    }
    
    private RegexGroup ParseRight(int pos){
        RegexGroup rg = new RegexGroup();
        for (int i = pos+1; i <= Input.length(); i++){
            Regex r = MatchStringWithTokenSeq(Input, pos, i);
            if (r != null){
                rg.addRegex(r);
                if (i == Input.length()){
                    Regex temp = r.clone();
                    temp.setToken(temp.getSize()-1, Tok.EndTok);
                    rg.addRegex(temp);
                }
            }
        }
        rg.addVoidRegex();
        return rg;
    }
    
    private Regex MatchStringWithTokenSeq(String s, int L, int R){
        Regex result = new Regex();
        int start = L;
        while (true){
            boolean match = false;
            for (int i = start+1; i <= R; i++){
                Regex r = MatchStringWithToken(s, start, i);
                if (r != null){
                    result.addRegex(r);
                    start = i;
                    match = true;
                    break;
                }
            }
            if (!match)
                return null;
            if (start == R)
                break;
        }
        result.setRange(L, R);
        return result;
    }
    
    private Regex MatchStringWithToken(String s, int L, int R){
        Regex r = new Regex();
        String ss = s.substring(L,R);
        Tok tok = Token.getTokenType(ss);
        if (tok == null)
            return null;
        if (tok == Tok.NumTok){
            if (L != 0){
                if (Tool.isNumber(s.charAt(L-1)))
                    return null;
            }
            if (R != s.length()){
                if (Tool.isNumber(s.charAt(R)))
                    return null;
            }
        }
        if (tok == Tok.UpperTok){
            if (L != 0){
                if (Tool.isUppercaseAlphabet(s.charAt(L-1)))
                    return null;
            }
            if (R != s.length()){
                if (Tool.isUppercaseAlphabet(s.charAt(R)))
                    return null;
            }
        }
        if (tok == Tok.LowerTok){
            if (L != 0){
                if (Tool.isLowercaseAlphabet(s.charAt(L-1)))
                    return null;
            }
            if (R != s.length()){
                if (Tool.isLowercaseAlphabet(s.charAt(R)))
                    return null;
            }
        }
        if (tok == Tok.AlphaTok){
            if (L != 0){
                if (Tool.isAlphabet(s.charAt(L-1)))
                    return null;
            }
            if (R != s.length()){
                if (Tool.isAlphabet(s.charAt(R)))
                    return null;
            }
        }
        r.addTok(tok);
        r.setRange(L, R);
        return r;
    }
    
    public PositionGroup getPositionGroupAt(int pos){
        return PositionGroupList.get(pos);
    }
}
