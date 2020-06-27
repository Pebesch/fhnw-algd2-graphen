package graph;

import java.util.ArrayList;
import java.util.List;

public class MyGraph implements Graph {
    private List<Node> nodes;

    public MyGraph() {
        this.nodes = new ArrayList<>();
    }

    @Override
    public int appendNode() {
        // Nodes "from" will be determined by position in list
        // E.g. First Node pos 0; 6th Node pos 5
        nodes.add(new Node());
        return nodes.size() - 1;
    }

    @Override
    public int getNumberOfNodes() {
        return nodes.size();
    }

    @Override
    public void addEdge(int source, int dest, int weight) {
        if(isNodeOutOfBound(source) || isNodeOutOfBound(dest) || source == dest) {
            throw new IllegalArgumentException("Arguments are out of bound");
        }
        Node node = nodes.get(source);
        // Check if connection already exists
        for (int i = 0; i < node.edges.size(); i++) {
            if(node.edges.get(i).neighbor == dest) throw new IllegalArgumentException("Connection already exists");
        }
        node.edges.add(new Edge(dest, weight));
    }

    @Override
    public void addUndirectedEdge(int n1, int n2, int weight) {
        // Add one in both directions
        addEdge(n1, n2, weight);
        addEdge(n2, n1, weight);
    }

    @Override
    public void markNode(int node) {
        if(isNodeOutOfBound(node)) {
            throw new IllegalArgumentException("Node out of bound");
        }
        nodes.get(node).marker = 1;
    }

    @Override
    public void incrementMark(int node) {
        if(isNodeOutOfBound(node)) {
            throw new IllegalArgumentException("Node out of bound");
        }
        nodes.get(node).marker++;
    }

    @Override
    public void decrementMark(int node) {
        if(isNodeOutOfBound(node)) {
            throw new IllegalArgumentException("Node out of bound");
        }
        if(nodes.get(node).marker > 0) {
            nodes.get(node).marker--;
        }
    }

    @Override
    public void unmarkNode(int node) {
        if(isNodeOutOfBound(node)) {
            throw new IllegalArgumentException("Node out of bound");
        }
        nodes.get(node).marker = 0;
    }

    @Override
    public boolean isMarked(int node) {
        if(isNodeOutOfBound(node)) {
            throw new IllegalArgumentException("Node out of bound");
        }
        return nodes.get(node).marker != 0;
    }

    @Override
    public void resetAllMarkers() {
        for (Node node : nodes) {
            node.marker = 0;
        }
    }

    @Override
    public int getNeighbour(int node, int index) {
        if(isNodeOrEdgeOutOfBound(node, index)) {
            throw new IllegalArgumentException("Arguments out of bound");
        }
        return nodes.get(node).edges.get(index).neighbor;
    }

    @Override
    public int getWeight(int node, int index) {
        if(isNodeOrEdgeOutOfBound(node, index)) {
            throw new IllegalArgumentException("Arguments out of bound");
        }
        return nodes.get(node).edges.get(index).weight;
    }

    @Override
    public int getNumberOfNeighbours(int node) {
        if(isNodeOutOfBound(node)) {
            throw new IllegalArgumentException("Node out of bound");
        }
        return nodes.get(node).edges.size();
    }

    @Override
    public void show() {
        System.out.println("-------------------------------------------");
        for (int i = 0; i < nodes.size(); i++) {
            System.out.println("Node: " + i + " (" + (isMarked(i) ? "Markiert" : "Unmarkiert") +")");
            for (int j = 0; j < nodes.get(i).edges.size(); j++) {
                System.out.println('\t' + "To: " + nodes.get(i).edges.get(j).neighbor + ", Gewicht: " + nodes.get(i).edges.get(j).weight);
            }
        }
    }

    private static class Edge {
        int neighbor;
        int weight;
        public Edge(int neighbor, int weight) {
            this.neighbor = neighbor;
            this.weight = weight;
        }
    }

    private static class Node {
        List<Edge> edges = new ArrayList<>();
        int marker;

        public boolean isEdgeOutOfBound(int index) {
            return (index < 0 || index > edges.size());
        }
    }

    private boolean isNodeOutOfBound(int node) {
        return (node < 0 || node > nodes.size());
    }

    private boolean isNodeOrEdgeOutOfBound(int node, int index) {
        if(isNodeOutOfBound(node)) {
            return true;
        } else {
            Node n = nodes.get(node);
            if(n.isEdgeOutOfBound(index)) {
                return true;
            }
        }
        return false;
    }
}
