package graph.solution;//******************************************************************************
//  FHNW.ALGD2  -  Excercise 9 : Graph                                         *
// --------------------------------------------------------------------------  *
//  version 1  (2014-01-07)                                               vtg  *
//  version 2  (2014-06-03) marker API changed                                 *
//******************************************************************************

import java.util.*;

public class Graph{
  
  public Graph(){
    nodes = new ArrayList<>();
  }
  
//***** basic API **************************************************************
  
  public int appendNode(){
    nodes.add(new Node());
    return nodes.size() - 1;
  }
  
  public int getNumberOfNodes(){
    return nodes.size();
  }
  
  public void addEdge(int source, int dest, int weight){
    if (source<0 || source>=nodes.size() || dest<0 || dest>=nodes.size() || source==dest)
      throw new IndexOutOfBoundsException("invalid node number(s)");
    Node n = nodes.get(source);
    for (int i=0; i<n.edges.size(); ++i){
      if (n.edges.get(i).neighbour == dest)
        throw new ConnectionAlreadyExistsException();
    }
    n.edges.add(new Edge(dest, weight));
  }
  
  public void addUndirectedEdge(int n1, int n2, int weight){
    addEdge(n1, n2, weight);
    addEdge(n2, n1, weight);
  }
  
  // markers
  
  public void incrementMarker(int node){
    if (node<0 || node>=nodes.size())
      throw new IndexOutOfBoundsException("invalid node number");
    ++(nodes.get(node).marker);
  }
  
  public void decrementMarker(int node){
    if (node<0 || node>=nodes.size())
      throw new IndexOutOfBoundsException("invalid node number");
    --(nodes.get(node).marker);
  }
  
  public void setMarker(int node, int value){
    if (node<0 || node>=nodes.size())
      throw new IndexOutOfBoundsException("invalid node number");
    nodes.get(node).marker = value;
  }
  
  public int getMarker(int node){
    if (node<0 || node>=nodes.size())
      throw new IndexOutOfBoundsException("invalid node number");
    return nodes.get(node).marker;
  }
  
  
  public void markNode(int node){
    if (node<0 || node>=nodes.size())
      throw new IndexOutOfBoundsException("invalid node number");
    nodes.get(node).marker = 1;
  }
  
  public void unmarkNode(int node){
    if (node<0 || node>=nodes.size())
      throw new IndexOutOfBoundsException("invalid node number");
    nodes.get(node).marker = 0;
  }
  
  public boolean isMarked(int node){
    if (node<0 || node>=nodes.size())
      throw new IndexOutOfBoundsException("invalid node number");
    return nodes.get(node).marker != 0;
  }
  
  public void resetAllMarkers(){
    for (Node n : nodes)
      n.marker = 0;
  }
  
  public int getNeighbour(int node, int index){
    if (index<0 || index >= nodes.get(node).edges.size())
      throw new NoSuchElementException();
    return nodes.get(node).edges.get(index).neighbour;
  }
  
  public int getWeight(int node, int index){
    if (index<0 || index >= nodes.get(node).edges.size())
      throw new NoSuchElementException();
    return nodes.get(node).edges.get(index).weight;
  }
  
  public int getNumberOfNeighbours(int node){
    if (node<0 || node>=nodes.size())
      throw new IndexOutOfBoundsException("invalid node number");
    return nodes.get(node).edges.size();
  }
  
  public void show(){
    //System.out.println("number of nodes: " + nodes.size());
    //for (int i=0; i<nodes.size(); ++i){
    //  System.out.println(String.format("%4d", i) + " " + String.format("%4d", nodes.get(i).marker));
    //}
    System.out.println();
    System.out.println("source destination weight");
    System.out.println("--------------------------");
    for (int i=0; i<nodes.size(); ++i){
      Node node = nodes.get(i);
      for (Edge edge : node.edges){
        System.out.println(String.format("%4d", i) + String.format("%9d", edge.neighbour) 
                                                   + String.format("%10d", edge.weight));
      }
    }
    System.out.println();
  }
  
//***** traversal **************************************************************
  
  public ArrayList<Integer> DFS(int start){
    
    if (start<0 || start>=nodes.size())
      throw new IndexOutOfBoundsException("invalid node number");
    ArrayList<Integer> result = new ArrayList<>();
    Stack<Integer> s = new Stack<>();
    resetAllMarkers();
    s.push(start);
    markNode(start);
    while (!s.isEmpty()){
      int current = s.pop();
      result.add(current);
      for (int i=0; i<getNumberOfNeighbours(current); ++i){
        int n = getNeighbour(current, i);
        if (!isMarked(n)){
          s.push(n);
          markNode(n);
        }
      }
    }
    return result;
  }
  
  public ArrayList<Integer> BFS(int start){
    
    if (start<0 || start>=nodes.size())
      throw new IndexOutOfBoundsException("invalid node number");
    ArrayList<Integer> result = new ArrayList<>();
    LinkedList<Integer> s = new LinkedList<>();
    resetAllMarkers();
    s.addFirst(start);
    markNode(start);
    while (!s.isEmpty()){
      int current = s.pollLast();
      result.add(current);
      for (int i=0; i<getNumberOfNeighbours(current); ++i){
        int n = getNeighbour(current, i);
        if (!isMarked(n)){
          s.addFirst(n);
          markNode(n);
        }
      }
    }
    return result;
  }
  
  
//***** attributes & nested classes*********************************************
  ArrayList<Node> nodes;
  
  private static class Edge{
    int weight;
    int neighbour;
    public Edge(int neighbour, int weight){
      this.neighbour = neighbour;
      this.weight = weight;
    }
  }
  
  private static class Node{
    ArrayList<Edge> edges = new ArrayList<>();
    int marker;
  }
  
  public class ConnectionAlreadyExistsException extends RuntimeException{}

}
