package graph;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

public class GraphUtilities {

    public ArrayList<Integer> topologicalSort(Graph g) {
        ArrayList<Integer> result = new ArrayList<>();
        g.resetAllMarkers();

        // Set the marker to indegree
        int node;
        for(int nodeId = 0; nodeId < g.getNumberOfNodes(); nodeId++) {
          for(int edgeId = 0; edgeId < g.getNumberOfNeighbours(nodeId); edgeId++) {
              node = g.getNeighbour(nodeId, edgeId);
              g.incrementMark(node);
          }
        }
        g.show();

        // Collect all nodes with indegree 0
        PriorityQueue set = new PriorityQueue();
        for(int nodeId = 0; nodeId < g.getNumberOfNodes(); nodeId++) {
            if(!g.isMarked(nodeId)) {
                set.add(nodeId);
            }
        }

        // As long as set is not empty: Remove a node and decrement all neighbors marker
        while (!set.isEmpty()) {
            Integer removedNode = (Integer)set.poll();
            result.add(removedNode);
            // Decrement all neighboring nodes' mark
            for(int edgeId = 0; edgeId < g.getNumberOfNeighbours(removedNode); edgeId++) {
                int neighboringNode = g.getNeighbour(removedNode, edgeId);
                g.decrementMark(neighboringNode);
                // Add node to set if mark reached 0
                if(!g.isMarked(neighboringNode)) {
                    set.add(neighboringNode);
                }
            }
        }

        if(result.size() != g.getNumberOfNodes()) {
            throw new IllegalStateException("Graph not feasible for topological sorting");
        }

        return result;
    }

    public Graph minimalSpanningTree(Graph g) {
        return new MyGraph();
    }

}
