

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

class minheapnode{
	Node v;
	int dist;
	
	public minheapnode(Node v1,int dist1){
		v=v1;
		dist=dist1;
	}
}

class minheap{
	int size;
	int capacity;
	int[] pos;
	minheapnode[] array;
	
	public minheap(int c){
		pos=new int[c];
		size=0;
		capacity=c;
		array=new minheapnode[c];
	}
	
	
	public void minheapify(int idx){
		int smallest,left,right;
		smallest=idx;
		left=2*idx+1;
		right=2*idx+2;
		if(left < size && array[left].dist < array[smallest].dist){
			smallest=left;
		}
		if(right < size && array[right].dist < array[smallest].dist){
			smallest=right;
		}
		if(smallest != idx)	{
			minheapnode smallestnode=array[smallest];
			minheapnode idxnode=array[idx];
			pos[smallestnode.v.f1.id]=idx;
			pos[idxnode.v.f1.id]=smallest;
			//swap(array[smallest],array[idx]);
			minheapnode temp=array[smallest];
			array[smallest]=array[idx];
			array[idx]=temp;
			minheapify(smallest);
		}
	}
	
	public boolean isempty(){
		return size == 0;
	}
	
	public minheapnode extractmin(){
		if(isempty()==true){
			return null;
		}
		minheapnode root=array[0];
		minheapnode lastnode=array[size-1];
		array[0]=lastnode;
		pos[root.v.f1.id]=size-1;
		pos[lastnode.v.f1.id]=0;
		--size;
		minheapify(0);
		return root;
	}
	
	public void decreaseKey(int v,int dist){
		int i=pos[v];
		array[i].dist=dist;
		while(i != 0 && array[i].dist < array[(i-1)/2].dist){
			pos[array[i].v.f1.id]=(i-1)/2;
			pos[array[(i-1)/2].v.f1.id]=i;
			//swap(array[i],array[(i-1)/2]);
			minheapnode temp=array[i];
			array[i]=array[(i-1)/2];
			array[(i-1)/2]=temp;
			i=(i-1)/2;
		}
	}
	
	public boolean isinminheap(int v1){
		if(pos[v1] < size)
			return true;
		return false;
	}
	
	public void printarr(int dist[],int n){
		for(int i=0;i<n;i++)
			System.out.println(i+" "+dist[i]);
	}
}
class flight{
	int id;
	String name;
	
	public flight(int i,String s){
		id=i;
		name=s;
	}
}


class Node{
	int c1;
	int c2;
	String dep;
	String arr;
	int dur;
	int[] weight;
	flight f1;
	Node link;
	
	public Node(int x,int y,String dep1,String arr1,int dur1,int w,flight f){
		c1=x;
		c2=y;
		dep=dep1;
		arr=arr1;
		dur=dur1;
		f1=f;
		link=null;
	}
	
	public void display(){
		System.out.println(c1+" "+c2+" "+dep+" "+arr+" "+dur+" "+f1.id+" "+f1.name);
		/*for(int i=0;i<weight.length;i++){
			System.out.print(weight[i]);
		}
		System.out.println();*/
	}
}

class linkedlist
{
    protected Node start;
    protected Node end ;
    public int size ;
 
  
    public linkedlist()
    {
        start = null;
        end = null;
        size = 0;
    }
    
    public void insertAtEnd(Node val)
    {
        Node nptr = val;    
        size++ ;    
        if(start == null) 
        {
            start = nptr;
            end = start;
        }
        else 
        {
            end.link=nptr;
            end = nptr;
        }
    }
    
}

class Graph
{ int v;
  linkedlist[] adj;
  
