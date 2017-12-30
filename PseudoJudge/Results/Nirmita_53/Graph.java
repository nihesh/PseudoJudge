package idccc;
import java.util.Arrays;
import java.util.Collections;
import java.lang.*;

import java.io.*;
import java.util.*;


class node{
	int c1;
	int c2;
	String f;
	int d;
	int a;
	int dur;
	node next;
	
	public node(int c1,int c2,String f,int d, int a,node p){
		this.c1=c1;
		this.c2=c2;
		this.f=f;
		this.d=d;
		this.a=a;
		next=p;
		dur=duration(a,d);
	}
	public static int duration(int a,int d){
		double ca=a;
		double cd=d;
		double sum12=0;
		int dur;
		for(int i=0;i<2;i++){
			double r=ca%10;
			sum12=sum12+r*Math.pow(10,i);
			ca=(int)ca/10;
		}
		double sum34=0;
		for(int i=0;i<2;i++){
			double r=cd%10;
			sum34=sum34+r*Math.pow(10,i);
			cd=(int)cd/10;
		}
		if(ca>cd)
			dur=(int)(60-sum34+sum12);
		else
			dur=(int)(sum12-sum34);
		return dur;
	}
	public static node copy(node c){
		
		node copy=new node(c.c1,c.c2,c.f,c.d,c.a,c.next);
		
		return copy;
		
	}
}
class justk{
	int sum;
	
	String full;
	
	int total;
	
	public justk(int V){
		 full="";
		 sum=0;
		 
	}
}


 
// This class represents a directed graph using adjacency list
// representation
public class Graph
{
    static int V;   // No. of vertices
    static int des;
    // Array  of lists for Adjacency List Representation
    static LinkedList<node> adj[];
    static justk[] bye1;
    // Constructor
    Graph(int v)
    {
        V = v;
        adj = new LinkedList[v+1];
        for (int i=0; i<=v; ++i)
            adj[i] = new LinkedList();
    }
    static int t1;
    static int t2;
 
    node front=new node(0,0,"",0,0,null);
    //Function to add an edge into the graph
    void addEdge(int v, node w)
    {
    	
    	front=adj[v].peek();
    	if(w.a<t2 && w.d>t1 && w.a<2300 && w.d>0500)
    		{
    			adj[v].add(w);
    			if(front!=null)
    				{
    					front.next=w;
    	    			front=front.next;

    				}
    			
    			
    			//front.next=new node(w.c1,w.c2,w.f,w.a,w.d,w.next);
    			//System.out.println(v+ " connected to : " + w.c2);
    		}
    	// Add w to v's list.
        
    }
    public static int duration(int a,int d){
    	if(d>a)
    		return -1;
		double ca=a;
		double cd=d;
	//	System.out.println("a "+a+ " d "+ d);
		double sum12=0;
		int dur;
		for(int i=0;i<2;i++){
			double r=ca%10;
			sum12=sum12+(int)r*Math.pow(10,i);
			//System.out.println(r+" Math pow "+ Math.pow(10, i) );
			ca=(int)ca/10;
		}
		//
		double sum34=0;
		for(int i=0;i<2;i++){
			double r=cd%10;
			sum34=sum34+(int)r*Math.pow(10,i);
			cd=(int)cd/10;
		}
		if((int)ca>(int)cd)
			dur=(int)((ca-cd)*60-sum34+sum12);
		else
			dur=(int)(sum12-sum34);
		return dur;
	}
    static int[][] info=new int[3][V+1];
//    static int sum[]=new int[V];
    static String[] details=new String[V+1];
//    static String[] full=new String[V]; 
    // A function used by DFS
   
       
    
    static int k=0; 
    static justk[] bye;
    public static void calc(int n){
    	
    	for(int i=0;i<=n;i++){
    		
    		bye[k].sum=bye[k].sum+info[2][i]+info[1][i];
    		bye[k].full=bye[k].full+" "+details[i];
    		
    	}
    	
    	bye[k].total=n;
    	k++;
    }
    
    // The function to do DFS traversal. It uses recursive DFSUtil()
    
