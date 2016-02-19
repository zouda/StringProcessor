package com.dag;

import java.util.ArrayList;
import com.expression.ExpressionConststr;
import com.expression.ExpressionGroup;
import com.expression.ExpressionSubstr;
import com.position.CPos;
import com.position.Pos;
import com.position.PositionGroup;
import com.regex.Regex;
import com.regex.RegexGroup;

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
        return 1;
    }
    
    private int Size(ExpressionSubstr es){
        return 1;
    }
    
    private int Size(ExpressionConststr ec){
        return 1;
    }
    
    private int Size(PositionGroup pg){
        return 1;
    }
    
    private int Size(CPos p){
        return 1;
    }
    
    private int Size(Pos p){
        return 1;
    }
    
    private int Size(RegexGroup rg){
        return 1;
    }
    
    private int Size(Regex r){
        return 1;
    }
}
