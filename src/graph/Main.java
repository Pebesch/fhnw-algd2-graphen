package graph;

public class Main {
    public static void main(String[] args) {
        Graph graph = new MyGraph();
        for(int i = 0; i < 6; i++) {
            graph.appendNode();
        }

        graph.addEdge(0, 3, 4);
        graph.addEdge(0, 1, 5);
        graph.addEdge(1, 3, 5);
        graph.addEdge(2, 0, 4);
        graph.addEdge(2, 1, 3);
        graph.addEdge(2, 3, 8);
        graph.addEdge(3, 1, 6);
        graph.addEdge(5, 4, 1);

        graph.show();
    }
}
