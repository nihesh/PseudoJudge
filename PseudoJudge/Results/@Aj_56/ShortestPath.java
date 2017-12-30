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
	int strt_t;
	public Node2(ArrayList<Integer> deprt,int arrivl,int sd)
	{
		this.pth=deprt;
		this.dist=arrivl;
		this.strt_t=sd;
		
	}
	public Node2() {

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
{	int crrct_edg_no;
	public ShortestPath(int j){
		this.crrct_edg_no=j;
	}
    
    int find_smallest_timewght(int length[], boolean k[])
    {
        int local = 2147483647, 
        frst_indx=-1; 
        for (int v=0;v<crrct_edg_no;v++)
            if (k[v]==false && length[v] <= local)
            {
            	local=length[v];
                frst_indx=v;
            } 
        return frst_indx;
    }
    int give_wght_dest(int dist[],int n)
    {
        if(dist[n]==2147483647){
            	return -1;
            }
            return dist[n];
    }
    ArrayList<Integer> store_parent_path(int parent[],int k,int n,int graph[][],ArrayList<Integer> bc){
    	
    	if(parent[n]==-102){
        	
    		//bc.add(parent[n]);
    		return bc;
    	}
    	if(parent[n]==k){
    	
    		bc.add(parent[n]);
    		return bc;
    	}
    	else{
    		bc.add(parent[n]);
    		//System.out.println(parent[n]);
    		return store_parent_path(parent,k, parent[n], graph,bc);
    	}
    }

    Node2 DA(int graph[][],int src,int dest)
    {
    	int source[]=new int[ crrct_edg_no];
        int distance[]=new int[ crrct_edg_no];
        boolean is_visited[]=new boolean[crrct_edg_no];
        for (int i=0;i<crrct_edg_no;i++)
        {
            distance[i] = 2147483647;
            is_visited[i] = false;
        }
        distance[src] = 0;
        source[src] = -102;
        for (int indx=0;indx<crrct_edg_no-1; indx++)
        {
            int least = find_smallest_timewght(distance, is_visited);
            is_visited[least] = true;
            for (int v=0;v<crrct_edg_no;v++)
                if (is_visited[v]==false && graph[least][v]!=0 && distance[least] != 2147483647 && distance[least]+graph[least][v] < distance[v]){    
                	distance[v] = distance[least] + graph[least][v];
                	source[v]  = least;}
        }
        Node2 ct=new Node2();
        ct.dist=give_wght_dest(distance, dest);        
        if(ct.dist!=-1){
        	ArrayList<Integer> cfl=new ArrayList();
        	cfl.add(dest);
        ct.pth=store_parent_path(source, src,dest, graph,cfl);}
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
        //System.out.println(lsize+" vgor");
        for(int j=0;j<lsize;j++){
        	for(int l=0;l<lsize;l++){
        		if(allflghs[j].to==allflghs[l].frm){ 
        			//System.out.println(allflghs[j].arrivl+" "+allflghs[j].deprt+" " +allflghs[l].deprt);
        			if(((allflghs[l].deprt/100)*60+allflghs[l].deprt%100)-((allflghs[j].arrivl/100)*60+allflghs[j].arrivl%100)>=0)
        			the_grph[j][l]=((allflghs[l].deprt/100)*60+allflghs[l].deprt%100)-((allflghs[j].deprt/100)*60+allflghs[j].deprt%100);
        		
        		}
        	}
        } /* Let us create the example graph discussed above */
//		for(int t=0;t<lsize;t++){
//		for(int l=0;l<lsize;l++){
//			//System.out.print(the_grph[t][l]+"      ");
//		}
//		//System.out.println(" ");
//	}
	
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
		
		Node2[] finall=new Node2[no_of_flights];
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
					ArrayList<Integer> kbc=new  ArrayList<Integer>();
					kbc.add(mn.get(l));
					loc.pth=kbc;
					loc.strt_t=allflghs[mn.get(l)].deprt;
			        finall[size]=loc;
			        size=size+1;
					
				}
				else{
					//System.out.println(mn.get(l)+" "+nm.get(c));
		        loc=t.DA(the_grph,mn.get(l),nm.get(c));
		        //System.out.println(loc.dist+" jknh");
		        if(loc.dist!=-1){
		        	
		        	//System.out.println(loc.pth.size()+" xs");
//		        	for(int v=0;v<loc.pth.size();v++){
//		        	//System.out.println(loc.pth.get(v)+" rvd");
//		        		}
		        	
		        //loc.dist=loc.dist+allflghs[(int) loc.pth.get(0)].arrivl-allflghs[(int) loc.pth.get(0)].deprt+allflghs[(int) loc.pth.get(loc.pth.size()-1)].deprt-((str_tim/100)*60+str_tim%100);
		        	int u=allflghs[(int) loc.pth.get(0)].arrivl;
		        	int uu=allflghs[(int) loc.pth.get(0)].deprt;
		        	loc.dist=loc.dist+((u/100)*60+u%100)-((uu/100)*60+uu%100);
		        //System.out.println(allflghs[(int) loc.pth.get(0)].arrivl+" 1 "+allflghs[(int) loc.pth.get(0)].deprt+" 2 "+allflghs[(int) loc.pth.get(loc.pth.size()-1)].deprt+" 3 "+((str_tim/100)*60+str_tim%100)+" 4 " );
		        //System.out.println(loc.dist);
		        	loc.strt_t=allflghs[mn.get(l)].deprt;
		        finall[size]=loc;
		        size=size+1;
		        }
			
			}
			}
        
    }
	//bubbleSort(finall,size);
	if(size==0){
			System.out.println(-1);
			return;
	}
	Node2 temp=finall[0];
	for (int ii=0;ii<size;ii++){
		if(finall[ii].dist<temp.dist){
			temp=finall[ii];
		}
	}
	Node2[] bckcd=new Node2[size];
	int bsize=0;
	for(int it=0;it<size;it++){
		if(temp.dist==finall[it].dist){
			bckcd[bsize]=finall[it];
			bsize=bsize+1;
		}
	}
	Node2 temp2=bckcd[0];
	for (int ii=0;ii<bsize;ii++){
		if(bckcd[ii].strt_t<temp2.strt_t){
			temp2=bckcd[ii];
		}
	}
	if(size==0){
		System.out.println(-1);
	}
	else{  
		System.out.println(temp2.pth.size());
		for(int kp=temp2.pth.size()-1;kp>=0;kp--){
			System.out.print(allflghs[(int) temp2.pth.get(kp)].Nam+" ");
		}
		
	}
}
//        public static void bubbleSort(Node2[] numArray, int size) {
//
//            int n =size;
//            Node2 temp = null;
//
//            for (int i = 0; i < n; i++) {
//                for (int j = 1; j < (n - i); j++) {
//
//                    if (numArray[j - 1].dist > numArray[j].dist) {
//                        temp = numArray[j - 1];
//                        numArray[j - 1] = numArray[j];
//                        numArray[j] = temp;
//                    }
//
//                }
//            }
//        }
        }