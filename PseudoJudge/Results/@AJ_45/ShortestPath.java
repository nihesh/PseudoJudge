import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;
class Reader {

    static BufferedReader reader;

    static StringTokenizer tokenizer;



    /** call this method to initialize reader for InputStream */

    static void init(InputStream input) {

        reader = new BufferedReader(new InputStreamReader(input));

        tokenizer = new StringTokenizer("");

    }

    static String next() throws IOException {

        while ( ! tokenizer.hasMoreTokens() ) {

            tokenizer = new StringTokenizer(

                   reader.readLine() );

        }

        return tokenizer.nextToken();

    }



    static int nextInt() throws IOException {

        return Integer.parseInt( next() );

    }

	

    static double nextDouble() throws IOException {

        return Double.parseDouble( next() );

    }

}	

class Node2{


	ArrayList<Integer> pth;
	int dist;
	public Node2(ArrayList<Integer> deprt,int arrivl)
	{
		
		
		this.pth=deprt;
		this.dist=arrivl;
		
	}
	public Node2() {
		// TODO Auto-generated constructor stub
	}
}
class Node{
	int frm;
	int to;
	String Nam;
	int deprt;
	int arrivl;
	public Node(int frm,int to,	String Nam,	int deprt,int arrivl)
	{
		this.frm=frm;
		this.to=to;
		this.Nam=Nam;
		this.deprt=deprt;
		this.arrivl=arrivl;
		
	}
}
public class ShortestPath
{	int V;
	public ShortestPath(int j){
		this.V=j;
	}
    // A utility function to find the vertex with minimum distance value,
    // from the set of vertices not yet included in shortest path tree
    
    int minDistance(int dist[], Boolean sptSet[])
    {
        // Initialize min value
        int min = Integer.MAX_VALUE, min_index=-1;
 
        for (int v = 0; v < V; v++)
            if (sptSet[v] == false && dist[v] <= min)
            {
                min = dist[v];
                min_index = v;
            }
 
        return min_index;
    }
 
    // A utility function to print the constructed distance array
    int printSolution(int dist[], int n)
    {
        //System.out.println("Vertex   Distance from Source");
        
            //System.out.println(n+" \t\t "+dist[n]);
            if(dist[n]==2147483647){
            	return -1;
            }
            return dist[n];
    }
 
    // Funtion that implements Dijkstra's single source shortest path
    // algorithm for a graph represented using adjacency matrix
    // representation
    void printMST(int parent[], int n, int graph[][])
    {
        System.out.println("Edge   Weight");
        for (int i = 1; i <V; i++)
            System.out.println(parent[i]+" - "+ i+"    "+graph[i][parent[i]]);
    }

    ArrayList<Integer> printpath(int parent[],int k,int n,int graph[][],ArrayList<Integer> bc){
    	if(parent[n]==k){
    	
    		bc.add(parent[n]);
    		return bc;
    	}
    	else{
    		bc.add(parent[n]);
    	
    		return printpath(parent,k, parent[n], graph,bc);
    	}
    }

    Node2 dijkstra(int graph[][], int src,int dest)
    {
        int dist[] = new int[V]; // The output array. dist[i] will hold
                                 // the shortest distance from src to i
        int parent[] = new int[V];
        // sptSet[i] will true if vertex i is included in shortest
        // path tree or shortest distance from src to i is finalized
        Boolean sptSet[] = new Boolean[V];
 
        // Initialize all distances as INFINITE and stpSet[] as false
        for (int i = 0; i < V; i++)
        {
            dist[i] = Integer.MAX_VALUE;
            sptSet[i] = false;
        }
 
        // Distance of source vertex from itself is always 0
        dist[src] = 0;
        parent[0] = -1;
        // Find shortest path for all vertices
        for (int count = 0; count < V-1; count++)
        {
            // Pick the minimum distance vertex from the set of vertices
            // not yet processed. u is always equal to src in first
            // iteration.
            int u = minDistance(dist, sptSet);
 
            // Mark the picked vertex as processed
            sptSet[u] = true;
 
            // Update dist value of the adjacent vertices of the
            // picked vertex.
            for (int v = 0; v <V; v++)
 
                // Update dist[v] only if is not in sptSet, there is an
                // edge from u to v, and total weight of path from src to
                // v through u is smaller than current value of dist[v]
                if (!sptSet[v] && graph[u][v]!=0 && dist[u] != Integer.MAX_VALUE && dist[u]+graph[u][v] < dist[v]){    
                
                	dist[v] = dist[u] + graph[u][v];
            parent[v]  = u;}
        }
 
        // print the constructed distance array
        Node2 ct=new Node2();
        ct.dist=printSolution(dist, dest);
        //printMST(parent, dest, graph);
        if(ct.dist!=-1){
        	ArrayList<Integer> cfl=new ArrayList();
        	cfl.add(dest);
        ct.pth=printpath(parent, src,dest, graph,cfl);}
        return ct;
        
    }
 
    // Driver method
        public static void main (String[] args) throws IOException
        
