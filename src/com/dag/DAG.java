package com.dag;

import java.util.ArrayList;

import com.Global;
import com.Sample;
import com.Tool;
import com.expression.*;
import com.position.*;

/*
 * DAG structure: represent trace expression
 */
public class DAG {
    private ArrayList<Node> NodeList;
    private ArrayList<Edge> EdgeList;
    private ArrayList<Sample> SampleList;
    private Node StartNode;
    private Node EndNode;
    private double size = 0;
    private boolean sizePreComputed = false;
    private int dim = 0;
    private int[] dimSize;
    
    public DAG(){
        dimSize = new int[Global.MAX_LABEL_NUMBER];
        NodeList = new ArrayList<Node>();
        EdgeList = new ArrayList<Edge>();
        SampleList = new ArrayList<Sample>();
    }
    
    public void setSample(Sample s){
        SampleList.add(s);
    }
    
    public void addSample(Sample s){
        SampleList.add(s);
    }
    
    public ArrayList<Sample> getSampleList(){
        return this.SampleList;
    }
    
    public int getDim(){
        return this.dim;
    }
    
    public void setDim(int dim){
        this.dim = dim;
    }
    
    public void setOneDimSize(int dimsize) {
        dimSize[0] = dimsize;
    }
    
    public Node getEndNode() {
        return EndNode;
    }
    
    public void setEndNode(Node endNode) {
        EndNode = endNode;
    }
    
    public Node getStartNode() {
        return StartNode;
    }
    
    public void setStartNode(Node startNode) {
        StartNode = startNode;
    }
    
    public ArrayList<Node> getNodeList() {
        return NodeList;
    }
    
    public void setNodeList(ArrayList<Node> nodeList) {
        NodeList = nodeList;
    }
    
    public ArrayList<Edge> getEdgeList() {
        return EdgeList;
    }
    
    public void setEdgeList(ArrayList<Edge> edgeList) {
        EdgeList = edgeList;
    }
    
    public void addEdge(Edge e){
        this.EdgeList.add(e);
    }
    
    public void addNode(Node n){
        this.NodeList.add(n);
    }

    public Node getNodeAt(int pos){
        return this.NodeList.get(pos);
    }
    
    public int getNodeNumber() {
        return this.NodeList.size();
    }
    
    public Edge getEdgeAt(int s, int t, int len){
        int pos = 0;
        for (int i = 0; i <= s-1; i++){
            pos += (len - i + 1);
        }
        pos += (t-s);
        pos -= 1;
        return this.EdgeList.get(pos);
    }
    
    public int getEdgeNumber() {
        return this.EdgeList.size();
    }
    
    public double getSize(){
        if (this.sizePreComputed){
            return this.size;
        }else{
            computeSize();
            this.sizePreComputed = true;
            return this.size;
        }
    }
    
    private void computeSize(){
        StartNode.setSize(1);
        for (int i = 0; i < NodeList.size(); i++){
            Node u = NodeList.get(i);
            u.SizeCompleted = true;
            for (int j = 0; j < u.getPathSize(); j++){
                Edge e = u.getPathAt(j);
                Node v = e.getTarget();
                if (v.SizeCompleted){
                    Tool.error("Error in computeSize");
                }
                v.setSize(v.getSize() + u.getSize() * Size(e.getExpressionGroup()));
            }
        }
        this.size = EndNode.getSize();
    }
    
    private int Size(ExpressionGroup eg){
        int size = 0;
        for (int i = 0; i < eg.getSize(); i++){
            size += Size(eg.getExpressionAt(i));
        }
        return size;
    }
    
    private int Size(Expression e) {
        if (e instanceof ExpressionSubstr)
            return Size((ExpressionSubstr)e);
        else
        if (e instanceof ExpressionConststr)
            return Size((ExpressionConststr)e);
        else
            return Size((ExpressionLoop)e);
    }

    private int Size(ExpressionSubstr es){
        PositionGroup pg1 = es.getPositionGroup1();
        PositionGroup pg2 = es.getPositionGroup2();
        return Size(pg1)*Size(pg2);
    }
    
    private int Size(ExpressionConststr ec){
        return 1;
    }
    
    private int Size(ExpressionLoop el){
        return (int)el.getDAG().getSize();
    }
    
    private int Size(PositionGroup pg){
        return pg.getSize();
    }
    
