//package noob;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.StringTokenizer;


public class flight {

	int c1;
	int c2;
	int d;
	int a;
	String f;
	boolean belong;
	
	flight()
	{
		
	}
	flight(int c1,int c2,String f,int d,int a)
	{
		this.c1 = c1;
		this.c2 = c2;
		this.d = d;
		this.a = a;
		this.f = f;
	}
	int find(int y,int[] parent)
	{
		if(parent[y] == -1)
			return y;
		return find(parent[y],parent);
	}
	/*int checkpath(int[] parent,int src)
	{
		if(src == des)
			return 1;
		else
		return checkpath(parent,parent[src],des);
	}*/
	int timeinMinute(int time)
	{
		return (time/100)*60 + (time-(time/100)*100);
	}
	
	int minTime(int[] time,boolean[] left,int size)
	{
		 int min = Integer.MAX_VALUE, min_index=-1;
		 
	        for (int v = 1; v <= size; v++)
	            if (left[v] == false && time[v] <= min)
	            {
	                min = time[v];
	                min_index = v;
	            }
	 
	        return min_index;
	}
	
	boolean vertexVisit(int str,boolean[] vertexLeft,int size)
	{
		vertexLeft[str] = true;
		for(int i=1;i<=size;i++)
		{
			if(vertexLeft[i] == false)
				return true;
		}
		return false;
	}
	
	void krusk(flight[] a,int size,int nof,flight main)
	{
		int[] parent = new int[size+1];
		int[] time = new int[size+1];
		int[] arrivalTime = new int[size+1];
		int[] departTime = new int[size+1];
		int[] tWait = new int[size+1];
		String[] fname = new String[size+1];
		//boolean[] edgeLeft = new boolean[nof];
	    boolean[] vertexLeft = new boolean[size+1];
	    int edge = 0;
	    boolean vertex = true;
	    for(int i=1;i<=size;i++)
	    {
	    	time[i] = Integer.MAX_VALUE;
	    	tWait[i] = Integer.MAX_VALUE;
	    	vertexLeft[i] = false;
	    }
	    /*for(int i=1;i<=nof;i++)
	    {
	    	edgeLeft[i] = false;
	    }*/
	    time[main.c1] = 0;
	    arrivalTime[main.c1] = main.d;  // setting the when we arrive at src to be the arrival time of src
	    int start = main.c1;
	    
	    
	    while(edge<=nof && vertex && vertexLeft[main.c2]==false)
	    {
	    	for(int i=1;i<=nof;i++)
	    	{
	    		if(a[i].c1 == start)
	    		{
	    			//System.out.println(timeinMinute(a[a[i].c2].d) - timeinMinute(a[i].a) + "<" + tWait[a[i].c2] + " "+ fname[i]);
	    			if(a[i].timeinMinute(a[i].d)-a[i].timeinMinute(arrivalTime[a[i].c1]) > 0  && 
	    					time[a[i].c2] > a[i].timeinMinute(a[i].a) - a[i].timeinMinute(arrivalTime[a[i].c1]))
	    			{
	    				//if(time[a[i].c2] > a[i].timeinMinute(a[i].a) - a[i].timeinMinute(arrivalTime[a[i].c1]))
	    					//{
		    					time[a[i].c2] = a[i].timeinMinute(a[i].a) - a[i].timeinMinute(arrivalTime[a[i].c1]);
			    				parent[a[i].c2] = a[i].c1;
			    				arrivalTime[a[i].c2] = a[i].a;
			    				departTime[a[i].c1] = a[i].d;
			    				tWait[a[i].c2] = timeinMinute(a[a[i].c2].d) - timeinMinute(a[i].a);
			    				fname[a[i].c2] = a[i].f;
			    				//System.out.println("hoho");
	    					//}
	    				/*else
	    				{
	    					 if(a[a[i].c2].d > a[i].a) // if the joining flight has no probs with depart time
	    					 {
			    					time[a[i].c2] = a[i].timeinMinute(a[i].a) - a[i].timeinMinute(arrivalTime[a[i].c1]);
				    				parent[a[i].c2] = a[i].c1;
				    				arrivalTime[a[i].c2] = a[i].a;
				    				departTime[a[i].c1] = a[i].d;
				    				tWait[a[i].c2] = timeinMinute(a[a[i].c2].d) - timeinMinute(a[i].a);
				    				fname[a[i].c2] = a[i].f;
				    				System.out.println("hoho");
	    					 }
	    				}*/
	    			}
	    			edge++;
	    			
	    		}
	    	}
	    	// mark start as visited
	    	vertex = main.vertexVisit(start,vertexLeft,size);
	    	int u = minTime(time,vertexLeft,size);
	    	start = u;
	    }
	    //System.out.println("lolo");
	    int end = main.c2;
	    int count = 0;
	    String[] usedFlight = new String[size];
	    if(time[main.c2] < timeinMinute(main.a) - timeinMinute(main.d) && arrivalTime[main.c2] < main.a)
	    {
	    	while(end != main.c1)
	    	{
	    		//System.out.println(fname[end] + "yoyo");
	    		usedFlight[count] = fname[end];
	    		end = parent[end];
	    		count++;
	    	}
	    	System.out.println(count);
	    	for(int i=count-1;i>=0;i--)
	    	{
	    		System.out.print(usedFlight[i]+ " ");
	    	}
	    }
	    else
	    {
	    	System.out.println(-1);
	    }
	    
	    
	    
	    
	    
	    
	    
	    /*int count = 0;  //////
	    int start = main.c1;
	    int starttime ;
	    int endtime ;
	    
	    for(int i=1;i<=size;i++)
	    {
	    	key[i] = Integer.MAX_VALUE; 
	    	left[i] = false;
	    }
	    key[main.c1] = 0;
	    
	    for(int i=1;i<=size-1;i++)
	    {
	    	int u = minDistance(key, left, size);
	    	left[u] = true;
	    	
	    }
	    /*{
	    	if(info[i].c1 == main.c1)
	    	{
    			root[info[i].c2] = c1;
    			key[info[i].c2] = getnettime(info,i,main);
	    	}
	    }*/
	    
	    /////////////////////////////////////// use dkistra to find path of all
	    //////////// key is the net dist 
	    ///////////  net dist contains evrything from flght duration to flight wait
	    //////////// for that we need the cur details and the next ones
	    //////////// we'll fill the key and psrent acc. till all haavent been visited
	    
	    
	    /*while(notempty(boolean) && )
	    {
	    	for(int i=1;i<=nof;i++)
	    	{
	    		if(arr[i].c1 == start)
	    		{
	    			root[arr[i].c2] = c1;
	    			key[arr[i].c2] = getnettime(arr,i,);
	    		}
	    	}
	    }*/
	    
	}
	
