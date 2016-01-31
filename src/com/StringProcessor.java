package com;

import com.expression.*;
import java.io.*;

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
	
	//generate trace expressions for one sample.
	public void GenerateStr(Sample sample){
		String s = sample.getOutput();
		for (int i = 0; i < s.length(); i++){
			for (int j = i; j < s.length(); j++){
				SubstrExpressionSets ses = GenerateSubstring(sample, i, j);
				ExpressionConststr ec = new ExpressionConststr(s.substring(i, j));
				ses.addExpression(ec);
			}
		}
	}
	
	//generate trace expression for substring of output string. 
	public SubstrExpressionSets GenerateSubstring(Sample sample, int leftIndex, int rightIndex){
		SubstrExpressionSets ses = new SubstrExpressionSets(sample, leftIndex, rightIndex);
		
		return ses;
	}
	
	public void GeneratePosition(String s, int pos){
		
	}
	
	public void GenerateRegex(){
		
	}
	
	public void run(){
		sample = InputSamples();
		OutputResults();
	}
}
