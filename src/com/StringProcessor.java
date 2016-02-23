package com;

import com.dag.*;
import com.expression.*;
import com.position.*;
import java.io.*;
import java.util.ArrayList;

/*
 * Main process
 */
public class StringProcessor {
	public ArrayList<Sample> SampleList;
	public DAGGroup T;
	
	public StringProcessor(){
	    SampleList = new ArrayList<Sample>();
	}
	
    public void InputSamples(){
        FileInputStream fis = null;
        InputStreamReader isr = null;
        BufferedReader br = null; 
        try {
            fis = new FileInputStream(Global.INPUT_FILE_PATH);
            isr = new InputStreamReader(fis);
            br = new BufferedReader(isr);
            while (true){
                String input = br.readLine();
                String output = br.readLine();
                if (input == null || output == null){
                    break;
                }
                Sample sample = new Sample();
                sample.setInput(input);
                sample.setOutput(output);
                SampleList.add(sample);
            }
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
    }
	
    public void DisplayInputContent(){
        for (int i = 0; i < SampleList.size();i++){
            Sample sample = SampleList.get(i);
            Tool.println(sample.getInput());
            Tool.println(sample.getOutput());
        }
    }
	
    public DAG GenerateDAG(Sample sample){
        DAG dag = new DAG();
        String s = sample.getOutput();
        for (int i = 0; i <= s.length(); i++){
            Node n = new Node(i);
            dag.addNode(n);
        }
        dag.setDim(1);
        dag.setOneDimSize(s.length());
        dag.setStartNode(dag.getNodeAt(0));
        dag.setEndNode(dag.getNodeAt(s.length()));
        for (int i = 0; i < s.length(); i++){
            for (int j = i + 1; j <= s.length(); j++){
                ExpressionGroup eg = GenerateSubstring(sample, i, j);
                ExpressionConststr ec = new ExpressionConststr(s.substring(i, j));
                eg.addExpression(ec);

                Edge e = new Edge();
                e.setEdge(dag.getNodeAt(i), dag.getNodeAt(j));
                e.setExpressionGroup(eg);
                dag.addEdge(e);
                dag.getNodeAt(i).addPath(e);
            }
        }
        sample.setDAG(dag);
        return dag;
    }
	
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
//            for (int i = 0; i < pg1.getSize(); i++){
//                for (int j = 0; j < pg2.getSize(); j++){
//                    Position p1 = pg1.getPositionAt(i);
//                    Position p2 = pg2.getPositionAt(j);
//                    if (p1 != null && p2 != null){
//                        ExpressionSubstr es = new ExpressionSubstr(sample, p1, p2);
//                        eg.addExpression(es);
//                    }
//                }
//            }
            ExpressionSubstr es = new ExpressionSubstr(pg1, pg2);
            eg.addExpression(es);
            source = source.substring(pos+1, source.length());
        }
        return eg;
    }
	
    public PositionGroup getPositionGroup(Sample sample, int pos){
		return sample.getPositionGroupAt(pos);
    }
    
    public void GenerateLoop(){
        
    }
    
    public void PreProcess(){
        T = new DAGGroup();
        Tool.startFileWriting();
        InputSamples();
        DisplayInputContent();
    }
    
    public void GenerateTraceExpressionsForEachSample(){
        for (int i = 0; i < SampleList.size(); i++){
            Sample sample = SampleList.get(i);
            sample.generatePositionGroups();
            DAG dag = GenerateDAG(sample);
            T.addDAG(dag);
        }
    }
    
    public void GeneratePartition(){
        while (T.ExistCompPair()){
            DAGPair dp = T.FindLargestCSPair();
            DAG d1 = T.getDAGAt(dp.getIndex1());
            DAG d2 = T.getDAGAt(dp.getIndex2());
            DAG newDAG = DAG.IntersectDAG(d1, d2);
            T.removeDAGAt(dp.getIndex1());
            T.removeDAGAt(dp.getIndex2());
            T.addDAG(newDAG);
        }
    }
    
    public void GenerateBoolClassifier(){
        
    }
    
    public void EndProcess(){
        Tool.endFileWriting();
    }
    
    public void Run(){
        PreProcess();
        GenerateTraceExpressionsForEachSample();
        //GeneratePartition();
        //GenerateBoolClassifier();
        EndProcess();
    }
}
