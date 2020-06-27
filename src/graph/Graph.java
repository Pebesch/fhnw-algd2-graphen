package graph;

public interface Graph {
    int appendNode();
    int getNumberOfNodes();
    void addEdge(int source, int dest, int weight);
    void addUndirectedEdge(int n1, int n2, int weight);
    void markNode(int node);
    void unmarkNode(int node);
    void incrementMark(int node);
    void decrementMark(int node);
    boolean isMarked(int node);
    void resetAllMarkers();
    int getNeighbour(int node, int index);
    int getWeight(int node, int index);
    int getNumberOfNeighbours(int node);
    void show();
}
