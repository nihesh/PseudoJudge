/* package codechef; // don't place package name! */

import java.util.*;
import java.lang.*;
import java.io.*;
import java.text.SimpleDateFormat;  

class extra_link
{
    extra_link next;
    int x;
    extra_link()
    {
        next=null;
        x=-1;
    }
    void insert(int x)
    {
        extra_link temp = new extra_link();
        temp.x=x;
        if(next==null)
        {
            next = temp;
        }
        else
        {
            temp.next = next;
            next=temp;
        }
    }
}
class linkedlist
{
    linkedlist link;
    vertice v;
    linkedlist(vertice v)
    {
        link= null;
        this.v = v;
    }

}
class finale
{
    int src;
    int start;
    int end;
    int dist;
    String parent;
    int p;
    finale (int s)

    {
        src =s;
        dist = end = start = Integer.MAX_VALUE;

    }
}
class vertice
{
    int dest;
    int start;
    int end;
    String name;
    int diff; 
    vertice()
    {
        // aise hi        
    }

    vertice(int a,String c,String d, String e)
    {
        diff = difference(c,d);
        dest = a;
        start=Integer.parseInt(c);
        end =Integer.parseInt(d);
        name=e;
           
    }
    int difference(int a,int b)
    {
        int t1 = a/100;
        int t2= a%100;
        int t3 = b/100;
        int t4= b%100;
        int min = t4-t2;
        int hour = t3-t1;
        //System.out.println((hour)*60 + min);
        return hour*60 + min;
    }
    int difference(String a, String b)
    {
        String[] a1 = a.split("");
        String[] b1 = b.split("");
        int t1 = Integer.parseInt(a1[0]+a1[1]);
        int t2 = Integer.parseInt(a1[2]+a1[3]);
        int t3 = Integer.parseInt(b1[0]+b1[1]);
        int t4 = Integer.parseInt(b1[2]+b1[3]);
        int min = t4-t2;
        int hour = t3-t1;
        //System.out.println((hour)*60 + min);
        return hour*60 + min;
    }  
}

class HEAP
{
    int size;
    finale[] heap;
    int current ;
    HEAP(int s)
    {
        current =0;
        size = s;
        heap =  new finale[s];
    }
    public Boolean empty()
    {
        return current ==0;
    }
    public void insert(finale temp)
    {
        heap[current]=temp;
        percup(current);
        current++;
    }
    public void percup(int i)
    {    
        finale temp = heap[i];
        while(i>0 && heap[(i-1)/2].dist > heap[i].dist)
        {
            heap[i]=heap[(i-1)/2]; 
            i=(i-1)/2;
        }
        heap[i]=temp;
    }

    public void perculate_down(int level)
    {
        int minnode=level;
        int leftchild = 2*level+1;
        int rightchild = 2*level+2;
        if(leftchild < current && heap[minnode].dist> heap[leftchild].dist)
            minnode=leftchild;
        
        if(rightchild < current && heap[minnode].dist > heap[rightchild].dist)
            minnode=rightchild;
        
        if(minnode!=level)
        {
            finale t = heap[minnode];
            heap[minnode] = heap[level];
            heap[level]=t;
            perculate_down(minnode);
        }   
    }
    public finale extract()
    {
        finale temp = heap[0];
        heap[0]= heap[current-1];
        current--;
        perculate_down(0);
        return temp;
    }
}

class graph
{
    linkedlist[] g;
    finale[] array;
    int vert;
    graph(int v)
    {
        g = new linkedlist[v];
        array = new finale[v];
        vert= v;
    }
    public void print()
    {
        System.out.println("Printing");
        for(int i =0;i < vert;i++)
        {
            linkedlist temp = g[i];
            while (temp!=null)
            {
                System.out.print(temp.v.dest);
                temp=temp.link;
            }

            System.out.println(" "+i);
        }
    }
    public void insert (int src,vertice dest)
    {
        linkedlist temp = new linkedlist(dest);
        if (g[src-1]==null)
        {
            g[src-1] = temp;
        }
        else
        {
            temp.link = g[src-1];
            g[src-1] = temp;
        }
    }
    
