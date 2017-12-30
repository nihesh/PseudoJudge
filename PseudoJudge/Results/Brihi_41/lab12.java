import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.InputMismatchException;

//Reader class to take inputs
class InputReader
{
    private InputStream stream;
	private byte[] buf = new byte[1024];
	private int curChar;
	private int numChars;
	private SpaceCharFilter filter;

	public InputReader(InputStream stream) {
		this.stream = stream;
	}

	public int read() {
		if (numChars == -1)
			throw new InputMismatchException();
		if (curChar >= numChars) {
			curChar = 0;
			try {
				numChars = stream.read(buf);
			} catch (IOException e) {
				throw new InputMismatchException();
			}
			if (numChars <= 0)
				return -1;
		}
		return buf[curChar++];
	}

	public int readInt() {
		int c = read();
		while (isSpaceChar(c))
			c = read();
		int sgn = 1;
		if (c == '-') {
			sgn = -1;
			c = read();
		}
		int res = 0;
		do {
			if (c < '0' || c > '9')
				throw new InputMismatchException();
			res *= 10;
			res += c - '0';
			c = read();
		} while (!isSpaceChar(c));
		return res * sgn;
	}

	public String readString() {
		int c = read();
		while (isSpaceChar(c))
			c = read();
		StringBuilder res = new StringBuilder();
		do {
			res.appendCodePoint(c);
			c = read();
		} while (!isSpaceChar(c));
		return res.toString();
	}

	public boolean isSpaceChar(int c) {
		if (filter != null)
			return filter.isSpaceChar(c);
		return c == ' ' || c == '\n' || c == '\r' || c == '\t' || c == -1;
	}

	public String next() {
		return readString();
	}

	public interface SpaceCharFilter {
		public boolean isSpaceChar(int ch);
	}
}


//----------------------Actual code for lab starts----------------------
class flight{
	public int from;
	public int to;
	public String flightname;
	public int dept;
	public int arrival;
	public int traveltime;
	public flight link;
	
	public flight(int f,int t, String fn, int d, int a){
		from=f;
		to=t;
		flightname=fn;
		dept=d;
		arrival=a;
		traveltime=time_convert(a)-time_convert(d);
		link=null;
	}
	public flight (){
	}
	
	public int time_convert(int a){
		int minutes=a%100;
		int h1=a/100 %10;
		int h2=a/1000 %10;
		int hours=h2*10 + h1;
		int time=hours*60+minutes;
		return time;
	}
}

class parent{
	int par;
	String flightname;
	int arrivaltime;
}

class flight_graph{
	public flight[] map;
	public int x;
	public int n;
	public int t1; // given initial time;
	public int t2; // given final time;
	public int start;
	public int end;
	public flight_graph(int x, int n, int t1, int t2, int s, int e){
		this.x=x;
		this.n=n;
		this.t1=t1;
		this.t2=t2;
		map=new flight[x+1];
		for (int i=1;i<=x;i++){
			map[i]=new flight();
		}
		start=s;
		end=e;
	}
	
	public void add_flight(flight f){
		if((f.dept>=t1) && (f.arrival<=t2)){
			flight start=map[f.from];
			//adding elements to the linked list
			while (start.link!=null){
				start=start.link;
			}
			start.link=f;
		}
	}
	
	public void printmap(){
		for (int i=1;i<=x;i++){
			flight ptr=map[i].link;
			while (ptr!=null){
				System.out.println(ptr.flightname);
				System.out.println(ptr.traveltime);
				ptr=ptr.link;
			}
		}
	}
	
	
	//for finding the minimum vertex
	public int mincity(int[] dist, boolean[] v){
		int x=Integer.MAX_VALUE;	
		int y=-1; 	//Graph not connected or there are no unvisited vertices
		
		for (int i=1; i<dist.length;i++){
			if (!v[i] && dist[i]<x){
				y=i;
				x=dist[i];
			}
		}
		return y;
	}
	
