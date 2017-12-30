import java.io.*;
import java.util.*;
class Reader {
    static BufferedReader reader;
    static StringTokenizer tokenizer;

 
    static void init(InputStream input) {
        reader = new BufferedReader(
                     new InputStreamReader(input) );
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
	
}
class Edge{
	String flight;
	int arrivalatc2;
	int departfromc1;
	Edge(){
		flight="";
		arrivalatc2=0;
		departfromc1=0;
	}
	Edge(String c,int d,int e){
		flight=c;
		arrivalatc2=e;
		departfromc1=d;
	}
}

class Vertex{
	int city;
	int time;
	int arrivaltime;
	boolean visit;
	String name;
	Vertex path;
	Vertex(int i){
		path=null;
		visit=false;
		time=Integer.MAX_VALUE;
		city=i;
		arrivaltime=0;
		name="";
	}
	Vertex(){
		path=null;
		visit=false;
		time=Integer.MAX_VALUE;
		city=0;
		arrivaltime=0;
		name="";
	}
	Vertex(int a,int b,int c,String d,Vertex e){
		city=a;
		time=b;
		arrivaltime=c;
		name=d;
		path=e;
		visit=false;
	}
}
public class lab122{
	public static void main(String[] args) throws Exception{
		Reader.init(System.in);
		int cityno=Reader.nextInt();
		Vertex []ver=new Vertex[cityno+1];
		int src=Reader.nextInt();
		int end=Reader.nextInt();
		int starttime=Reader.nextInt();
		starttime=starttime/100*60+starttime%100;
		int finishtime=Reader.nextInt();
		finishtime=finishtime/100*60+finishtime%100;
		int flno=Reader.nextInt();
		String []arr=new String[flno+1];
		Edge graph[][]=new Edge[cityno+1][cityno+1];
		for(int i=0;i<flno;i++){
			int a=Reader.nextInt();
			int b=Reader.nextInt();
			String c=Reader.next();
			int d=Reader.nextInt();
			int d1=d/100*60;
			int d2=d%100;
			int e=Reader.nextInt();
			int e1=e/100*60;
			int e2=e%100;
			graph[a][b]=new Edge(c,d1+d2,e1+e2);
			
		}
		for(int i=1;i<ver.length;i++)
			ver[i]=new Vertex(i);
		int flag=0;
		int check=0;
		for(;;){
			Vertex v=new Vertex();
			if(flag==0){
				v.city=src;
				v.time=0;
				flag=1;
			}
			else{
				v=min(ver);
			}

			if(v==null)
				break;
			v.visit=true;
			if(v.city==end)
				break;
			if(v.city==src){
				for(int i=1;i<ver.length;i++){
					if(graph[v.city][ver[i].city]!=null&&ver[i].visit==false&&v.time!=Integer.MAX_VALUE){

						if(graph[v.city][ver[i].city].arrivalatc2-graph[v.city][ver[i].city].departfromc1+v.time<ver[i].time&&graph[v.city][ver[i].city].departfromc1>=starttime&&graph[v.city][ver[i].city].arrivalatc2<=finishtime){
							
							ver[i].time=0;
							ver[i].time=graph[v.city][ver[i].city].arrivalatc2-graph[v.city][ver[i].city].departfromc1+v.time;
							ver[i].arrivaltime=graph[v.city][ver[i].city].arrivalatc2;
							ver[i].name=graph[v.city][ver[i].city].flight;
							ver[i].path=v;
						}
					}
				}
			}
			
			else{
				for(int i=1;i<ver.length;i++){
					if(graph[v.city][ver[i].city]!=null&&ver[i].visit==false&&v.time!=Integer.MAX_VALUE){ 			
						if(graph[v.city][ver[i].city].arrivalatc2-graph[v.city][ver[i].city].departfromc1+v.time+graph[v.city][ver[i].city].departfromc1-v.arrivaltime<ver[i].time&&graph[v.city][ver[i].city].departfromc1>=starttime&&graph[v.city][ver[i].city].departfromc1>=v.arrivaltime&&graph[v.city][ver[i].city].arrivalatc2<=finishtime){
							ver[i].time=0;
						 	ver[i].time=graph[v.city][ver[i].city].arrivalatc2-graph[v.city][ver[i].city].departfromc1+v.time+graph[v.city][ver[i].city].departfromc1-v.arrivaltime;
						 	ver[i].arrivaltime=graph[v.city][ver[i].city].arrivalatc2;
						 	ver[i].name=graph[v.city][ver[i].city].flight;
						 	ver[i].path=v;
						}
					}
				}
			}
		}
		Vertex v=new Vertex();
		v=ver[end];
		if(v.arrivaltime>finishtime||v.visit==false)
			System.out.println(-1);
		else{
			int i=0;
			Vertex a=new Vertex();
			a=ver[end];
			while(a!=null){
				arr[i]=a.name;
				i++;
				a=a.path;
			}
			i=i-1;
			System.out.println(i);
			i--;
			while(i>=0){
				if(i==0){
					System.out.println(arr[i]+" ");
				}
				else
					System.out.print(arr[i]+" ");
				i--;
			}
		}
		
	}
	public static Vertex min(Vertex[] v){
		Vertex min=null;
		for(int i=1;i<v.length;i++){
			if(v[i].visit==false&&v[i].time!=Integer.MAX_VALUE&&(min==null||min.time>v[i].time)){
				min=v[i];
			}
		}
		return min;
	}

}
