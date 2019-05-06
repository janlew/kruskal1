import java.util.ArrayList;
import java.util.Collections;

public class Kruskal {
    public static void main(String[] args) {
        ArrayList<Edge> graphEdges = new ArrayList<>();
        graphEdges.add(new Edge(3, 5, 2));
        graphEdges.add(new Edge(6, 7, 5));
        graphEdges.add(new Edge(3, 4, 6));
        graphEdges.add(new Edge(4, 8, 7));
        graphEdges.add(new Edge(1, 2, 9));
        graphEdges.add(new Edge(4, 5, 11));
        graphEdges.add(new Edge(1, 6, 14));
        graphEdges.add(new Edge(1, 7, 15));
        graphEdges.add(new Edge(5, 8, 16));
        graphEdges.add(new Edge(3, 6, 18));
        graphEdges.add(new Edge(3, 8, 19));
        graphEdges.add(new Edge(7, 5, 20));
        graphEdges.add(new Edge(2, 3, 24));
        graphEdges.add(new Edge(7, 8, 44));
        graphEdges.add(new Edge(6, 5, 30));

        int nodeCount = 8;

        Kruskal graph = new Kruskal();
        graph.kruskalMST(graphEdges, nodeCount);
    }

    public void kruskalMST(ArrayList<Edge> graphEdges, int nodeCount) {
        String outputMessage = "";

        Collections.sort(graphEdges);
        ArrayList<Edge> mstEdges = new ArrayList<Edge>();

        DisjointSet nodeSet = new DisjointSet(nodeCount + 1);        //Initialize singleton sets for each node in graph. (nodeCount +1) to account for arrays indexing from 0

        for (int i = 0; i < graphEdges.size() && mstEdges.size() < (nodeCount - 1); i++) {        //loop over all edges. Start @ 1 (ignore 0th as placeholder). Also early termination when number of edges=(number of nodes-1)
            Edge currentEdge = graphEdges.get(i);
            int root1 = nodeSet.find(currentEdge.getU());        //Find root of 1 vertex of the edge
            int root2 = nodeSet.find(currentEdge.getV());

            if (root1 != root2) {            //if roots are in different sets the DO NOT create a cycle
                mstEdges.add(currentEdge);        //add the edge to the graph
                nodeSet.union(root1, root2);    //union the sets
            }
        }

        outputMessage += "Final Minimum Spanning Tree (" + mstEdges.size() + " edges)\n";
        int mstTotalEdgeWeight = 0;
        for (Edge edge : mstEdges) {
            outputMessage += edge + "\n";        //print each edge
            mstTotalEdgeWeight += edge.getWeight();
        }
        System.out.println(outputMessage);
    }
}


class Edge implements Comparable<Edge> {
    private int u;
    private int v;
    private int weight;

    public Edge(int u, int v, int weight) {
        this.u = u;
        this.v = v;
        this.weight = weight;
    }

    public int getU() {
        return u;
    }

    public int getV() {
        return v;
    }

    public int getWeight() {
        return weight;
    }

    @Override
    public int compareTo(Edge otherEdge) {                //Compare based on edge weight (for sorting)
        return this.getWeight() - otherEdge.getWeight();
    }

    @Override
    public String toString() {
        return "Edge (" + getU() + ", " + getV() + ") weight=" + getWeight();
    }
}


class DisjointSet {
    private int[] set;        //the disjoint set as an array


    /**
     * Construct the disjoint sets object.
     *
     * @param numElements the initial number of disjoint sets.
     */
    public DisjointSet(int numElements) {        //constructor creates singleton sets
        set = new int[numElements];
        for (int i = 0; i < set.length; i++) {        //initialize to -1 so the trees have nothing in them
            set[i] = -1;
        }
    }

    /**
     * Union two disjoint sets using the height heuristic.
     * For simplicity, we assume root1 and root2 are distinct
     * and represent set names.
     *
     * @param root1 the root of set 1.
     * @param root2 the root of set 2.
     */
    public void union(int root1, int root2) {
        if (set[root2] < set[root1]) {        // root2 is deeper
            set[root1] = root2;        // Make root2 new root
        } else {
            if (set[root1] == set[root2]) {
                set[root1]--;            // Update height if same
            }
            set[root2] = root1;        // Make root1 new root
        }
    }

    /**
     * Perform a find with path compression.
     * Error checks omitted again for simplicity.
     *
     * @param x the element being searched for.
     * @return the set containing x.
     */
    public int find(int x) {
        if (set[x] < 0) {        //If tree is a root, return its index
            return x;
        }
        int next = x;
        while (set[next] > 0) {        //Loop until we find a root
            next = set[next];
        }
        return next;
    }

}