	public parent[] dijkstra(int s){
		int[] dist= new int[map.length];
		parent[] pred= new parent[map.length];
		boolean[] visited= new boolean[map.length];
		
		for (int i=1;i<dist.length;i++){
			dist[i]=Integer.MAX_VALUE;
		}
		dist[s]=0;
		for (int i=1;i<dist.length;i++){
			pred[i]=new parent();
		}
		//int flag=0;
		for (int i=1;i<dist.length;i++){
			int next=mincity(dist,visited);
			//System.out.println(next);
			
			if (next!=-1){
				//flag=1;
			//the shortest path to next is dist[next] and via pred[next]
				if (pred[next].flightname==null){
					visited[next]=true;
					flight n=map[next];
					flight ptr=n;
					while (ptr!=null){
						int d=dist[next]+ptr.traveltime;
						if (dist[ptr.to]>d){
							dist[ptr.to]=d;
							pred[ptr.to].par=next;
							pred[ptr.to].flightname=ptr.flightname;
							pred[ptr.to].arrivaltime=ptr.arrival;
							/*System.out.println("next-flight"); 
							System.out.println(ptr.from);
							System.out.println(ptr.to);
							System.out.println(pred[ptr.to].par);
							System.out.println(pred[ptr.to].flightname);
							System.out.println(pred[ptr.to].arrivaltime);
							System.out.println();*/
						}
						ptr=ptr.link;
					}
				}
				else{
					//int flag=0;
					visited[next]=true;
					flight n=map[next];
					flight ptr=n.link;
					int arr=pred[next].arrivaltime;
					//System.out.println(arr);
					while (ptr!=null){
						int dep=ptr.dept;
						//System.out.println(dep);
						int wt=time_convert(dep)-time_convert(arr);
						//System.out.println(wt);
						if (wt>=0){
							int d=dist[next]+ptr.traveltime+wt;
							if (dist[ptr.to]>d){
								dist[ptr.to]=d;
								pred[ptr.to].par=next;
								pred[ptr.to].flightname=ptr.flightname;
								pred[ptr.to].arrivaltime=ptr.arrival;
								/*System.out.println("next-flight");
								System.out.println(ptr.from);
								System.out.println(ptr.to);
								System.out.println(pred[ptr.to].par);
								System.out.println(pred[ptr.to].flightname);
								System.out.println(pred[ptr.to].arrivaltime);
								System.out.println();*/
							}
							ptr=ptr.link;
						}
						else{
							ptr=ptr.link;
						}
					}
					//System.out.println(flag);
				}
			}
		}
		//System.out.println(Arrays.toString(dist));
		return pred;
	}
	
	public int time_convert(int a){
		int minutes=a%100;
		int h1=a/100 %10;
		int h2=a/1000 %10;
		int hours=h2*10 + h1;
		int time=hours*60+minutes;
		return time;
	}
	
	public void printflights(parent[] p){
		int t=end;
		stackz st=new stackz(n);
		if (p[t].par!=0){
			//System.out.println(p[t].par);
			if (p[t].par==start){
				st.push(p[t].flightname);
			}
			else{
				while(p[t].par!=start){
					//System.out.println("Here");
					st.push(p[t].flightname);
					t=p[t].par;
				}
				st.push(p[t].flightname);
			}
			System.out.println(st.size);
			//System.out.println("Here");
			while (st.isEmpty()==false){
				System.out.print(st.pop()+" ");
			}
		}
		else{
			System.out.println(-1);
		}
	}
}

class stackz{
	int size;
	private String[] arr;
	private int topelem;
	
	public stackz(int n){
		arr=new String[n];
		topelem=-1;
	}
	public void push(String j){
		arr[++topelem]=j;
		size++;
	}
	public String pop(){
		size--;
		return arr[topelem--];
	}
	public String peek(){
		return arr[topelem];
	}
	public boolean isEmpty(){
		return topelem==-1;
	}
	
}

public class lab12 {

	public static void main(String[] args) throws Exception{
		// TODO Auto-generated method stub
		InputReader r = new InputReader(System.in);
		int x=r.readInt();
		int start=r.readInt();
		int end=r.readInt();
		int t1=r.readInt();;
		int t2=r.readInt();
		int n=r.readInt();
		flight_graph graph=new flight_graph(x,n,t1,t2,start,end);
		int n_temp=n;
		while (n_temp-->0){
			int f=r.readInt();
			int t = r.readInt();
			String fn = r.readString();
			int d = r.readInt();
			int a= r.readInt();
			flight f1=new flight(f,t,fn,d,a);
			graph.add_flight(f1);
		}
		//graph.printmap();
		graph.printflights(graph.dijkstra(start));
		
		
	}

} 