	public static void main(String[] args) throws IOException
	{
		//boolean[] left = new boolean[4];
		//int time = 1010;
		//System.out.println((time/100)*60 + (time-(time/100)*100));
		Reader.init(System.in);
		int x = Reader.nextInt(); // no. of cities
		int src = Reader.nextInt();
		int des = Reader.nextInt();
		int t1 = Reader.nextInt();
		int t2 = Reader.nextInt();
		int n = Reader.nextInt(); //no. of flights
		flight main = new flight(src,des,null,t1,t2);
		flight[] arr = new flight[n+1];
		for(int i=1;i<=n;i++)
		{
			flight temp = new flight(Reader.nextInt(),Reader.nextInt(),Reader.next(),Reader.nextInt(),Reader.nextInt());
			arr[i] = temp;
		}
		int[] start = new int[n+1];
		int[] end = new int[n+1];
		for(int i=1;i<=n;i++)
		{
			start[i] = arr[i].c1;
		}
		for(int i=1;i<=n;i++)
		{
			end[i] = arr[i].c2;
		}
		/*flight[][] m = new flight[x+1][x+1];
		for(int i=1;i<=x;i++)
		{
			for(int j=1;j<=x;j++)
			{
			
			}
		}*/
		int[] parent = new int[x+1];
		int path = -1;
		for(int i=1;i<=x;i++)
			parent[i] = -1;
		if(src < des)
		{
			for(int i=1;i<=n;i++)
			{
				int s = arr[i].find(start[i],parent);
				int e = arr[i].find(end[i],parent);
				if(s != e)
				{
					parent[e] = s;
				}
			}
		}
		else
		{
			for(int i=n;i>=1;i--)
			{
				int s = arr[i].find(start[i],parent);
				int e = arr[i].find(end[i],parent);
				if(s != e)
				{
					parent[e] = s;
				}
			}
		}
		/*for(int i=1;i<=x;i++)
			System.out.print(parent[i] + " ");
			*/
		//int ch = checkpath(parent,src,des);
		/*int tempsrc;
		for(int i=1;i<=n;i++)
		{
			if(parent[i] == src)
				tempsrc = i;
				break;
		}*/
		if(parent[des] == src)
		{
			//System.out.println("pat is there");
			path = 1;
		}
		else
		{
			//System.out.println("no path" + -1);
			path = -1;
		}
		///////////////////// path checked above
		if(n == 1)
		{
			if(t1<arr[1].d && t2>arr[1].a)
			{
				System.out.println(1);
				System.out.println(arr[1].f);
			}
			else
				System.out.println(-1);
		}
		else
		{
			if(path == 1)
				arr[1].krusk(arr,x,n,main);
		}
	}
}

class Reader
{
	 static BufferedReader reader;
	 static StringTokenizer tokenizer;
	/** Class for buffered reading int and double values */

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
	        tokenizer = new StringTokenizer( reader.readLine() );
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