    public static boolean Comp(DAG d1, DAG d2){
        DAG d = IntersectDAG(d1, d2, false);
        if (d.isCovered())
            return true;
        else
            return false;
    }
    
    public static DAG IntersectDAG(DAG d1, DAG d2, boolean isLoop) {
        DAG d = new DAG();
        Tool.TimerStart();
        d.setDim(d1.getDim()+d2.getDim());
        for (int i = 0; i < d1.dim; i++){
            d.dimSize[i] = d1.dimSize[i];
            d.addSample(d1.SampleList.get(i));
        }
        for (int i = 0; i < d2.dim; i++){
            d.dimSize[i+d1.dim] = d2.dimSize[i];
            d.addSample(d2.SampleList.get(i));
        }
        for (int i = 0; i < d1.getNodeNumber(); i++){
            for (int j = 0; j < d2.getNodeNumber(); j++){
                Node n = new Node(d1.getNodeAt(i), d2.getNodeAt(j));
                n.Isolated = true;
                d.addNode(n);
                if (i == 0 && j == 0){
                    d.setStartNode(n);
                }
                if ((i == d1.getNodeNumber()-1) && (j == d2.getNodeNumber()-1)){
                    d.setEndNode(n);
                }
            }
        }
        int count = 0;
        for (int i = 0; i < d1.getNodeNumber(); i++){
            for (int j = 0; j < d2.getNodeNumber(); j++){
                Node u = d.NodeList.get(i * d2.getNodeNumber() + j);
                for (int k = 0; k < d1.getNodeAt(i).getPathSize(); k++){
                    for (int l = 0; l < d2.getNodeAt(j).getPathSize(); l++){
                        count++;
                        Edge e1 = d1.getNodeAt(i).getPathAt(k);
                        Edge e2 = d2.getNodeAt(j).getPathAt(l);
                        Node t1 = e1.getTarget();
                        Node t2 = e2.getTarget();
                        Node v = d.NodeList.get(t1.getIndex() * d2.getNodeNumber() + t2.getIndex());
                        
                        ExpressionGroup eg = Intersect(e1.getExpressionGroup(), e2.getExpressionGroup(), isLoop);                        
                        if (eg != null){
                            Edge e = new Edge(u,v);
                            e.setExpressionGroup(eg);
                            d.addEdge(e);
                            u.addPath(e);
                            u.Isolated = false;
                            v.Isolated = false;
                        }
                    }
                }
            }
        }
        Tool.TimerEnd();
        Tool.SumTime();
        int index = d.NodeList.size()-1;
        while (true){
            if (d.NodeList.get(index).Isolated){
                d.NodeList.remove(index);
            }
            index--;
            if (index == -1)
                break;
        }
        for (int i = 0; i < d.NodeList.size(); i++){
            d.NodeList.get(i).setIndex(i);
        }
        //System.out.println(count);
        
        Global.Count_total += count;
        return d;
    }
    
    public static DAG Unify(DAG d1, DAG d2){
        return IntersectDAG(d1, d2, true);
    }

    private static ExpressionGroup Intersect(ExpressionGroup eg1, ExpressionGroup eg2, boolean isLoop){
        ExpressionGroup eg = new ExpressionGroup();
        for (int i = 0; i < eg1.getSize(); i++){
            for (int j = 0; j < eg2.getSize(); j++){
                Expression e1 = eg1.getExpressionAt(i);
                Expression e2 = eg2.getExpressionAt(j);
                if ((e1 instanceof ExpressionSubstr) && (e2 instanceof ExpressionSubstr)){
                    ExpressionSubstr e = Intersect((ExpressionSubstr)e1, (ExpressionSubstr)e2, isLoop);
                    if (e != null){
                        eg.addExpression(e);
                    }
                } else if ((e1 instanceof ExpressionConststr) && (e2 instanceof ExpressionConststr)){
                    ExpressionConststr e = Intersect((ExpressionConststr)e1, (ExpressionConststr)e2);
                    if (e != null){
                        eg.addExpression(e);
                    }
                } else if ((e1 instanceof ExpressionLoop) && (e2 instanceof ExpressionLoop)){
                    ExpressionLoop e = Intersect((ExpressionLoop)e1, (ExpressionLoop)e2);
                    if (e != null){
                        eg.addExpression(e);
                    }
                }
                    
            }
        }
        if (eg.getSize() == 0)
            return null;
        return eg;
    }

