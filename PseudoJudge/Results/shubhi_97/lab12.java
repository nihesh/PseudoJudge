import java.io.*;
import java.util.StringTokenizer;
public class lab12 {

	static flight arr[][],Q[];
	static int x,C1,C2,D,A,num,q=-1;
	
	public static void main(String args[])throws IOException
	{
		BufferedReader BR=new BufferedReader(new InputStreamReader(System.in));
		lab12 obj=new lab12();
		
		x=Integer.parseInt(BR.readLine());
		String c=BR.readLine();
		StringTokenizer st=new StringTokenizer(c);
		C1=Integer.parseInt(st.nextToken());
		C2=Integer.parseInt(st.nextToken());
		D=Integer.parseInt(st.nextToken());
		A=Integer.parseInt(st.nextToken());
		
		num=Integer.parseInt(BR.readLine());
		
		arr=new flight[x][x]; 
		Q=new flight[num];
		
	//	System.out.println(C1+" "+C2+" "+D+" "+A);
		for (int i=0;i<num;i++)
		{
			
			flight new1=new flight();
			String cn=BR.readLine();
			
			st=new StringTokenizer(cn);
			new1.c1=Integer.parseInt(st.nextToken());
			new1.c2=Integer.parseInt(st.nextToken());
			new1.f=st.nextToken();
			new1.d=Integer.parseInt(st.nextToken());
			new1.a=Integer.parseInt(st.nextToken());
			
			if(new1.c2==C1)
				continue;
			if(new1.d<D || new1.a>A ||new1.d<0500 || new1.d>2300)
				continue;
			if(new1.c1==C2)
				continue;
			
			if(new1.d/100==new1.a/100)
				new1.in=new1.a-new1.d;
			else
			{
				int s=(new1.a/100)-(new1.d/100);
				new1.in=(60*(s-1))+(60-(new1.d%100))+(new1.a%100);
			}
			
			if(new1.c1==C1)
			{
				new1.sum=new1.in;
			}
			
			if(arr[new1.c1-1][new1.c2-1]==null)
				arr[new1.c1-1][new1.c2-1]=new1;
			else 
			{
				flight ptr=arr[new1.c1-1][new1.c2-1];
				flight prev=ptr;
				while(ptr!=null)
				{
					prev=ptr;
					if(ptr.in>new1.in)
						break;
					ptr=ptr.next;
				}
				if(prev==arr[new1.c1-1][new1.c2-1] && prev.in>new1.in)
				{
					new1.next=arr[new1.c1-1][new1.c2-1];
					arr[new1.c1-1][new1.c2-1]=new1;
				}
				else if(prev.in>new1.in)
				{
					new1.next=prev.next;
					prev.next=new1;
				}
				else
				{
					prev.next=new1;
				}
			}
			Q[++q]=new1;
		}
		
	/*	for(int i=0;i<x;i++)
			for(int j=0;j<x;j++)
			{	
				flight ptr=arr[i][j];
				while(ptr!=null)
					{
					System.out.println(ptr.c1+" "+ptr.c2+" "+ptr.f+" "+ptr.d+" "+ptr.a+" "+ptr.in);
					ptr=ptr.next;
					}
			}*/ 
/*		for(int i=0;i<=q;i++)
		{
			flight ptr=Q[i];
			System.out.println(ptr.c1+" "+ptr.c2+" "+ptr.f+" "+ptr.d+" "+ptr.a+" "+ptr.in+" "+ptr.sum+" "+ptr.p);
		}*/
		int min=0;
		flight mn=null;
		int dist=0;
		int q1=q;
		while(q1!=-1)
		{
			flight u=obj.min(dist);
			if(u==null)
				break;
		//	System.out.println(">"+u.c1+" "+u.c2+" "+u.f+" "+u.d+" "+u.a+" "+u.in+" "+u.sum+" "+u.p);
		//	System.out.println("min "+u.f+" "+u.sum);
			u.v=true;
			dist=u.sum;
			
			if(u.c2==C2)
			{
					if(min==0)
					{
						min=u.sum;
						mn=u;
					}
					else if(min>u.sum)
					{
						min=u.sum;
						mn=u;
					}
					else if(min==u.sum)
					{
						if(mn.a>u.a)
							{
								min=u.sum;
								mn=u;
							}
					}
			}
			
			for(int i=0;i<x;i++)
			{
				int w=0;
				flight nb=arr[u.c2-1][i];
				
				if(nb==null)
					continue;
				
				flight pr=nb;
				while(pr!=null)
				{
					if(u.a/100==pr.d/100)
						w=pr.d-u.a;
					else
					{
						int s=(pr.d/100)-(u.a/100);
						w=(60*(s-1))+(60-(u.a%100))+(pr.d%100);
					}
					
			//		System.out.println(u.f+" "+pr.f+" "+w);
					
					if(pr.sum>(w+pr.in+u.sum) && w>=0)
						{
						//	System.out.println(pr.sum+"*"+u.f+" "+pr.f+" "+w);
							pr.sum=w+pr.in+u.sum;
							pr.p=u;
						}
					pr=pr.next;
				}
			}
			q1--;
		}
		
		if(min==0 || min==999999999)
			System.out.println(-1);
		else 
			{
				obj.display(mn,0);
				System.out.println();
			}
		
		
	/*	for(int i=0;i<x;i++)
			for(int j=0;j<x;j++)
			{	
				flight ptr=arr[i][j];
				while(ptr!=null)
					{
					System.out.println(ptr.c1+" "+ptr.c2+" "+ptr.f+" "+ptr.d+" "+ptr.a+" "+ptr.in+" "+ptr.sum+" "+ptr.p);
					if(ptr.f.equalsIgnoreCase("ULYEP"))
					{
						System.out.println(">>>"+ptr.c1+" "+ptr.c2+" "+ptr.f+" "+ptr.d+" "+ptr.a+" "+ptr.in+" "+ptr.sum+" "+ptr.p);
					}
					if(ptr.p!=null)
						System.out.println(ptr.p.f);
					
					ptr=ptr.next;
					}
			}*/
		/*
		int min=999999999;
		flight mn=null;
		int nc=x+1;
		for(int i=0;i<x;i++)
		{
			if(arr[i][C2-1]!=null)
			{
				flight pr=arr[i][C2-1];
				while(pr!=null)
				{
					if(pr.sum==999999999)
					{
						pr=pr.next;
						continue;
					}
					
					flight ptr=pr;
					int nm=0;
					while(ptr!=null)
					{
						nm++;
						ptr=ptr.p;
					}
					
					if(pr.sum<min)
					{
						min=pr.sum;
						mn=pr;
						nc=nm;
					}
					else if(pr.sum==min)
					{
						if(pr.a<mn.a)
						{
							min=pr.sum;
							mn=pr;
							nc=nm;
						}
					}
					pr=pr.next;
				}
			/*	else if(arr[i][C2-1].sum==min)
				{ System.out.println(arr[i][C2-1].f+"*****************"+mn.f);
					if(nm<nc)
					{
						min=arr[i][C2-1].sum;
						mn=arr[i][C2-1];
						nc=nm;
					}
				}	
			}
		}
		if(min==999999999 || mn==null)
		{
			System.out.println(-1);
		}
		else 
		{
			System.out.println(nc);
			obj.display(mn);
			System.out.println();
		}*/

	}
	public int dpart(flight root)
	{
		if(root.p==null)
			return root.d;
		else
		{
			return dpart(root.p);
		}
	}
	public void display(flight root,int nc)
	{
		if(root.p==null)
		{
			System.out.println(nc+1);
			System.out.print(root.f+" ");
		}
		else
		{
			display(root.p,nc+1);
			System.out.print(root.f+" ");
		}
	}
	
	 public flight min(int dist)
	 {
		 int a=0;
		 while(Q[a].v!=false)
		 {
			 a++;
		 }
		 int m=Q[a].sum;
		 flight mn=Q[a];
		 
		 for(int i=0;i<=q;i++)
		 {
			if(Q[i].sum>=dist && Q[i].sum<=m && Q[i].v==false )
			{
				m=Q[i].sum;
				mn=Q[i];
			}
		 }
		 
		 if(mn.sum!=999999999) 
			 return mn;
		 else
			 return null;
	 }
}

class flight
{
	int c1;
	int c2;
	String f;
	int d;
	int a;
	int in;
	boolean v=false;
	flight next;
	int sum=999999999;
	flight p;
}