    public static void printAllPaths(int s, int d)//s=src d=des
    {

        // Mark all the vertices as not visited
        boolean[] visited = new boolean[V+1];
     
        // Create an array to store paths
        node[] path = new node[V+1];
        int path_index = 0; // Initialize path[] as empty
     
        // Initialize all vertices as not visited
        for (int i = 0; i < V; i++)
            visited[i] = false;
     
        // Call the recursive helper function to print all paths
        printAllPathsUtil(s, d, visited, path, path_index,adj[s].peek());
    }
     
    // A recursive function to print all paths from 'u' to 'd'.
    // visited[] keeps track of vertices in current path.
    // path[] stores actual vertices and path_index is current
    // index in path[]
    static int src;
    static int lol=0;
    static node temp;
    public static void printAllPathsUtil(int u, int d, boolean visited[],
                                  node path[], int path_index,node i)
    {
        // Mark the current node and store it in path[]
        visited[u] = true;
              	
        path[path_index] = i;
       // System.out.println("u "+u);
        path_index++;
        
     
        // If current vertex is same as destination, then print
        // current path[]
        if (u == d)
        {	int time=0;
        	bye1[lol]=new justk(V);
            for (int i1 = 1; i1<path_index; i1++){
            	time=0;
            	if(i1<path_index-1)
            		time=duration(path[i1+1].d,path[i1].a);
            //	System.out.println("wait "+time);
            	if(time>=0)
            	{
            		bye1[lol].sum=path[i1].dur+time+bye1[lol].sum;
            	
            		bye1[lol].full=bye1[lol].full +path[i1].f+" " ;
            	
            		//System.out.print(path[i1].f + " ");
            	}
            	else
            		break;
              }
            bye1[lol].total=path_index;
            if(time>=0)
            { 	lol++;
            	//System.out.println();
            }
          
        }
        else 
        {
           
            for (node i2 = adj[u].peek(); i2 != null  ; i2=i2.next)
            { 
            	if (!visited[i2.c2])
                {
                	
                	printAllPathsUtil(i2.c2, d, visited, path, path_index,i2);
                	
                }
            }
        }
     
        // Remove current vertex from path[] and mark it as unvisited
        path_index--;
        visited[u] = false;
    }
   // public static int shortest()

    public static void main(String args[])
    {
    	Scanner in=new Scanner(System.in);
    	int c1;
    	int n=in.nextInt();
    	src= in.nextInt();
    	des= in.nextInt();
    	t1= in.nextInt();
    	t2= in.nextInt();
    	int n2=in.nextInt();
    	
    	Graph g = new Graph(n);
    	for(int i=0;i<n2;i++){
    		
    		int cc1= in.nextInt();
    		int cc2= in.nextInt();
    		String f= in.next();
    		int d1= in.nextInt();
    		int a1= in.nextInt();
    		//if(d1>t1 && a1<t2){
    	
    		node fll=new node(cc1,cc2,f,d1,a1,null);
    		
    			
    	//	}
    		g.addEdge(cc1, fll);
    	}
    	bye1=new justk[n2*n];
       
    	temp=adj[src].peek();
      /*  g.addEdge(0, 1);
        g.addEdge(0, 2);
        g.addEdge(1, 2);
        g.addEdge(2, 0);
        g.addEdge(2, 3);
        g.addEdge(3, 3);*/
    	//List b = Arrays.asList(ArrayUtils.toObject(bye.sum));

       // System.out.println(Arrays.stream(bye[].sum).min().getAsInt());
      //  System.out.println(x);
    	g.printAllPaths(src,des);
    //	g.print();
    	int min=2400;
    	int s=0;
    	if(lol>0)
    		lol--;
    	if(bye1[lol]!=null)
    	if(bye1[lol].full!=""){
    		for(int j=0;j<n;j++)
    	{
    		if(bye1[j]!=null)
    		if(bye1[j].sum < min)
    			{
    				min=bye1[j].sum;
    				s=j;
    			}
    	}
    	System.out.println(bye1[s].total-1);
    	System.out.println(bye1[s].full);
    	}
    	else
    		System.out.println(-1);
    	else
    		System.out.println(-1);
    	
     //   System.out.println("Following is Depth First Traversal "+
       //                    "(starting from vertex 2)");
 
      
    }
}