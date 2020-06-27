package graph;

public class Main {
    public static void main(String[] args) {
        // Regular
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

        // Topological
        GraphUtilities utilities = new GraphUtilities();
        Graph topologicalGraph = new MyGraph();
        for (int i=0; i<10; ++i) topologicalGraph.appendNode();
        topologicalGraph.addEdge(0, 1, 1);
        topologicalGraph.addEdge(0, 2, 1);
        topologicalGraph.addEdge(0, 3, 1);
        topologicalGraph.addEdge(0, 4, 1);
        topologicalGraph.addEdge(0, 8, 1);
        topologicalGraph.addEdge(1, 5, 1);
        topologicalGraph.addEdge(1, 6, 1);
        topologicalGraph.addEdge(2, 7, 1);
        topologicalGraph.addEdge(2, 8, 1);
        topologicalGraph.addEdge(3, 9, 1);
        topologicalGraph.show();
        System.out.println("Topo : " + utilities.topologicalSort(topologicalGraph));
    }
}
