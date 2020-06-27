package graph.solution;// vtg - version 2 - 2015-06

import java.util.*;


public class main_Graph{
 
  private static final int TOPO = 0;
  private static final int PATH_AND_MST = 1;
  
  private static final int OPERATION = TOPO;

  public static void main(String[] args){
    Graph g = new Graph();
    
    if (OPERATION == PATH_AND_MST){
      for (int i=0; i<7; ++i)  g.appendNode();
      g.addUndirectedEdge(2, 5, 7);
      g.addUndirectedEdge(5, 6, 4);
      g.addUndirectedEdge(2, 3, 6);
      g.addUndirectedEdge(3, 4, 2);
      g.addUndirectedEdge(4, 6, 2);
      g.addUndirectedEdge(3, 0, 5);
      g.addUndirectedEdge(0, 1, 9);
      g.addUndirectedEdge(6, 1, 3);
    }else{
      for (int i=0; i<10; ++i)  g.appendNode();
      g.addEdge(0, 1, 1);
      g.addEdge(0, 2, 1);
      g.addEdge(0, 3, 1);
      g.addEdge(0, 4, 1);
      g.addEdge(0, 8, 1);
      g.addEdge(1, 5, 1);
      g.addEdge(1, 6, 1);
      g.addEdge(2, 7, 1);
      g.addEdge(2, 8, 1);
      g.addEdge(3, 9, 1);
    }
    
    g.show();
    System.out.println("DFS :  " + g.DFS(0));
    System.out.println("BFS :  " + g.BFS(0));
    if (OPERATION == PATH_AND_MST){
      System.out.println();
      shortestPath(g, 0);
      System.out.println();
      System.out.println("minimal spanning tree:");
      Graph mst = minimalSpanningTree(g);
      mst.show();
    }else{
      System.out.println("Topo : " + topoSort(g));
    }
  }
  
  //***** Node class for topological sorting & shortest path *******************
  
  static class ComparableNode implements Comparable<ComparableNode>{
    public int id;
    public int key;
    public ComparableNode(int id, int key) {this.id=id; this.key=key;}
    public int compareTo(ComparableNode n){
      if (key > n.key) return 1;
      if (key < n.key) return -1;
      return 0;
    }
    public String toString(){ return " " + id + "/" + key; }
  }
  
  //***** topological sorting **************************************************
  
  public static ArrayList<Integer> topoSort(Graph g){
    // in this algorithm Node.key is used as in-degree
    int dest, curr;
    g.resetAllMarkers();
    for (int node=0; node<g.getNumberOfNodes(); ++node){
      for (int edge=0; edge<g.getNumberOfNeighbours(node); ++edge){
        dest = g.getNeighbour(node, edge);
        g.incrementMarker(dest);
      }
    }
    PriorityQueue<ComparableNode> pq = new PriorityQueue<>();
    for (int node=0; node<g.getNumberOfNodes(); ++node)
      pq.add(new ComparableNode(node, g.getMarker(node)));
    ArrayList<Integer> result = new ArrayList<>();
    while(!pq.isEmpty() && pq.peek().key==0){
      curr = pq.poll().id;
      result.add(curr);
      for (int edge=0; edge<g.getNumberOfNeighbours(curr); ++edge){
        dest = g.getNeighbour(curr, edge);
        g.setMarker(dest, g.getMarker(dest) - 1);
        pq.add(new ComparableNode(dest, g.getMarker(dest)));
      }
    }
    return result;
  }
  
  //***** shortest paths *******************************************************
  
  public static boolean test_sp(Graph g, int node, int index, ComparableNode[] d){
    if (d[g.getNeighbour(node, index)].key > d[node].key + g.getWeight(node, index)){
      d[g.getNeighbour(node, index)].key = d[node].key + g.getWeight(node, index);
      return true;
    }else{
      return false;
    }
  }
  
  
  public static void show_d(String name, ComparableNode[] d){
    System.out.println(name + ":");
    for (ComparableNode n : d)
      System.out.println(n.id + " : " + n.key);
  }
 
  
  public static void show_pq(PriorityQueue pq){
    System.out.println(pq);
  }
  
  
  public static void shortestPath(Graph g, int start){
    ComparableNode current;
    ComparableNode[] d = new ComparableNode[g.getNumberOfNodes()];
    for (int i=0; i <d.length; ++i)
      d[i] = new ComparableNode(i, Integer.MAX_VALUE);
    d[start].key = 0;
    PriorityQueue<ComparableNode> pq = new PriorityQueue<>();
    pq.add(d[start]);
    while(!pq.isEmpty()){
      current = pq.poll();
      g.setMarker(current.id, 1);
      for (int edge=0; edge<g.getNumberOfNeighbours(current.id); ++edge){
        int neighbour = g.getNeighbour(current.id, edge);
        if (d[neighbour].key == Integer.MAX_VALUE){
          pq.add(d[neighbour]);
        }
        if (test_sp(g, current.id, edge, d)){
          pq.remove(d[neighbour]);  // change priority...
          pq.add(d[neighbour]);
        }
      }
    }
    show_d("shortest paths", d);
  }
  
  // minimal spanning tree *****************************************************
  
  static class ComparableEdge extends ComparableNode{
    private int start;
    public ComparableEdge(int end, int key){
      super(end, key);
    }
    public String toString(){ return start + "/" + id + " k=" + key;} 
  }
  
  
  public static boolean test_mst(Graph g, int node, int index, ComparableEdge[] d){
    if (d[g.getNeighbour(node, index)].key > g.getWeight(node, index)){
      d[g.getNeighbour(node, index)].key = g.getWeight(node, index);
      return true;
    }else{
      return false;
    }
  }
  
  
  public static Graph minimalSpanningTree(Graph g){
    Graph result = new Graph();
    for (int i=0; i<g.getNumberOfNodes(); ++i)  
      result.appendNode();
    ComparableEdge current;
    ComparableEdge[] d = new ComparableEdge[g.getNumberOfNodes()];
    for (int i=0; i <d.length; ++i)
      d[i] = new ComparableEdge(i, Integer.MAX_VALUE);
    PriorityQueue<ComparableEdge> pq = new PriorityQueue<>();
    g.resetAllMarkers();
    d[0].key = 0;   // start arbitrarily from node 0
    pq.add(d[0]);
    int edgeCount = g.getNumberOfNodes() - 1;
    while (edgeCount > 0){
      current = pq.poll();
      g.setMarker(current.id, 1);
      if (current.id != current.start){
        result.addUndirectedEdge(current.start, current.id, current.key);
        --edgeCount;
      }
      for (int edge=0; edge<g.getNumberOfNeighbours(current.id); ++edge){
        int neighbour = g.getNeighbour(current.id, edge);
        if (!g.isMarked(neighbour)){
          if (d[neighbour].key == Integer.MAX_VALUE){
            d[neighbour].start = current.id;
            d[neighbour].key = g.getWeight(current.id, edge);
            pq.add(d[neighbour]);
          }
          if (test_mst(g, current.id, edge, d)){
            pq.remove(d[neighbour]);  // change priority...
            pq.add(d[neighbour]);
          }
        }
      }
    }
    return result;
  }
  
  
  
}
