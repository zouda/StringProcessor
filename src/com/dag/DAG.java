package com.dag;

import java.util.ArrayList;
import com.expression.*;
import com.position.*;
import com.regex.*;

/*
 * DAG structure: record trace expression information
 */
public class DAG {
    private ArrayList<Node> NodeList;
    private ArrayList<Edge> EdgeList;
    private Node StartNode;
    private Node EndNode;
    private int size = 0;
    private boolean sizePreComputed = false;
    
    public DAG(){
        NodeList = new ArrayList<Node>();
        EdgeList = new ArrayList<Edge>();
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
    
    public boolean isVoid(){
        return false;
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
}
