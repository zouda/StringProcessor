package com.dag;

import java.util.ArrayList;

import com.Global;
import com.expression.*;
import com.position.*;

/*
 * DAG structure: represent trace expression
 */
public class DAG {
    private ArrayList<Node> NodeList;
    private ArrayList<Edge> EdgeList;
    private Node StartNode;
    private Node EndNode;
    private int size = 0;
    private boolean sizePreComputed = false;
    private int dim = 0;
    private int[] dimSize;
    
    public DAG(){
        dimSize = new int[Global.MAX_LABEL_NUMBER];
        NodeList = new ArrayList<Node>();
        EdgeList = new ArrayList<Edge>();
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
    
    public Edge getEdgeAt(int pos){
        return this.EdgeList.get(pos);
    }

    public int getEdgeNumber() {
        return this.EdgeList.size();
    }
    
    public int getSize(){
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
        for (int i = 0; i < EdgeList.size(); i++){
            Edge e = EdgeList.get(i);
            Node s = e.getSource();
            Node t = e.getTarget();
            t.setSize(t.getSize() + s.getSize() * Size(e.getExpressionGroup()));
        }
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
            return Size((ExpressionConststr)e);
    }

    private int Size(ExpressionSubstr es){
        PositionGroup pg1 = es.getPositionGroup1();
        PositionGroup pg2 = es.getPositionGroup2();
        return Size(pg1)*Size(pg2);
    }
    
    private int Size(ExpressionConststr ec){
        return 1;
    }
    
    private int Size(PositionGroup pg){
        return pg.getSize();
    }
    
    public static boolean Comp(DAG d1, DAG d2){
        DAG d = IntersectDAG(d1, d2);
        if (d.isCovered())
            return false;
        else
            return true;
    }
    
    public static DAG IntersectDAG(DAG d1, DAG d2) {
        DAG d = new DAG();
        d.setDim(d1.getDim()+d2.getDim());
        for (int i = 0; i < d1.dim; i++){
            d.dimSize[i] = d1.dimSize[i];
        }
        for (int i = 0; i < d2.dim; i++){
            d.dimSize[i+d1.dim] = d2.dimSize[i];
        }
        for (int i = 0; i < d1.getNodeNumber(); i++){
            for (int j = 0; j < d2.getNodeNumber(); j++){
                Node n = new Node(d1.getNodeAt(i), d2.getNodeAt(j));
                d.addNode(n);
                if (i == 0 && j == 0){
                    d.setStartNode(n);
                }
                if ((i == d1.getNodeNumber()-1) && (j == d2.getNodeNumber()-1)){
                    d.setEndNode(n);
                }
            }
        }
        for (int i = 0; i < d1.getNodeNumber(); i++){
            for (int j = 0; j < d2.getNodeNumber(); j++){
                Node u = d.getNodeByNode(new Node(d1.getNodeAt(i), d2.getNodeAt(j)));
                for (int k = 0; k < d1.getNodeAt(i).getPathSize(); k++){
                    for (int l = 0; l < d2.getNodeAt(j).getPathSize(); l++){
                        Edge e1 = d1.getNodeAt(i).getPathAt(k);
                        Edge e2 = d2.getNodeAt(j).getPathAt(l);
                        Node t1 = e1.getTarget();
                        Node t2 = e2.getTarget();
                        Node v = d.getNodeByNode(new Node(t1, t2));
                        Edge e = new Edge(u,v);
                        ExpressionGroup eg = Intersect(e1.getExpressionGroup(), e2.getExpressionGroup());
                        e.setExpressionGroup(eg);
                        d.addEdge(e);
                    }
                }
            }
        }
        return d;
    }
    
    private Node getNodeByNode(Node node) {
        int[] label = node.getLabel();
        int dim = node.getDim();
        int index = label[0];
        for (int i = 1; i < dim; i++){
            index += index * this.dimSize[i-1] + label[i];
        }
        return this.getNodeAt(index);
    }
    
    private int getNodeIndexByNode(Node node) {
        int[] label = node.getLabel();
        int dim = node.getDim();
        int index = label[0];
        for (int i = 1; i < dim; i++){
            index += index * this.dimSize[i-1] + label[i];
        }
        return index;
    }

    private static ExpressionGroup Intersect(ExpressionGroup eg1, ExpressionGroup eg2){
        ExpressionGroup eg = new ExpressionGroup();
        for (int i = 0; i < eg1.getSize(); i++){
            for (int j = 0; j < eg2.getSize(); j++){
                Expression e1 = eg1.getExpressionAt(i);
                Expression e2 = eg2.getExpressionAt(j);
                if ((e1 instanceof ExpressionSubstr) && (e2 instanceof ExpressionSubstr)){
                    ExpressionSubstr e = Intersect((ExpressionSubstr)e1, (ExpressionSubstr)e2);
                    if (e != null){
                        eg.addExpression(e);
                    }
                } else
                if ((e1 instanceof ExpressionConststr) && (e2 instanceof ExpressionConststr)){
                    ExpressionConststr e = Intersect((ExpressionConststr)e1, (ExpressionConststr)e2);
                    if (e != null){
                        eg.addExpression(e);
                    }
                }
            }
        }
        if (eg.getSize() == 0){
            return null;
        } else{
            return eg;
        }
    }

    private static ExpressionConststr Intersect(ExpressionConststr e1, ExpressionConststr e2) {
        String s1 = e1.getConstStr();
        String s2 = e2.getConstStr();
        if (s1.equals(s2))
            return (new ExpressionConststr(s1));
        else
            return null;
    }
    
    private static ExpressionSubstr Intersect(ExpressionSubstr e1, ExpressionSubstr e2) {
        PositionGroup pg1 = Intersect(e1.getPositionGroup1(), e2.getPositionGroup1());
        PositionGroup pg2 = Intersect(e1.getPositionGroup2(), e2.getPositionGroup2());
        if (pg1 != null && pg2 != null)
            return (new ExpressionSubstr(pg1, pg2));
        return null;
    }
    
    private static PositionGroup Intersect(PositionGroup pg1, PositionGroup pg2) {
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
                    Pos p = Intersect((Pos)p1, (Pos)p2);
                    if (p != null){
                        pg.AddPosition(p);
                    }
                }
            }
        }
        return null;
    }

    private static CPos Intersect(CPos p1, CPos p2) {
        if (p1.getPos() == p2.getPos())
            return p1;
        else
            return null;
    }
    
    private static Pos Intersect(Pos p1, Pos p2) {
        if (p1.getRegex1().equals(p2.getRegex1()) 
            && p1.getRegex2().equals(p2.getRegex2()) 
            && (p1.getC() == p2.getC())){
            return p1;
        } else {
            return null;
        }
    }
    
    //if a path from StartNode to EndNode found(Flood Flow)
    public boolean isCovered(){
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
                int index = this.getNodeIndexByNode(v);
                if (!flag[index]){
                    flag[index] = true;
                    queue[bot++] = index;
                }
            }
            if (top == bot) break;
        }
        return flag[this.NodeList.size()-1];
    }
}
