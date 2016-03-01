package com;

import com.Token.Tok;
import com.bool.Bool;
import com.bool.Match;
import com.bool.MatchGroup;
import com.dag.*;
import com.expression.*;
import com.position.*;
import com.regex.Regex;

import java.io.*;
import java.util.ArrayList;

/*
 * Main process
 */
public class StringProcessor {
	public ArrayList<Sample> SampleList;
	public MatchGroup MatchSet;
	public DAGGroup T;
	public int RouteNumber = 0;
	
	public StringProcessor(){
        T = new DAGGroup();
	    SampleList = new ArrayList<Sample>();
	    MatchSet = new MatchGroup();
	}
	
    public void GenerateAllMatch(){
        for (int i = 1; i <= 3; i++){
            MatchSet.addMatch(new Match(new Regex(Tok.AlphaTok), i));
            MatchSet.addMatch(new Match(new Regex(Tok.ColonTok), i));
            MatchSet.addMatch(new Match(new Regex(Tok.CommaTok), i));
            MatchSet.addMatch(new Match(new Regex(Tok.DotTok), i));
            MatchSet.addMatch(new Match(new Regex(Tok.HyphenTok), i));
            MatchSet.addMatch(new Match(new Regex(Tok.LowerTok), i));
            MatchSet.addMatch(new Match(new Regex(Tok.LparenTok), i));
            MatchSet.addMatch(new Match(new Regex(Tok.NumTok), i));
            MatchSet.addMatch(new Match(new Regex(Tok.RparenTok), i));
            MatchSet.addMatch(new Match(new Regex(Tok.SemicolonTok), i));
            MatchSet.addMatch(new Match(new Regex(Tok.SlashTok), i));
            MatchSet.addMatch(new Match(new Regex(Tok.SpaceTok), i));
            MatchSet.addMatch(new Match(new Regex(Tok.UpperTok), i));
        }
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
	
    public DAG GenerateDAG(Sample sample){
        DAG dag = new DAG();
        String s = sample.getOutput();
        for (int i = 0; i <= s.length(); i++){
            Node n = new Node(i);
            dag.addNode(n);
        }
        dag.setSample(sample);
        dag.setDim(1);
        dag.setOneDimSize(s.length());
        dag.setStartNode(dag.getNodeAt(0));
        dag.setEndNode(dag.getNodeAt(s.length()));
        for (int i = 0; i < s.length(); i++){
            for (int j = i + 1; j <= s.length(); j++){
                ExpressionGroup eg = GenerateSubstring(sample, i, j);
                Edge e = new Edge();
                e.setEdge(dag.getNodeAt(i), dag.getNodeAt(j));
                e.setExpressionGroup(eg);
                dag.addEdge(e);
                dag.getNodeAt(i).addPath(e);
                
                ExpressionConststr ec = new ExpressionConststr(s.substring(i, j));
                eg.addExpression(ec);
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
    
    private Bool GenerateBoolExpression(int index) {
        Bool b = new Bool();
        SampleSet s1 = new SampleSet();
        SampleSet s2 = new SampleSet();
        s1.addSampleSet(T.getDAGAt(index).getSampleList());
        for (int i = 0; i < T.getDAGNumber(); i++){
            if (i != index){
                s2.addSampleSet(T.getDAGAt(i).getSampleList());
            }
        }
        while (!s1.isEmpty()){
            SampleSet old_s1 = s1.Clone(); 
            SampleSet s1_ = s1.Clone();
            MatchGroup d = new MatchGroup();
            while (!s2.isEmpty()){
                SampleSet old_s2 = s2.Clone();
                int max_score = 0, best = 0;
                for (int i = 0; i < MatchSet.getSize(); i++){
                    Match m = MatchSet.getMatchAt(i);
                    int score = CSP(s1, s2, m);
                    if (score > max_score){
                        max_score = score;
                        best = i;
                    }
                }
                s1_.clearUnMatchedWith(MatchSet.getMatchAt(best));
                s2.clearUnMatchedWith(MatchSet.getMatchAt(best));
                d.addMatch(MatchSet.getMatchAt(best));
                if (old_s2.getSize() == s2.getSize())
                    return null;
            }
            s1.removeAllSamplesIn(s1_);
            b.addConjunct(d);
            if (old_s1.getSize() == s1.getSize())
                return null;
        }
        return b;
    }
   
    private int CSP(SampleSet s1, SampleSet s2, Match m) {
        int num1 = 0, num2 = 0;
        for (int i = 0; i < s1.getSize(); i++){
            if (s1.getSampleAt(i).isInputMatchedWith(m))
                num1++;
        }
        for (int i = 0; i < s2.getSize(); i++){
            if (!s2.getSampleAt(i).isInputMatchedWith(m))
                num2++;
        }
        return num1*num2;
    }
    
    private void DisplayExamples(){
        Tool.println("== Examples ==");
        for (int i = 0; i < SampleList.size();i++){
            Sample sample = SampleList.get(i);
            Tool.print(sample.getInput());
            Tool.print(" | ");
            Tool.println(sample.getOutput());
        }
        Tool.println("");
    }
    
    private void DisplayPartition(){
        Tool.println("== Partition ==");
        for (int i = 0 ; i < T.getDAGNumber();i++){
            DAG d = T.getDAGAt(i);
            Tool.print("Group #");
            Tool.print(i+1);
            Tool.println(":");
            for (int j = 0; j < d.getDim(); j++){
                Tool.print(d.getSampleList().get(j).getInput());
                Tool.print(" | ");
                Tool.println(d.getSampleList().get(j).getOutput());
            }
        }
        Tool.println("");
    }
    
    private void DisplayBoolClassifiers(){
        Tool.println("== Classifier ==");
        for (int i = 0; i < T.getDAGNumber(); i++){
            int number = i + 1;
            Tool.print("Group #"+number+": ");
            Bool b = T.getBoolAt(i);
            for (int j = 0; j < b.getConjunctNumber(); j++){
                if (j > 0){
                    Tool.print(" & ");
                }
                MatchGroup d = b.getConjuctAt(j);
                for (int k = 0; k < d.getSize(); k++){
                    if (k > 0){
                        Tool.print(" | ");
                    }
                    Match m = d.getMatchAt(k);
                    Tool.print("Match(v1,");
                    m.getRegex().Print();
                    Tool.print(","+m.getC()+")");
                }
            }
            Tool.println("");
        }
        Tool.println("");
    }
    
    private void DisplayRoute(ArrayList<Edge> route){
        if (route.size() > 0){
            Tool.print("Concatenate(");
        }
        for (int i = 0; i < route.size(); i++){
            if (i > 0)
                Tool.print(" + ");
            route.get(i).getExpressionGroup().getExpressionAt(0).Print();
        }
        if (route.size() > 0){
            Tool.print(")");
        }   
        Tool.println("");
    }
    
    private void DFS(DAG d, Node n, ArrayList<Edge> route){
        if (n == d.getEndNode()){
            RouteNumber++;
            DisplayRoute(route);
            return;
        }
        for (int i = 0; i < n.getPathSize(); i++){
            Edge e = n.getPathAt(i);
            route.add(e);
            DFS(d, e.getTarget(), route);
            route.remove(route.size()-1);
        }
    }
    
    private void TraverseDAG(DAG d){
        RouteNumber = 0;
        DFS(d, d.getStartNode(), new ArrayList<Edge>());
        Tool.print("Total: ");
        Tool.print(RouteNumber);
        Tool.println("");
    }
    
    public void DisplayProgram(){
        Tool.println("== Program ==");
        for (int i = 0; i < T.getDAGNumber(); i++){
            int number = i + 1;
            Tool.print("Group #"+number+":\n");
            TraverseDAG(T.getDAGAt(i));
        }
    }
    
    public void PreProcess(){
        Tool.startFileWriting();
        InputSamples();
        DisplayExamples();
        GenerateAllMatch();
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
            T.removeDAGAt(dp.getIndex2());
            T.removeDAGAt(dp.getIndex1());
            T.addDAG(newDAG);
        }
        DisplayPartition();
    }
    
    public void GenerateBoolClassifier(){
        for (int i = 0; i < T.getDAGNumber(); i++){
            Bool b = GenerateBoolExpression(i);
            if (b == null){
                int number = i+1;
                Tool.error("Failure in generate bool classifier for Partition Group "+number);
            }
            else{
                T.addBoolClassifier(b);
            }
        }
        DisplayBoolClassifiers();
    }
    
    public void EndProcess(){
        DisplayProgram();
        Tool.endFileWriting();
    }
    
    public void Run(){
        PreProcess();
        GenerateTraceExpressionsForEachSample();
        //GeneratePartition();
        GenerateBoolClassifier();
        EndProcess();
    }
}
