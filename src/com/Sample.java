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
        for (int i = 0; i < Input.length(); i++){
            
        }
    }
    
    //generate position for input string at pos index
    public void generatePositionAt(int pos){
        
    }
    
    public PositionGroup getPositionGroupAt(int pos){
        return PositionGroupList.get(pos);
    }
}