    private static ExpressionConststr Intersect(ExpressionConststr e1, ExpressionConststr e2) {
        String s1 = e1.getConstStr();
        String s2 = e2.getConstStr();
        if (s1.equals(s2))
            return (new ExpressionConststr(s1));
        else
            return null;
    }
    
    private static ExpressionSubstr Intersect(ExpressionSubstr e1, ExpressionSubstr e2, boolean isLoop) {
        PositionGroup pg1 = Intersect(e1.getPositionGroup1(), e2.getPositionGroup1(), isLoop);
        PositionGroup pg2 = Intersect(e1.getPositionGroup2(), e2.getPositionGroup2(), isLoop);
        if (pg1 != null && pg2 != null)
            return (new ExpressionSubstr(pg1, pg2));
        return null;
    }
    
    private static ExpressionLoop Intersect(ExpressionLoop e1, ExpressionLoop e2) {
        DAG d = Unify(e1.getDAG(), e2.getDAG());
        if (d != null)
            return (new ExpressionLoop(d));
        return null;
    }
    
    private static PositionGroup Intersect(PositionGroup pg1, PositionGroup pg2, boolean isLoop) {
        PositionGroup pg = new PositionGroup();
        for (int i = 0; i < pg1.getSize(); i++){
            for (int j = 0; j < pg2.getSize(); j++){
                Position p1 = pg1.getPositionAt(i);
                Position p2 = pg2.getPositionAt(j);
                if ((p1 instanceof CPos) && (p2 instanceof CPos)){
                    CPos p = Intersect((CPos)p1, (CPos)p2);
                    if (p != null){
                        pg.AddPosition(p);
                    }
                } else
                if ((p1 instanceof Pos) && (p2 instanceof Pos)){
                    Pos p = Intersect((Pos)p1, (Pos)p2, isLoop);
                    if (p != null){
                        pg.AddPosition(p);
                    }
                }
            }
        }
        if (pg.getSize() == 0)
            return null;
        return pg;
    }

    private static CPos Intersect(CPos p1, CPos p2) {
        if (p1.getPos() == p2.getPos())
            return p1;
        else
            return null;
    }
    
    private static Pos Intersect(Pos p1, Pos p2, boolean isLoop) {
        if (isLoop){
            if (p1.getRegex1().equals(p2.getRegex1()) 
                && p1.getRegex2().equals(p2.getRegex2())){
                    return (new Pos(p1.getRegex1(), p1.getRegex2(), 0));
                } else {
                    return null;
                }
        }
        if (p1.getRegex1().equals(p2.getRegex1()) 
            && p1.getRegex2().equals(p2.getRegex2()) 
            && (p1.getC() == p2.getC())){
            return p1;
        } else {
            return null;
        }
    }
    
    //if a path from StartNode to EndNode found(FloodFill)
    public boolean isCovered(){
        if (this.NodeList.size() == 0){
            return false;
        }
        boolean[] flag = new boolean[this.NodeList.size()];
        int[] queue = new int[this.NodeList.size()];
        int top = -1, bot = 0;
        flag[0] = true;
        queue[0] = 0;
        while (true){
            top++;
            Node u = this.NodeList.get(queue[top]);
            for (int i = 0; i < u.getPathSize(); i++){
                Edge e = u.getPathAt(i);
                Node v = e.getTarget();
                int index = v.getIndex();
                if (!flag[index]){
                    flag[index] = true;
                    queue[++bot] = index;
                }
            }
            if (top == bot) break;
        }
        return flag[this.NodeList.size()-1];
    }
    
    private static void DFS(DAG d, Node n, Route route, RouteGroup rg){
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
    
    public static RouteGroup TraverseDAG(DAG d){
        RouteGroup rg = new RouteGroup();
        DFS(d, d.getStartNode(), new Route(), rg);
        if (rg.size() == 0)
            return null;
        return rg;
    }

    public void Print(boolean printNumber, boolean isLoop) {
        RouteGroup rg = DAG.TraverseDAG(this);
        for (int j = 0; j < rg.size(); j++){
            rg.get(j).Print();
            if (!isLoop)
                Tool.println("");
        }
        if (printNumber){
            Tool.print("#");
            Tool.print("Total: ");
            Tool.print(rg.getNumber());
            Tool.println("");
        }
    }
}