        {Reader.init(System.in);
        
        int no_of_cities=Reader.nextInt();
        int src =Reader.nextInt();
        int dest=Reader.nextInt();
        int str_tim=Reader.nextInt();
        int end_tim=Reader.nextInt();
        int no_of_flights=Reader.nextInt();
        int[][] the_grph=new int[no_of_flights][no_of_flights];
        Node[] allflghs=new Node[no_of_flights];
        int i=0;
        int lsize=0;
        while(i<no_of_flights){
        	int from=Reader.nextInt();
        	int tto=Reader.nextInt();
        	String Name=Reader.next();
        	int deprtur=Reader.nextInt();
        	int arrival=Reader.nextInt();
        	
        	if(deprtur>=str_tim && arrival<=end_tim){
        	Node a=new Node(from, tto, Name, deprtur, arrival);
        	allflghs[lsize]=a;
        	lsize=lsize+1;
        	}
        	i=i+1;
         }
        for(int j=0;j<lsize;j++){
        	for(int l=0;l<lsize;l++){
        		if(allflghs[j].to==allflghs[l].frm){
        			//System.out.println(allflghs[j].arrivl+" "+allflghs[j].deprt+" " +allflghs[l].deprt);
        			if(((allflghs[l].deprt/100)*60+allflghs[l].deprt%100)-((allflghs[j].arrivl/100)*60+allflghs[j].arrivl%100)>0)
        			the_grph[j][l]=((allflghs[l].deprt/100)*60+allflghs[l].deprt%100)-((allflghs[j].deprt/100)*60+allflghs[j].deprt%100);
        		
        		}
        	}
        } /* Let us create the example graph discussed above */
		for(int t=0;t<lsize;t++){
		for(int l=0;l<lsize;l++){
			//System.out.print(the_grph[t][l]+"      ");
		}
		//System.out.println(" ");
	}
		ArrayList<Integer> mn=new ArrayList();
		ArrayList<Integer> nm=new ArrayList();
		
		for(int k=0;k<lsize;k++){
			if(allflghs[k].frm==src){
				//System.out.println(allflghs[k].Nam);
				mn.add(k);
				
			}
			if(allflghs[k].to==dest){
		
				
				nm.add(k);
				//System.out.println(allflghs[k].Nam);
				}
			
			
		}
		
		Node2[] finall=new Node2[mn.size()];
		int size=0;
		for(int l=0;l<mn.size();l++){
			for(int c=0;c<nm.size();c++){
				Node2 loc=new Node2();
				ShortestPath t = new ShortestPath(lsize);
				//System.out.println(mn.get(l)+" "+nm.get(c));
				
				if(mn.get(l)==nm.get(c)){
					int vgin=allflghs[mn.get(l)].arrivl;
					int agin=allflghs[mn.get(l)].deprt;
					//System.out.println((vgin/100)*60+vgin%100 +" "+(agin/100)*60+agin%100);
					loc.dist=((vgin/100)*60+vgin%100)-((agin/100)*60+agin%100);
					//System.out.println(loc.dist);
					ArrayList<Integer> kbc=new  ArrayList();
					kbc.add(mn.get(l));
					loc.pth=kbc;
			        finall[size]=loc;
			        size=size+1;
					
				}
				else{
		        loc=t.dijkstra(the_grph,mn.get(l),nm.get(c));
		       // System.out.println(loc.dist+" jknh");
		        if(loc.dist!=-1){
		        	//System.out.println(loc.pth.size()+" xs");
		        	for(int v=0;v<loc.pth.size();v++){
		        	//System.out.println(loc.pth.get(v)+" rvd");
		        		}
		        	
		        //loc.dist=loc.dist+allflghs[(int) loc.pth.get(0)].arrivl-allflghs[(int) loc.pth.get(0)].deprt+allflghs[(int) loc.pth.get(loc.pth.size()-1)].deprt-((str_tim/100)*60+str_tim%100);
		        	int u=allflghs[(int) loc.pth.get(0)].arrivl;
		        	int uu=allflghs[(int) loc.pth.get(0)].deprt;
		        	loc.dist=loc.dist+((u/100)*60+u%100)-((uu/100)*60+uu%100);
		        //System.out.println(allflghs[(int) loc.pth.get(0)].arrivl+" 1 "+allflghs[(int) loc.pth.get(0)].deprt+" 2 "+allflghs[(int) loc.pth.get(loc.pth.size()-1)].deprt+" 3 "+((str_tim/100)*60+str_tim%100)+" 4 " );
		        //System.out.println(loc.dist);
		        finall[size]=loc;
		        size=size+1;
		        }
			
			}
			}
        
    }
	bubbleSort(finall,size);
	if(size==0){
		System.out.println(-1);
	}
	else{ 
		System.out.println(finall[0].pth.size());
		for(int kp=finall[0].pth.size()-1;kp>=0;kp--){
			System.out.print(allflghs[(int) finall[0].pth.get(kp)].Nam+" ");
		}
		
	}
}
        public static void bubbleSort(Node2[] numArray, int size) {

            int n =size;
            Node2 temp = null;

            for (int i = 0; i < n; i++) {
                for (int j = 1; j < (n - i); j++) {

                    if (numArray[j - 1].dist > numArray[j].dist) {
                        temp = numArray[j - 1];
                        numArray[j - 1] = numArray[j];
                        numArray[j] = temp;
                    }

                }
            }
        }}