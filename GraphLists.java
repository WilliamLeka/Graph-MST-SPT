/*
 * William Leka C21423244 Algorithms 
 */

// Simple weighted graph representation 
// Uses an Adjacency Linked Lists, suitable for sparse graphs

import java.io.*;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Scanner;

class Heap
{
    int[] a;	   // heap array
    private int[] hPos;	   // hPos[h[k]] == k
    private int[] dist;    // dist[v] = priority of v

    private int N;         // heap size
   
    // The heap constructor gets passed from the Graph:
    //    1. maximum heap size
    //    2. reference to the dist[] array
    //    3. reference to the hPos[] array

    // Default Constructor
    public Heap(int maxSize, int[] _dist, int[] _hPos) 
    {
        N = 0;
        a = new int[maxSize + 1];
        dist = _dist;
        hPos = _hPos;
    } // ends Heap Constructor

    public boolean isEmpty() 
    {
        return N == 0;
    } // end isEmpty method

    // Moves element up in heap until in correct pos
    public void siftUp( int k) 
    {
        int v = a[k];
        int priority = dist[v];
        hPos[v] = k;
        while (priority < dist[a[k / 2]]) {
            a[k] = a[k / 2];
            hPos[a[k]] = k;
            k = k / 2;
        }
        a[k] = v;
        hPos[v] = k;
        
    }



    public void siftDown( int k) 
    {
        int v, j;
        v = a[k];
        int priority = dist[v];
        while (k <= N / 2) {
            j = 2 * k;
            if (j < N && dist[a[j]] > dist[a[j + 1]]) {
                j++;
            }
            if (priority <= dist[a[j]]) {
                break;
            }
            a[k] = a[j];
            hPos[a[k]] = k;
            k = j;
        }
        a[k] = v;
        hPos[v] = k;
        
    }



    // Checks if a given integer is in Heap
    public boolean inHeap(Heap pq, int num) 
    {
        // Nested For loop goes through all integers in loop
        for(int i = 1; i<= N/2; i = i * 2) {
            for(int j = 2*i; j < 4*i && j <= N; ++j)
                // IF num is in heap
                if(num == a[j])
                {
                    return true;
                }
        }
        return false;
    } // end inHeap method



    public void insert( int x) 
    {
        a[++N] = x;
        siftUp(N);
    } // End insert method



    public int remove() 
    {  
        // IF heap is empty does not remove from heap
        if(this.isEmpty() == true) 
        {
            return 0;
        }
        int v = a[1];
        hPos[v] = 0; // v is no longer in heap
        a[N+1] = 0;  // put null node into empty spot
        
        a[1] = a[N--];
        siftDown(1);
        
        return v;
    } // end remove method



