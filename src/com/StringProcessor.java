package com;

import com.expression.*;
import com.position.*;

import java.io.*;

/*
 * Main process
 */
public class StringProcessor {
    public Sample sample;
	
    public Sample InputSamples(){
        Sample result = new Sample();
        FileInputStream fis = null;
        InputStreamReader isr = null;
        BufferedReader br = null; 
        try {
            fis = new FileInputStream(Global.INPUT_FILE_PATH);
            isr = new InputStreamReader(fis);
            br = new BufferedReader(isr);
            result.setInput(br.readLine());
            result.setOutput(br.readLine());
        } catch (FileNotFoundException e) {
            System.out.println("Error: File Not Exist");
        } catch (IOException e) {
            System.out.println("Error: File Reading Failed");
        } 
        finally {
            try {
                br.close();
                isr.close();
                fis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }
	
    public void OutputResults(){
        System.out.println(sample.getInput());
        System.out.println(sample.getOutput());
    }
	
    //generate trace expressions for one sample
    public void GenerateStr(Sample sample){
        String s = sample.getOutput();
        for (int i = 0; i < s.length(); i++){
            for (int j = i + 1; j <= s.length(); j++){
                ExpressionGroup eg = GenerateSubstring(sample, i, j);
                ExpressionConststr ec = new ExpressionConststr(s.substring(i, j));
                eg.addExpression(ec);
                if (i == 0 && j == 3){
                    eg.Print();
                }
            }
        }
    }
	
    //generate expression group for substring of output string
    public ExpressionGroup GenerateSubstring(Sample sample, int L, int R){
        ExpressionGroup eg = new ExpressionGroup(sample, L, R);
        String target = sample.getOutput().substring(L, R);
        String source = sample.getInput();
        while (true){
            int pos = source.indexOf(target);
            if (pos == -1) 
                break;
            PositionGroup pg1 = getPositionGroup(sample, pos);
            PositionGroup pg2 = getPositionGroup(sample, pos+target.length());
            for (int i = 0; i < pg1.getSize(); i++){
                for (int j = 0; j < pg2.getSize(); j++){
                    Position p1 = pg1.getPositionAt(i);
                    Position p2 = pg2.getPositionAt(j);
                    if (p1 != null && p2 != null){
                        ExpressionSubstr es = new ExpressionSubstr(sample, p1, p2);
                        eg.addExpression(es);
                    }
                }
            }
            source = source.substring(pos+1, source.length());
        }
        return eg;
    }
	
    public PositionGroup getPositionGroup(Sample sample, int pos){
		return sample.getPositionGroupAt(pos);
    }
    
    public void GenerateLoop(){
        
    }
    
    public void Display(){
        
    }
    
    public void run(){
        Tool.startFileWriting();
        sample = InputSamples();
        OutputResults();
        sample.generatePositionGroups();
        GenerateStr(sample);
        //Display();
        Tool.endFileWriting();
    }
}
