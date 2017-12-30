import java.util.*;
import java.lang.*;
import java.io.*;

class lab12
{
	public static void main(String[] args) throws IOException
	{
		Reader.init(System.in);

		int c=Reader.nextInt();

		int src=Reader.nextInt();
		int des=Reader.nextInt();
		int start=Reader.nextInt();
		int end=Reader.nextInt();

		int f=Reader.nextInt();

		Node[] af=new Node[f+2];

		int legalf=1;
		while(f-->0)
		{
			int c1=Reader.nextInt();
			int c2=Reader.nextInt();
			String fname=Reader.next();
			int dep=Reader.nextInt();
			int arr=Reader.nextInt();
			int tt=0;
			int nn=legalf;

			if(c1!=des && c2!=src && dep>=start && arr<=end)
			{
				// int a=(arr/100*60)+(arr%100);
				// int b=(dep/100*60)+(dep%100);
				tt=(arr/100*60)+(arr%100);
				tt=tt-(dep/100*60)-(dep%100);

				af[legalf]=new Node(c1,c2,fname,arr,dep,tt,nn);
				// n[c1].link

				legalf++;
			}
		}

		Node[] G=new Node[++legalf];
		for(int i=0;i<legalf;i++)
		{
			G[i]=new Node();
		}
		G[legalf-1].nn=legalf-1;
		// 0th node or start node and end node
		for(int i=1;i<legalf-1;i++)
		{
			if(af[i].c1==src)
			{
				Node temp=G[0].link;
				G[0].link=af[i];
				af[i].link=temp;
				// System.out.println(i);
			}
			if(af[i].c2==des)
			{
				G[i].link=G[legalf-1];
				// System.out.println(i);
			}
		}

		for(int i=1;i<legalf-1;i++)
		{
			for(int j=1;j<legalf-1;j++)
			{
				if(af[i].c2==af[j].c1)
				{
					Node temp=G[i].link;
					G[i].link=af[j];
					af[j].link=temp;
				}
			}
		}

		// for(int i=0;i<legalf;i++)
		// {
		// 	Node ptr=G[i].link;
		// 	System.out.print(i+": ");
		// 	while(ptr!=null)
		// 	{
		// 		System.out.print(ptr.nn+" "+ptr.tt+", ");
		// 		ptr=ptr.link;
		// 	}
		// 	System.out.println();
		// }

		// Dijkstra's algo
		int[] D=new int[legalf];
		for(int i=1;i<legalf;i++)
		{
			D[i]=Integer.MAX_VALUE;
		}

		int[] vis=new int[legalf];
		int[] q=new int[legalf];
		int[] P=new int[legalf];

		int lr=legalf;
		while(lr-->0)
		{
			int min=Integer.MAX_VALUE;
			int u=0;
			for(int i=0;i<legalf;i++) //min element in D
			{
				if(D[i]<=min && vis[i]==0)
				{
					min=D[i];
					u=i;
				}
			}

			vis[u]=1;
			// if(lr==0)
			// {
			// 	System.out.println("last node:"+u);
			// }
			Node ptr=G[u].link;
			while(ptr!=null)
			{

				int w=W(u , ptr.nn, legalf, af);
				// if(lr==0)
				// {
				// 	System.out.println(D[ptr.nn]+" "+w+"nn="+ptr.nn);
				// }
				if(w<0)
				{
					D[ptr.nn]=Integer.MAX_VALUE;
					ptr=ptr.link;
					continue;
				}
				if(D[ptr.nn] > D[u] && D[ptr.nn] > D[u] + w + ptr.tt)
				{
					D[ptr.nn]=D[u] + w + ptr.tt;
					P[ptr.nn]=u;
					// if(lr==0)
					// {
					// 	System.out.println(D[u]+" "+w+" "+ptr.tt);
					// }
				}
				ptr=ptr.link;
			}
		// for(int i=0;i<legalf;i++)
		// {
		// 	System.out.println(D[i]);
		// }
		// System.out.println("-------");
		}
		// for(int i=0;i<legalf;i++)
		// {
		// 	System.out.println(D[i]+" "+P[i]);
		// }

		if(D[legalf-1]>=Integer.MAX_VALUE)
		{
			System.out.println("-1");
			return;
		}
		int par=P[legalf-1];
		String[] res=new String[legalf];
		
		int k=0;
		while(true)
		{
 			res[k]=af[par].fname;
 			par=P[par];
 			k++;
 			if(par==0)
 			{
 				break;
 			}
		}
		System.out.println(k);
		while(k-->0)
		{
			System.out.print(res[k]+" ");
		}
	}
	public static int W(int a, int b,int legalf, Node[] af)
	{
		if(a==0 || b==legalf-1)
		{
			// System.out.println(a);
			return 0;
		}
		int t1=(af[a].arr/100*60)+(af[a].arr%100);
		int t=((af[b].dep/100)*60)+(af[b].dep%100)-t1;
		// if(a==3 && b==5)
		// {
		// 	System.out.println("here is the wait:"+t);
		// }
		return t;
	}
}

class Node
{
	int c1;
	int c2;
	String fname;
	int arr;
	int dep;
	int tt;
	int nn;
	Node link;
	Node(int c1, int c2, String fname, int arr, int dep, int tt, int nn)
	{
		this.c1=c1;
		this.c2=c2;
		this.fname=fname;
		this.arr=arr;
		this.dep=dep;
		this.tt=tt;
		this.nn=nn;
		link=null;
	}
	Node()
	{
		link=null;
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