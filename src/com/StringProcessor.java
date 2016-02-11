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
            for (int j = i; j < s.length(); j++){
                ExpressionGroup eg = GenerateSubstring(sample, i, j);
                ExpressionConststr ec = new ExpressionConststr(s.substring(i, j));
                eg.addExpression(ec);
            }
        }
    }
	
    //generate trace expression for substring of output string
    public ExpressionGroup GenerateSubstring(Sample sample, int L, int R){
        ExpressionGroup eg = new ExpressionGroup(sample, L, R);
        String target = sample.getOutput().substring(L, R);
        String source = sample.getInput();
        while (true){
            int pos = source.indexOf(target);
            if (pos == -1) 
                break;
            Position p1 = GeneratePosition(sample, pos);
            Position p2 = GeneratePosition(sample, pos+target.length());
            if (p1 != null && p2 != null){
                ExpressionSubstr es = new ExpressionSubstr(sample, p1, p2);
                eg.addExpression(es);
            }
            source = source.substring(pos+1, target.length());
        }
        return eg;
    }
	
    public Position GeneratePosition(Sample sample, int pos){
		return null;
    }
	
    public void GenerateRegex(){
		
    }
    
    public void GenerateLoop(){
        
    }
    
    public void run(){
        sample = InputSamples();
        OutputResults();
        sample.generatePositionGroups();
    }
}