  public Graph(int v1){
	v=v1;
	adj=new linkedlist[v1];
	
	for(int j=0;j<v1;j++){
		adj[j]=new linkedlist();
	}
}

public void addlink(Node a,Node b){
	adj[a.f1.id].insertAtEnd(b);
}

public void display(){
	for(int j=0;j<v;j++){
		Node temp=adj[j].start;
		while(temp != null){
			System.out.println(j);
			temp.display();
			temp=temp.link;
		}
	}
}

public void dijkstra(Node src,Node des,String t1,String t2,Node[] parent,int[] dist,minheap mh,Node[] zZ){
	int V=v;
	/*int[] dist=new int[V];
	Node[] parent=new Node[V];
	minheap mh=new minheap(V);
	for(int j=0;j<V;j++){
		parent[j]=null;
		dist[j]=Integer.MAX_VALUE;
		mh.array[j]=new minheapnode(zZ[j],dist[j]);
		mh.pos[j]=j;
		}*/
	
	mh.array[src.f1.id]=new minheapnode(src,dist[src.f1.id]);
	mh.pos[src.f1.id]=src.f1.id;
	dist[src.f1.id]=0;
	mh.decreaseKey(src.f1.id,dist[src.f1.id]);
	mh.size=v;
	while(mh.isempty()==false){
		minheapnode r=mh.extractmin();
		Node u=r.v;
		//System.out.println("u : "+u.f1.name);
		Node pcrawl=adj[u.f1.id].start;
		while(pcrawl != null){
			if( u.weight[pcrawl.f1.id] >= 0 && (pcrawl.arr.matches("0") && (pcrawl.f1.id==src.f1.id || pcrawl.f1.id==des.f1.id) || Integer.parseInt(pcrawl.arr)<=Integer.parseInt(t2)) && (pcrawl.dep.matches("0") && (pcrawl.f1.id==src.f1.id || pcrawl.f1.id==des.f1.id) || Integer.parseInt(pcrawl.dep) >= Integer.parseInt(t1))){
				int vq=pcrawl.f1.id;
				if(mh.isinminheap(vq)==true && dist[u.f1.id] != Integer.MAX_VALUE && u.weight[pcrawl.f1.id]+pcrawl.dur+dist[u.f1.id] < dist[vq] ){
					dist[vq]=dist[u.f1.id]+u.weight[pcrawl.f1.id]+pcrawl.dur;
					//System.out.println("vq : "+pcrawl.f1.name+" "+dist[vq]);
					parent[vq]=u;
					mh.decreaseKey(vq,dist[vq]);
				}
			}
			pcrawl=pcrawl.link;
		}
	}
	if(parent[des.f1.id] == null){
		System.out.println("-1");
	}
	else{int smallest=Integer.parseInt(t2);
		for(int y=0;y<V;y++){
			if(y != des.f1.id && dist[y]==dist[des.f1.id] && zZ[y].c2==des.f1.id+1){
				//System.out.println(" vor "+zZ[y].f1.name);
				if(Integer.parseInt(zZ[y].dep) < smallest){
					smallest=Integer.parseInt(zZ[y].dep);
					parent[des.f1.id]=zZ[y];
					//System.out.println(" or "+zZ[y].f1.name);
				}
			}
		}
		//System.out.println("bye");
	printpath(parent,des,0);}
}

public void printpath(Node[] parent,Node j,int count){
	if(parent[j.f1.id] == null){
		System.out.println(count-1);
		return;}
	printpath(parent,parent[j.f1.id],count+1);
	if(j.arr.matches("0") == false)
	System.out.print(j.f1.name+" ");
}
	
}