    public void work_here(int src,int dest,int start,int end)
    {
        finale[] time  = new finale[vert];
        int[] visited = new int[vert];
        for(int i =0;i<vert;i++)
        {
            time[i] = new finale(i);
            visited[i]=0;
        }
 //       System.out.print(vert);
   
        vertice cal = new vertice();
        time[src-1].dist =0;    
        time[src-1].end = start;
       time[src-1].parent="a";
        HEAP h = new HEAP(vert);
        h.insert(time[src-1]);
        int first =0;
        while (h.empty()!=true)
        {
            int[] inlist = new int[vert];
            finale temp = h.extract();
            visited[temp.src]=1;
            int dij = temp.src;
         //  System.out.println("Working on "+ (dij+1));
          // System.out.println("TIme is "+ temp.end);
            linkedlist temp1 = g[dij];
            extra_link temp2 = new extra_link();
            while(temp1!=null)
            {
                // /System.out.println(temp1.v.dest);
                if ((temp.end <= temp1.v.start) && visited[temp1.v.dest]==0)
                {
            //        System.out.println("Adjacent with time and node" + (temp1.v.end));
              //      System.out.println(temp1.v.dest+1);
                    int total_time = cal.difference(temp.end,temp1.v.end);
                    if (time[temp1.v.dest].dist > total_time + temp.dist )
                    {
                        time[temp1.v.dest].dist=total_time+temp.dist;
                        time[temp1.v.dest].end = temp1.v.end;
                //        System.out.println("udating edn " + temp1.v.end);
                       time[temp1.v.dest].parent = temp1.v.name;
                            time[temp1.v.dest].p = dij;
                        if(inlist[temp1.v.dest]==0)
                        {
                            inlist[temp1.v.dest]=1;
                            temp2.insert(temp1.v.dest);
                        }
                    }
                }
                temp1 = temp1.link;
            }

            while (temp2!=null)
            {
                if (temp2.x == -1)
                {
                     temp2=temp2.next;
                     continue;
                }
                  //  System.out.println("inserting heap with node ad time "+ (temp2.x+1));
                //System.out.println(time[temp2.x].dist);
                               h.insert(time[temp2.x]);
                temp2=temp2.next;
            }
        
        }
       // for (int i =0;i<vert;i++)
        //{
         //   System.out.println(time[i].dist);
          ///  System.out.println(time[i].end);
        //}
        int i = dest-1;
        //System.out.println("LOLOL "+time[i].end);
        //System.out.println("LOLOL"+end);
      
        if (time[i].end <= end)
        { 
            int k=0;
            String[] data = new String[vert];
            while (time[time[i].p].parent !="a")
            {
                data[k++] = time[i].parent;
                i = time[i].p;
            } 
            System.out.println(k+1);
            k=k-1;
             System.out.print(time[i].parent+" ");
            while (k>=0)
            {
                System.out.print(data[k]+" ");
                k--;
            }


            //System.out.println(time[i].dist);
        }
        if(time[i].end ==Integer.MAX_VALUE || time[i].end > end)
        {
                System.out.println("-1");
        }
    }


}

public class The_final_task
{
	public static void main(String args[]) throws IOException
	{
		BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
		int vert = Integer.parseInt(read.readLine());
        graph GRAPH = new graph(vert);
		String run = read.readLine();
        int edge = Integer.parseInt(read.readLine());
        while (edge!=0)
        {
         //   System.out.println("inserting");
            String[] temp = read.readLine().split(" ");
            vertice temp1 = new vertice(Integer.parseInt(temp[1])-1,temp[3],temp[4],temp[2]);
            GRAPH.insert(Integer.parseInt(temp[0]),temp1);
            edge--;
        }
        //GRAPH.print();
        String[] temp = run.split(" ");
        int src = Integer.parseInt(temp[0]);
        int dest = Integer.parseInt(temp[1]);
        int start = Integer.parseInt(temp[2]);
        int end = Integer.parseInt(temp[3]);
        GRAPH.work_here(src,dest,start,end);

	}
}