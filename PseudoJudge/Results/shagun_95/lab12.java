package lab12;
import java.util.StringTokenizer;
import java.io.*;


public class lab12
{
	static BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
	static Node[][] array;
	static int[] array1,sum;
	static int time_limit;
	static Node[] queue;
	static int top=-1,X;
	static int answer=0,min_time=0,look=0,end=0,look1=0;
	static int src=0,des=0,o=0;
	static Node p;
	static int min_value=999999;
	static int m=0;
	static String ans="";
	
	public static void main(String args[])throws IOException
	{
		//input
		
		X=Integer.parseInt(br.readLine());
		
		String a1=br.readLine();
		StringTokenizer st=new StringTokenizer(a1);
		src=Integer.parseInt(st.nextToken());
		des=Integer.parseInt(st.nextToken());
		int t1=Integer.parseInt(st.nextToken());
		int t2=Integer.parseInt(st.nextToken());
		
		array=new Node[X][X];
		
		String a2=br.readLine();
		StringTokenizer st1=new StringTokenizer(a2);
		int N=Integer.parseInt(st1.nextToken());
		int in_flight;
		queue=new Node[N];
		
		for(int g=0;g<N;g++)
		{
			String a3=br.readLine();
			StringTokenizer st2=new StringTokenizer(a3);
			
			int c1=Integer.parseInt(st2.nextToken());
			int c2=Integer.parseInt(st2.nextToken());
			String f=st2.nextToken();
			int d=Integer.parseInt(st2.nextToken());
			int a=Integer.parseInt(st2.nextToken());
			
			if((int)(a/100)==(int)(d/100))
			{
				in_flight=a-d;
			}
			else
			{
				in_flight=((a/100-d/100)-1)*60+(60-(d%100))+a%100;
			}
			
			if(d>t1 && a<t2)
			{
				String y="unvisited";
				if(c1==src)
					p=new Node(c1,c2,f,d,a,in_flight,y,in_flight,c1,c2);
				else
					p=new Node(c1,c2,f,d,a,in_flight,y,999999,c1,c2);
				array[c1-1][c2-1]=p;
				top++;
				queue[top]=array[c1-1][c2-1];
			}
		
		}
		
		int ptr=top;
		int location=0;
		/*for(int h=top;h>=0;h--)
		{
			System.out.println(queue[h].value+" "+queue[h].in_flight);
		}*/
		
		for(int a=0;a<top+1;a++)
		{	
			
					location=find_min();
					if(location != -1)
					{
						min_time=queue[location].value;
						//System.out.println(min_time);
						look=queue[location].city_2;
						look1=queue[location].city_1;
						queue[location].status="visited";
						end=queue[location].time_2;
						
						if(look!=des)
							sum=required_time();
						for(int k=0;k<top+1;k++)
						{
							if(queue[k].city_1==look && queue[k].city_2==(sum[0]+1))
								{
									queue[k].value=sum[1];
								}
						}
					
					}
			
		}
		int answer=999999;
		int in=0;
		String ans="";
		Node x=null;
		for(int u=0;u<X;u++)
		{
			//System.out.println(u+" "+des+" "+array[2][des-1].value);
			if(array[u][des-1]!=null && array[u][des-1].value<answer)
			{
				
				answer=array[u][des-1].value;
				in=u;
				x=array[u][des-1];
				
			}
			if(answer!=999999 && array[u][des-1]!=null && array[u][des-1].value==answer)
			{
				if(array[u][des-1].time_2<x.time_2)
					x=array[u][des-1];
			}
			
		}
		if(x==null)
			System.out.println(-1);
		else
		{
		ans=x.flight_no+" "+ans;
		int count=1;
		while(x.p_x!=src)
		{
			x=array[x.p_x-1][x.p_y-1];
			count+=1;
			ans=x.flight_no+" "+ans;
		}
		if(N!=1)
		{
		x=array[x.p_x-1][x.p_y-1];
		count+=1;
		ans=x.flight_no+" "+ans;
		}
		System.out.println(count);
		System.out.println(ans);
		}
		
		//System.out.println(top);
		/*for(int h=top;h>=0;h--)
		{
			System.out.println(queue[h].value);
		}*/
		
	}
	
	public static int[] required_time()
	{
		int pos=0;
		int new_time=0;
		array1=new int[2];
		
		for(int i=0;i<X;i++)
		{
			int min_time1=min_time;
			int wait_time=0;
			if(array[look-1][i]!=null)
			{
				
				if(end<=array[look-1][i].time_1)
				{
				if((int)(end/100)==(int)(array[look-1][i].time_1/100))
					wait_time=(array[look-1][i].time_1-end);
				else
					wait_time=((array[look-1][i].time_1/100-end/100)-1)*60+(60-(end%100))+array[look-1][i].time_1%100;
				
				min_time1+=wait_time+time_difference(array[look-1][i]);
				//System.out.println(wait_time);
				if(new_time==0)
					new_time=min_time1;
				}

			}
			if(new_time!=0 && new_time<=min_time1)
			{
				pos=i;
				new_time=min_time1;
			}
		}
		//System.out.println(new_time);
		if(new_time!=0)
		{
			//System.out.println(look);
			array[look-1][pos].p_x=look1;
			array[look-1][pos].p_y=look;
		}
		array1[0]=pos;
		array1[1]=new_time;
		
		return array1;
	}
	
	public static int time_difference(Node a)
	{
		int flight=0;
		if((int)(a.time_2/100)==(int)(a.time_1/100))
		{
			flight=a.time_2-a.time_1;
		}
		else
		{
			flight=((a.time_2/100-a.time_1/100)-1)*60+(60-(a.time_1%100))+a.time_2%100;
		}
		return flight;
	}
	
	public static int find_min()
	{
		int p=-1;
		min_value=999999;
		for(int r=0;r<=top;r++)
		{
			
			if(queue[r].status.equals("unvisited") && queue[r].value<min_value && queue[r].value!=m)
			{
				p=r;
				min_value=queue[r].value;
				
			}
		}
		//System.out.println(min_value);
		m=min_value;
		if(p!=-1)
		queue[p].status="visited";
		return p;
	}
}

class Node
{
	public int city_1;
	public int city_2;
	public String flight_no;
	public int time_1;
	public int time_2;
	public int in_flight;
	public String status;
	public int value;
	public int p_x,p_y;
	
	
	public Node(int c1,int c2,String f,int time_1,int time_2,int in_flight,String status,int value, int px, int py)
	{
		city_1=c1;
		city_2=c2;
		flight_no=f;
		this.time_1=time_1;
		this.time_2=time_2;	
		this.in_flight=in_flight;
		this.status=status;
		this.value=value;
		p_x=px;
		p_y=py;
	}
}