public class Lab12 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Reader.init(System.in);
		try{//long startTime = System.currentTimeMillis();
	    	//long endTime = 0;

			int n=Reader.nextInt();
			int src=Reader.nextInt();
			int des=Reader.nextInt();
			String t1=Reader.next();
			String t2=Reader.next();
			int n1=Reader.nextInt();
			Node[] l1=new Node[n];
			Node[] z=new Node[n+n1];
			int[] dist=new int[n+n1];
			Node[] parent=new Node[n+n1];
			//Node[] desz=new Node[n+n1];
			minheap mh=new minheap(n+n1);
			//linkedlist z=new linkedlist();
			int f1;
			for( f1=0;f1<n;f1++){
				flight r=new flight(f1,Integer.toString(f1));
				Node np=new Node(f1+1,f1+1,"0","0",0,0,r);
				np.weight=new int[n+n1];
				l1[f1]=np;
				z[f1]=np;
				parent[f1]=null;
				dist[f1]=Integer.MAX_VALUE;
				mh.array[f1]=new minheapnode(z[f1],dist[f1]);
				mh.pos[f1]=f1;
			}
			//f1=f1-1;
			Graph g=new Graph(n+n1);
			Node[] l2=new Node[n1];
			//int b=0;
			for(int i=0;i<n1;i++){
				int c1=Reader.nextInt();
				int c2=Reader.nextInt();
				String d=Reader.next();
				String a=Reader.next();
				//int a1=Integer.parseInt(a);
				String f=Reader.next();
				//int f6=Integer.parseInt(f);
				int diff=timediff(a,f);
				flight f9=new flight(f1,d);
		        Node n2=new Node(c1,c2,a,f,diff,0,f9);
		        n2.weight=new int[n+n1];
		        l2[i]=n2;
		        z[f1]=n2;
		        parent[f1]=null;
				dist[f1]=Integer.MAX_VALUE;
				mh.array[f1]=new minheapnode(z[f1],dist[f1]);
				mh.pos[f1]=f1;
				g.adj[c1-1].insertAtEnd(n2);
				g.adj[n2.f1.id].insertAtEnd(l1[c2-1]);
				/*if(n2.c2 == l1[des-1].c1){
					desz[f1]=n2;
				}*/
				/*for(int s=0;s<i;s++){
					Node we=l2[s];
					if(n2.c1 == we.c2){
						we.weight[n2.f1.id]=timediff(we.arr,n2.dep);
						g.addlink(we,n2);
					}
					else if(we.c1 == n2.c2){
						n2.weight[we.f1.id]=timediff(n2.arr,we.dep);
						g.addlink(n2, we);
					}
					
				}*/
		        f1++;
			}
			
			/*for(int e=0;e<n;e++){
				l1[e].display();
			}
			System.out.println();
			for(int f=0;f<n1;f++){
				l2[f].display();
			}*/
			//int m=0;
			/*while(m<n){
				int p=0;
				Node temp1=l1[m];
				//temp1.display();
				while(p<n1){
					Node temp2=l2[p];
					if(temp1.c2 == temp2.c1){
						temp1.weight[temp2.f1.id]=0;
						System.out.println(temp1.f1.id+" -> "+temp2.f1.id);
						g.addlink(temp1, temp2);
						
						g.display();
						System.out.println("end");
					}
					if(temp2.c2 == temp1.c1){
						temp2.weight[temp1.f1.id]=0;
						g.addlink(temp2,temp1);
						//System.out.println("bye1");
						//g.display();
						//System.out.println("end1");
					}
					p++;
					//g.display();
				}
				m++;
			}*/
			for(int i=0;i<n1;i++){
				Node tempp=l2[i];
				for(int j=0;j<n1;j++){
					Node tempq=l2[j];
					if(tempp.c2 == tempq.c1){
						tempp.weight[tempq.f1.id]=timediff(tempp.arr,tempq.dep);
						g.addlink(tempp, tempq);
					}
				}
			}
			//g.display();
			g.dijkstra(l1[src-1],l1[des-1],t1,t2,parent,dist,mh,z);
			//endTime = System.currentTimeMillis();
			//long timeneeded =  ((startTime - endTime) /1000);
			//System.out.println("time needed "+timeneeded);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static int timediff(String a,String f){
		int q=a.length()/2;
		String h1=a.substring(0,q);
		int x=Integer.parseInt(h1)*60;
		String m1=a.substring(q);
		int dsum=x+Integer.parseInt(m1);
		int w=f.length()/2;
		String h2=f.substring(0,w);
		int y=Integer.parseInt(h2)*60;
		String m2=f.substring(w);
		int asum=y+Integer.parseInt(m2);
		int diff=asum-dsum;
		return diff;
	}

}
/** Class for buffered reading int and double values */
class Reader {
    static BufferedReader reader;
    static StringTokenizer tokenizer;

    /** call this method to initialize reader for InputStream */
    static void init(InputStream input) {
        reader = new BufferedReader(
                     new InputStreamReader(input) );
        tokenizer = new StringTokenizer("");
    }

    /** get next word */
    static String next() throws IOException {
        while ( ! tokenizer.hasMoreTokens() ) {
            //TODO add check for eof if necessary
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