    // display heap values and their priorities or distances
    void display() {
        System.out.println("\n\nThe tree structure of the heaps is:");
        System.out.println( a[1] + "(" + dist[a[1]] + ")" );
        for(int i = 1; i<= N/2; i = i * 2) {
            for(int j = 2*i; j < 4*i && j <= N; ++j)
                System.out.print( a[j] + "(" + dist[a[j]] + ")  ");
            System.out.println();
        }
    } // End display Method

} // End Heap class



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

    
    // used for traversing graph
    private int[] visited;
    private int id;
    private int[] parent;
    
    
    // default constructor
    public Graph(String graphFile)  throws IOException
    {
        int u, v;
        int e, wgt;
        Node t, n;

        FileReader fr = new FileReader(graphFile);
		BufferedReader reader = new BufferedReader(fr);
	           
        String splits = " +";  // multiple whitespace as delimiter
		String line = reader.readLine();        
        String[] parts = line.split(splits);
        System.out.println("Parts[] = " + parts[0] + " " + parts[1]);
        
        V = Integer.parseInt(parts[0]);
        E = Integer.parseInt(parts[1]);

        parent = new int[V+1];
        
        // create sentinel node
        z = new Node(); 
        z.next = z;
        
        // create adjacency lists, initialised to sentinel node z       
        adj = new Node[V+1];        
        for(v = 1; v <= V; ++v)
            adj[v] = z;               
        
        // read the edges
        System.out.println("Reading edges from text file");
        for(e = 1; e <= E; ++e)
        {
            line = reader.readLine();
            parts = line.split(splits);
            u = Integer.parseInt(parts[0]);
            v = Integer.parseInt(parts[1]); 
            wgt = Integer.parseInt(parts[2]);
            
            System.out.println("Edge " + toChar(u) + "--(" + wgt + ")--" + toChar(v));   
            t = new Node();
            n = new Node();
            // First Node places edge into adj[V]
            t.wgt = wgt;
            t.vert = u;
            t.next = adj[v];
            adj[v] = t;
            // // Second Node places edge into adj[U], reversing the first node
            // n.wgt = wgt;
            // n.vert = v;
            // n.next = adj[u];
            // adj[u] = n;  


        } // End for    

    } // End Graph constructor




    // convert vertex into char
    private char toChar(int u)
    {  
        return (char)(u + 64);
    } // end toChar method


    
    // method to display the graph representation
    public void display() {
        int v;
        Node n;
        
        for(v=1; v<=V; ++v){
            System.out.print("\nadj[" + toChar(v) + "] ->" );
            for(n = adj[v]; n != z; n = n.next) 
                System.out.print(" |" + toChar(n.vert) + " | " + n.wgt + "| ->");    
        } // end for
        System.out.println("");
    } // end display method



    // method to initialize Depth First Search
    public void DF(int s){

        id = s;
        visited = new int[V+1];
        for(int v = 1; v <= V; ++v)
            visited[v] = 0;

        System.out.println("--------------------------\nDepth First Search is now starting.....\n"+ "Starting on Vertex " + toChar(s) + "\n--------------------------\n");
        dfvisit(id,s);
        System.out.println("--------------------------\nDepth First Search is now Finished\n--------------------------\n");

    } // end DF method



    // Depth First Search algorithm
    public void dfvisit(int prev , int v){

        Node t =  new Node();
        prev = id;
        visited[v] = ++id;
        int back = 0;

        
        for(int u = 1; u <= V; u++){
            // IF node u has not been visited and is connected to v
            if(visited[u] == 0 && hasEdge(v, u) == true){
                back = v;
                System.out.println("\n visited vertex " + toChar(u) + " - " + toChar(back) + "--" + toChar(u));
                dfvisit(id,u);
            } // end if
        } // end for

    } // End dfvisit method



    // Checks if v and u are connected by an edge
    private boolean hasEdge(int v,int u) {
        Node n = new Node();

        // loops through adj[v]
        for(n = adj[v]; n != z; n = n.next) {
            // IF int u == to node N int
            if(u == n.vert) 
            {
                return true;
            }
        } // end for
        return false;

    } // end hasEdge method



    // Breadth First Search algorithm
    public void BF(int s) {
        Queue<Integer> queue = new LinkedList<>();

        // initializes visited array
        for(int v = 1; v <= V; ++v)
            visited[v] = 0;

            
        queue.add(s); // Adds starting vertex to queue
        System.out.println("--------------------------\nBreadth First Search is now starting.....\n"+ "Starting on Vertex " + toChar(s) + "\n--------------------------\n");
        // WHILE queue is NOT empty
        while(!queue.isEmpty()) 
        {
            int v = queue.remove(); // V is current node
            visited[v] = 1; // current node has been visited
                
            for(int u=0; u<V ; u++)
            {
                // IF node u has not been visited and has a connected edge
                if(visited[u] == 0 && hasEdge(v, u) == true) 
                {
                    visited[u] = 1; // Node u will be visited
                    queue.add(u); // adds index u to queue
                    s = u; // Starting node now switches to u
                    System.out.println("\nvisited vertex " + toChar(s) + " - edge " + toChar(v) + "--" + toChar(s) );
                } // end if
                        
            } // end for

        } // end while 
        System.out.println("--------------------------\nBreadth First Search is now Finished\n--------------------------\n");

    } // End BF method

    public void printArray(int[] arr) 
    {
        for(int i=1; i<arr.length; i++) 
        {
            System.out.print(arr[i]+", ");
        }
        System.out.println("\n--------");
    }
    
	// Prim's algorithm
    public int[] MST_Prim(int s)
    {
        int v;
        int wgt_sum = 0;
        int[] dist, parent, hPos;
        Node t;

        // Initializes array size
        dist = new int[V + 1];
        parent = new int[V + 1];
        hPos = new int[V + 1];


        // initializing parent, dist and hPos arrays
        for (v = 1; v <= V; v++)
        {
            dist[v] = Integer.MAX_VALUE;
            parent[v] = 0;
            hPos[v] = 0;
        }

        // Creates new heap and inserts method argument s into heap first
        Heap heap = new Heap(V + 1, dist, hPos);
        heap.insert(s);

        dist[s] = 0; // Makes node connected to self = 0

        // WHILE heap is NOT empty
        while (!heap.isEmpty())
        {

            // Removes value from heap
            v = heap.remove();

            wgt_sum += dist[v]; 
            dist[v] = -dist[v];

            // Goes through adj[v]
            for (t = adj[v]; t != z; t = t.next)
            {
        
                if (t.wgt < dist[t.vert])
                {
                    dist[t.vert] = t.wgt;
                    parent[t.vert] = v;

                    //If the vertex is empty, insert next vertex
                    if (hPos[t.vert] == 0)
                    {
                        heap.insert(t.vert);
                    } // end if
                    else
                    {
                        heap.siftUp(hPos[t.vert]);
                    } // end else

                } // end if

            } // end for

        } // end While

        System.out.println("\n\nWeight of Prim's MST  = " + wgt_sum);
        return parent;
    } // end MST_Prim method
    



    // Displays MST using int array
    public void showMST(int[] mst)
    {
        int v;
        int weight = 0;

        System.out.print("\n\nMinimum spanning tree (Prim) :\n");
        for(v = 1; v <= V; ++v)
        {
            for(Node x = adj[v];x!=z;x=x.next) 
            {
                if(x.vert == mst[v])
                {
                    weight = x.wgt;
                }
            }
            if(v!= 12) 
            {
                System.out.println(toChar(v) +"---" + weight + "---" + toChar(mst[v]));
            }
                
        }
        System.out.println();
    } // end ShowMST method





    public void SPT_Dijkstra(int s)
    {
        int v, d,u;
        int wgt_sum = 0;
        int[] dist, parent, hPos;
        Node t;

        // Initializes array size
        dist = new int[V + 1];
        parent = new int[V + 1];
        hPos = new int[V + 1];

        // initializing parent, dist and hPos arrays
        for (v = 1; v <= V; v++)
        {
            dist[v] = Integer.MAX_VALUE;
            parent[v] = 0;
            hPos[v] = 0;
        } // end for

        Heap pq = new Heap(V + 1, dist, hPos); // Creates new Heap
        dist[s] = 0; // Makes node connected to self = 0
        v = s; // Makes v starting vertex

        // While V does not reach end of heap
        while(v != 0)
        {
            // Loops through adj[v]
            for(t = adj[v]; t != z; t = t.next) 
            {
                u = t.vert;
                d = t.wgt;
                if(dist[u] > dist[v] + d) 
                {
                    dist[u] = dist[v] + d;

                    // IF value u is not in Heap pq
                    if(pq.inHeap(pq, u) == false) 
                    {
                        
                        pq.insert(u);
                    } // end if
                    else 
                    {
                        pq.siftUp(hPos[u]);
                    } // end else
                    parent[u] = v;
                } // end if

            }// end for
            v = pq.remove();
        } // end while
        parent[12] = 12;
        // Print table of shortest paths
        System.out.println("Shortest path table:");
        System.out.println("Vertex\tDistance\tParent");
        for (int i = 1; i <= V; i++) {
            System.out.println(toChar(i) + "\t" + dist[i] + "\t\t" + toChar(parent[i]));
        }
        
    } // End SPT_Dijkstra method

} // end Graph class





public class GraphLists {
    public static void main(String[] args) throws IOException
    {
        int s = 12;
        int[] mst;
        String fname;
        
        try (Scanner myObj = new Scanner(System.in)) {
            System.out.print("\nInput name of file with graph definition: ");
            fname = myObj.nextLine();  // Read user input
        }            

        Graph g = new Graph(fname);
       
        g.display();
        g.DF(s);
        g.BF(s);
        mst = g.MST_Prim(s);   
        g.showMST(mst);
        g.SPT_Dijkstra(s);      
    }
}
