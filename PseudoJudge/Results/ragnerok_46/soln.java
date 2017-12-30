import java.util.*;
import java.io.*;
import java.lang.*;

class Node
{
	//t2 is time of arrival and t1 is time of departure
	 int src, dest, t1, t2;
	 String name;
	Node next;

	public Node(int a, int b, int c, int d, String r)
	{
		src = a;
		dest = b;
		t1 = c;
		t2 = d;
		name = r;
		next = null;
	}
}

class linkedList
{
	Node head = null;

	void push_back(Node x)
	{
		if(head == null)
		{
			head = x;
			return;
		}
		Node crawler = head;
		while(crawler.next != null)
		{
			crawler = crawler.next;
		}
		crawler.next = x;
	}

}

public class soln
{
	static linkedList[] v;
	static double[] time = new double[1000];
	static int x;
	static int[] visited = new int[1000];
	static Node[] parent = new Node[1000];
	static int vert = 0;
	static double inf = Double.POSITIVE_INFINITY;

	public soln()
	{
		for(int i = 0; i < 1000; ++i)
		{
			parent[i] = null;
		}
	}

	static int sub(int t2, int t1)
	{
		if(t1 == -1)
			return 0;
		int h2 = t2/100, h1 = t1/100;
		int m2 = t2%100, m1 = t1%100;
		int ans = 0;
		if(m2 < m1)
		{
			h2--;
			m2 += 60;
		}
		ans += (m2 - m1) + ((h2 - h1)*60);
		return ans;
	}

	static void dijkstra(int src, int dest, int t1, int t2)
	{
		for(int i = 1; i <= x; ++i)
		{
			if(i != src)
			{
				time[i] = inf;
			}
		}
		//int never = src;
		Node n = new Node(0, src, -1, -1, "XX");
		parent[src] = n;
		int ans = 0;

		while(true)
		{
			double min = inf;
			int fl = -1;
			for(int i = 1; i <= x; ++i)
			{
				if(time[i] < min && visited[i] == 0)
				{
					min = time[i];
					fl = i;
				}
			}
			if(fl == -1)
			{
				ans = -1;
				break;
			}
			//System.out.println(fl + " " + time[fl]);
			visited[fl] = 1;

			if(fl == dest)
				break;
			Node cr = v[fl].head;
			while(cr != null)
			{
				if(parent[fl].t2 > cr.t1)
				{
					cr = cr.next;
					continue;
				}

				if(time[cr.dest] > time[fl] + sub(cr.t2, cr.t1) + sub(cr.t1, parent[fl].t2))
				{
					//System.out.println(fl);
					time[cr.dest] = time[fl] + sub(cr.t2, cr.t1) + sub(cr.t1, parent[fl].t2);
					parent[cr.dest] = cr;
				}
				cr = cr.next;
			}
		}

		if(ans == -1)
		{
			System.out.println(-1);
			return;
		}
		String[] flights = new String[1000];
		//TODO : implement output properly
		int c = 0;
		while(true)
		{
			Node daddy = parent[dest];
			if(daddy.src == src)
			{
				flights[c] = daddy.name;
				c++;
				break;
			}
			flights[c] = daddy.name;
			c++;
			dest = daddy.src;
		}
		System.out.println(c);
		for(int i = c - 1; i > -1; --i)
		{
			System.out.print(flights[i] + " ");
		}
	}

	public static void main(String[] args)
	{
		Scanner sc =  new Scanner(System.in);

		//input src, dest, arrival and dept
		int src, dest, t1, t2, n;
		x = sc.nextInt();
		src = sc.nextInt();
		dest = sc.nextInt();
		t1 = sc.nextInt();
		t2 = sc.nextInt();
		n = sc.nextInt();

		Node data;

		//create vector
		v = new linkedList[x + 1];
		for(int i = 0; i <= x; ++i)
		{
			v[i] = new linkedList();
		}

		for(int i = 0; i < n; ++i)
		{
			int a = sc.nextInt(), b = sc.nextInt();
			String name = sc.next();
			int dept = sc.nextInt(), arriv = sc.nextInt();
			//if dept is before given interval or arrival is after given interval reject it
			if(dept < t1 || arriv > t2)
				continue;
			data = new Node(a, b, dept, arriv, name);
			vert++;
			//else add to graph
			v[a].push_back(data);
		}

		//appky dijkstra
		dijkstra(src, dest, t1, t2);
	}
}