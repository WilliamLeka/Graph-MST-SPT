import java.io.*;
import java.util.Scanner;

class Edge {
    public int u, v, wgt;
    public Edge() {
        u = 0;
        v = 0;
        wgt = 0;
    } 

    public Edge(int u, int v, int wgt) {
        this.u = u;
        this.v = v;
        this.wgt = wgt;
    }

    private char toChar(int u) {
        return (char) (u + 64);
    } 

    public void show() {
        System.out.println(toChar(u) + "--" + wgt + "--" + toChar(v));
    } 

}

class Heap {
    private int[] h;
    int N, Nmax;
    Edge[] edge;

    public Heap(int _N, Edge[] _edge) {
        int i;
        Nmax = N = _N;
        h = new int[N + 1];
        edge = _edge;

        for (i = 0; i <= N; ++i)
            h[i] = i;


        for (i = N / 2; i > 0; --i)
            siftDown(i);
    }

    private void siftDown(int k) {
        int e, j;

        e = h[k];
        while (k <= N / 2) {
            j = 2 * k;
            if (j < N && edge[h[j]].wgt > edge[h[j + 1]].wgt)
                j++;
            if (edge[e].wgt <= edge[h[j]].wgt)
                break;
            h[k] = h[j];
            k = j;
        } 
        h[k] = e;
    }

    public void displayHeap() 
    {
        for (int i = 1; i <= N; i++) {
            edge[h[i]].show();
        }
    } 

    public int remove() {
        h[0] = h[1];
        h[1] = h[N--];
        siftDown(1);
        return h[0];
    } 

}

class UnionFindSets {
    private int[] treeParent;
    private int N;

    public UnionFindSets(int V) {
        N = V;
        treeParent = new int[V + 1];
        for (int i = 1; i <= V; ++i)
            treeParent[i] = i;
    }

    public int findSet(int vertex) {
        if (treeParent[vertex] == vertex)
            return vertex;
        else
            return findSet(treeParent[vertex]);

    }

    public void union(int set1, int set2) {
        treeParent[findSet(set1)] = findSet(set2);
    }

    public void showTrees() {
        int i;
        for (i = 1; i <= N; ++i)
            System.out.print(toChar(i) + "->" + toChar(treeParent[i]) + "  ");
        System.out.print("\n");
    }

    public void showSets() {
        int u, root;
        int[] shown = new int[N + 1];
        for (u = 1; u <= N; ++u) {
            root = findSet(u);
            if (shown[root] != 1) {
                showSet(root);
                shown[root] = 1;
            } 
        } 
        System.out.print("\n");
    }

    private void showSet(int root) {
        int v;
        System.out.print("Set{");
        for (v = 1; v <= N; ++v)
            if (findSet(v) == root)
                System.out.print(toChar(v) + " ");
        System.out.print("}  ");

    } 

    private char toChar(int u) {
        return (char) (u + 64);
    } 
}

class Graph {
    class Node {
        public int vert; // data
        public int wgt; // weight
        public Node next; // next node
    }

    // V = number of vertices
    // E = number of edges
    // adj[] is the adjacency lists array
    private int V, E;
    private Node[] adj;
    private Node z;
    private Edge[] mst;
    private Edge[] edge;

    // used for traversing graph
    private int[] visited;
    private int id;
    private int[] parent;

    public Graph(String graphFile) throws IOException {
        int u, v;
        int e, wgt;

        FileReader fr = new FileReader(graphFile);
        BufferedReader reader = new BufferedReader(fr);

        String splits = " +"; // multiple whitespace as delimiter
        String line = reader.readLine();
        String[] parts = line.split(splits);
        System.out.println("Parts[] = " + parts[0] + " " + parts[1]);

        V = Integer.parseInt(parts[0]);
        E = Integer.parseInt(parts[1]);

        // create edge array
        edge = new Edge[E + 1];
        parent = new int[V + 1];

        // create sentinel node
        z = new Node();
        z.next = z;

        // create adjacency lists, initialised to sentinel node z
        adj = new Node[V + 1];
        for (v = 1; v <= V; ++v)
            adj[v] = z;

        // read the edges
        System.out.println("Reading edges from text file");
        for (e = 1; e <= E; ++e) {
            line = reader.readLine();
            parts = line.split(splits);
            u = Integer.parseInt(parts[0]);
            v = Integer.parseInt(parts[1]);
            wgt = Integer.parseInt(parts[2]);

            System.out.println("Edge " + toChar(u) + "--(" + wgt + ")--" + toChar(v));

            edge[e] = new Edge(u, v, wgt);

        } 

    }

    private char toChar(int u) {
        return (char) (u + 64);
    }

    public Edge[] MST_Kruskal() {
        int ei, i = 0;
        Edge e;
        int uSet, vSet;
        UnionFindSets partition = new UnionFindSets(V);
        mst = new Edge[V - 1];
        Heap h = new Heap(E, edge);
        while (i < V - 1) {
            ei = h.remove();
            e = edge[ei];
            uSet = partition.findSet(e.u);
            vSet = partition.findSet(e.v);
            if (uSet != vSet) {
                mst[i++] = e;
                partition.union(uSet, vSet);
            } 
        }
        System.out.println("\n");
        System.out.println();
        return mst;
    }


    public void showMST() {
        System.out.println("\nMST:");
        for (int e = 0; e < V - 1; ++e)
            mst[e].show();

        System.out.println();
    } 

}

public class Krus {
    public static void main(String[] args) throws IOException {
        String fname = "wGraph3.txt";
    

        Graph g = new Graph(fname);
    
        g.MST_Kruskal();
        g.showMST();
    
    }
}
