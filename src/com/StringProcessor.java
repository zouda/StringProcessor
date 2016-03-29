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
	
	public StringProcessor(){
        T = new DAGGroup();
	    SampleList = new ArrayList<Sample>();
	    MatchSet = new MatchGroup();
	}
	
    private void GenerateAllMatch(){
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
	
    private void InputSamples(){
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
	
    private DAG GenerateDAG(Sample sample){
        DAG dag = new DAG();
        String s = sample.getOutput();
        for (int i = 0; i <= s.length(); i++){
            Node n = new Node(i);
            n.Isolated = false;
            n.setIndex(i);
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
	
    private ExpressionGroup GenerateSubstring(Sample sample, int L, int R){
        ExpressionGroup eg = new ExpressionGroup(sample, L, R);
        String target = sample.getOutput().substring(L, R);
        String source = sample.getInput();
        int offset = 0;
        while (true){
            int pos = source.indexOf(target);
            if (pos == -1) 
                break;
            PositionGroup pg1 = getPositionGroup(sample, pos+offset);
            PositionGroup pg2 = getPositionGroup(sample, pos+offset+target.length());
            if (pg1 != null && pg2 != null){
                ExpressionSubstr es = new ExpressionSubstr(pg1, pg2);
                eg.addExpression(es);
            }
            offset += (pos + 1);
            source = source.substring(pos+1, source.length());
        }
        return eg;
    }
	
    private PositionGroup getPositionGroup(Sample sample, int pos){
		return sample.getPositionGroupAt(pos);
    }
    
    public void GenerateLoop(Sample sample, DAG W){
        /* Algorithm:
         * 
         * enumerate k1,k2,k3
         * generate DAG for output[k1,k2] and output[k2,k3], say d1, d2
         * d = unify(d1,d2)
         * construct s = d(input)
         * if s matches output[k1,k4], add Loop(d) to output[k1,k4]
         */
        String output = sample.getOutput();
        for (int k1 = 0; k1 < output.length(); k1++){
            for (int k2 = k1 + 1; k2 < output.length(); k2++){
                for (int k3 = k2 + 1; k3 < output.length(); k3++){
                    DAG d1 = GenerateDAG(new Sample(sample.getInput(), output.substring(k1, k2)));
                    DAG d2 = GenerateDAG(new Sample(sample.getInput(), output.substring(k2, k3)));
                    
                    DAG d = DAG.Unify(d1, d2);
                    if (d == null)
                        continue;
                    
                    String temp = ConstructLoopOutput(sample.getInput(), d);
                    if (temp == null)
                        continue;
                    
                    ExpressionLoop el = new ExpressionLoop(d);
                    for (int k4 = k1 + 1; k4 < output.length(); k4 ++){
                        if (output.substring(k1, k4).equals(temp)){
                            Edge e = W.getEdgeAt(k1 * output.length() + k4);
                            e.getExpressionGroup().addExpression(el);
                        }
                    }
                }
            }
        }
    }
    
    private String ConstructLoopOutput(String input, DAG d) {
        RouteGroup rg = TraverseDAG(d);
        return null;
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
            SampleSet s2_ = s2.Clone();
            MatchGroup d = new MatchGroup();
            while (!s2_.isEmpty()){
                SampleSet old_s2 = s2_.Clone();
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
                s2_.clearUnMatchedWith(MatchSet.getMatchAt(best));
                d.addMatch(MatchSet.getMatchAt(best));
                if (old_s2.getSize() == s2_.getSize())
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
    
    private void DFS(DAG d, Node n, Route route, RouteGroup rg){
        if (n == d.getEndNode()){
            Route r = route.clone();
            rg.add(r);
            return;
        }
        for (int i = 0; i < n.getPathSize(); i++){
            Edge e = n.getPathAt(i);
            route.add(e);
            DFS(d, e.getTarget(), route, rg);
            route.remove(route.size()-1);
        }
    }
    
    private RouteGroup TraverseDAG(DAG d){
        RouteGroup rg = new RouteGroup();
        DFS(d, d.getStartNode(), new Route(), rg);
        return rg;
    }
    
    private void DisplayProgram(){
        Tool.println("== Program ==");
        for (int i = 0; i < T.getDAGNumber(); i++){
            int number = i + 1;
            Tool.print("Group #"+number+":\n");
            RouteGroup rg = TraverseDAG(T.getDAGAt(i));
            for (int j = 0; j < rg.size(); j++){
                rg.get(j).Print();
            }
            Tool.print("#");
            Tool.print("Total: ");
            Tool.print(rg.getNumber());
            Tool.println("");
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
            //GenerateLoop(sample, dag);
            T.addDAG(dag);
        }
    }
    
    public void GeneratePartition(){
        while (T.ExistCompPair()){
            DAGPair dp = T.FindLargestCSPair();
            DAG d1 = T.getDAGAt(dp.getIndex1());
            DAG d2 = T.getDAGAt(dp.getIndex2());
            DAG newDAG = DAG.IntersectDAG(d1, d2, false);
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
                T.addBoolClassifier(null);
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
        Tool.printTime();
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
            if (b == null){
                Tool.print("NULL");
                continue;
            }
            for (int j = 0; j < b.getConjunctNumber(); j++){
                if (j > 0){
                    Tool.print(" || ");
                }
                MatchGroup d = b.getConjuctAt(j);
                for (int k = 0; k < d.getSize(); k++){
                    if (k > 0){
                        Tool.print(" && ");
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
    
    public void Run(){
        PreProcess();
        GenerateTraceExpressionsForEachSample();
        GeneratePartition();
        GenerateBoolClassifier();
        EndProcess();
    }
}
