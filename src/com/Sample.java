package com;

import java.util.ArrayList;
import com.position.*;
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
    
    //generate position groups for input string
    public void generatePositionGroups(){
        for (int i = 0; i < Input.length()+1; i++){
            PositionGroup pg = generatePositionGroupAt(i);
            PositionGroupList.add(pg);
        }
    }
    
    //generate position group for input string at pos
    public PositionGroup generatePositionGroupAt(int pos){
        PositionGroup pg = new PositionGroup();
        pg.AddPosition(new CPos(pos));
        pg.AddPosition(new CPos(-(Input.length()-pos)));
        
        if (isTokenInterruptable(pos)){
            
        }
        return pg;
    }
    
    //check: the pos is not inside one token 
    public boolean isTokenInterruptable(int pos){
        if ((pos == 0) || (pos == Input.length()))
            return true;
        if (Tool.isSameKindToken(Input.charAt(pos-1), Input.charAt(pos)))
            return false;
        return false;
    }
    
    public PositionGroup getPositionGroupAt(int pos){
        return PositionGroupList.get(pos);
    }
}
