import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

class InReader {
    static BufferedReader reader;
    static StringTokenizer tokenizer;

    //    Initializing reader for input stream
    static void init(InputStream input) {
        reader = new BufferedReader(new InputStreamReader(input));
        tokenizer = new StringTokenizer("");
    }

    //    to get next word
    static String next() throws IOException {
        while (!tokenizer.hasMoreTokens()) {
            tokenizer = new StringTokenizer(reader.readLine());
        }
        return tokenizer.nextToken();
    }

    static int nextInt() throws IOException {
        return Integer.parseInt(next());
    }
    static double nextDouble() throws IOException {
        return Double.parseDouble(next());
    }
    static long nextLong() throws IOException {
        return Long.parseLong(next());
    }
}

public class itAintOverTillItsOver {
    static HashMap<Integer,HashSet<Integer>> startsAtCity = new HashMap<>();
    static HashMap<Integer,HashSet<Integer>> endsAtCity = new HashMap<>();
    static flightNode[] flightDetail;
    static graphNode[] graphList;
    public static void main(String[] args) throws IOException{
        InReader.init(System.in);
        int cities = InReader.nextInt();
        final int source = InReader.nextInt();
        final int dest = InReader.nextInt();
        int startTime = InReader.nextInt();
        int stHoursInMins = (startTime/100)*60;
        int stMins = startTime%100;
        startTime = stHoursInMins+stMins;
        // System.out.println(startTime);
        int endTime = InReader.nextInt();
        int endHoursInMins = (endTime/100)*60;
        int endMins = endTime%100;
        endTime = endHoursInMins+endMins;
        // System.out.println(endTime);
        int flights = InReader.nextInt();
        flightDetail = new flightNode[Math.max(Math.max(source,dest),flights+2)];
        int flightID = 0;
        while (flights>0){
            if (flightID == source || flightID == dest) {
                flightID++;
            }
            int c1 = InReader.nextInt();
            int c2 = InReader.nextInt();
            String flightName = InReader.next();
            int t1 = InReader.nextInt();
            int t1HoursInMins = (t1/100)*60;
            int t1Mins = t1%100;
            t1 = t1HoursInMins+t1Mins;
            int t2 = InReader.nextInt();
            int t2HoursInMins = (t2/100)*60;
            int t2Mins = t2%100;
            t2 = t2HoursInMins+t2Mins;
            flightDetail[flightID] = new flightNode(c1,c2,flightName,t1,t2);
            // System.out.println("c1 is "+c1+" and c2 is "+c2);
            if (c2 != source && c1!=dest) {
                if (startsAtCity==null || !startsAtCity.containsKey(c1)) {
                    HashSet<Integer> c1set = new HashSet<>();
                    c1set.add(flightID);
                    startsAtCity.put(c1,c1set);
                }
                else {
                    startsAtCity.get(c1).add(flightID);
                }
                if (endsAtCity==null || !endsAtCity.containsKey(c2)) {
                    HashSet<Integer> c2set = new HashSet<>();
                    c2set.add(flightID);
                    endsAtCity.put(c2,c2set);
                }
                else {
                    endsAtCity.get(c2).add(flightID);
                }
                flightID++;
            }
            flights--;
        }
        // for (int i=0;i<flightDetail.length;i++) {
        //     if (flightDetail[i] != null) {
        //         System.out.println("Flight ID is "+i);
        //         flightDetail[i].printNode();
        //     }
        // }
        graphList = new graphNode[flightID+2];
        for (int i = 0; i <=cities; i++) {
            // System.out.println("i is "+i);
            if (startsAtCity.get(i)!=null && endsAtCity.get(i)!=null) {
                for (int flightThatEndsHere:endsAtCity.get(i)) {
                    for (int flightThatStartsHere:startsAtCity.get(i)) {
                        // System.out.println("The flights are "+flightDetail[flightThatEndsHere].src+" "+flightDetail[flightThatEndsHere].dest+" "
                        //         +flightDetail[flightThatEndsHere].name+" and "+flightDetail[flightThatStartsHere].src+" "+flightDetail[flightThatStartsHere].dest+" "
                        //         +flightDetail[flightThatStartsHere].name);
                        int waitingTime;
                        waitingTime = flightDetail[flightThatStartsHere].start-flightDetail[flightThatEndsHere].end;
                        // System.out.println(waitingTime);
                        if (waitingTime>=0) {
                            if (flightThatEndsHere == source) {
                                waitingTime = 0;
                            }
                            else if (flightThatStartsHere == dest) {
                                waitingTime = 0;
                            }
                            graphNode nextFlightNode = new graphNode(flightThatStartsHere,waitingTime);
                            if (graphList[flightThatEndsHere] == null) {
                                graphList[flightThatEndsHere] = nextFlightNode;
                            }
                            else {
                                graphList[flightThatEndsHere] = graphNode.addConnectedFlight(graphList[flightThatEndsHere], nextFlightNode);
                            }
                            // System.out.println("There is edge between "+flightDetail[flightThatEndsHere].src+" "+flightDetail[flightThatEndsHere].dest+" "
                            //         +flightDetail[flightThatEndsHere].name+" and "+flightDetail[flightThatStartsHere].src+" "+flightDetail[flightThatStartsHere].dest+" "
                            //         +flightDetail[flightThatStartsHere].name+" with waiting time "+waitingTime);
                        }
                    }
                }
            }
            else if (startsAtCity.get(i)==null && endsAtCity.get(i)!=null) {
                // System.out.println("No flight starts at city number "+i);
                for (int flightThatEndsHere:endsAtCity.get(i)) {
                    int waitingTime;
                    if (i == dest) {
                        if (flightDetail[i] == null) {
                            flightDetail[i] = new flightNode(i,i,"DEST",endTime,0);
                        }
                        waitingTime = flightDetail[i].start-flightDetail[flightThatEndsHere].end;
                        if (waitingTime>=0) {
                            waitingTime = 0;
                            graphNode nextFlightNode = new graphNode(i,waitingTime);
//                            System.out.println(flightThatEndsHere==i);
                            if (graphList[flightThatEndsHere] == null) {
                                graphList[flightThatEndsHere] = nextFlightNode;
                            }
                            else {
                                graphList[flightThatEndsHere] = graphNode.addConnectedFlight(graphList[flightThatEndsHere], nextFlightNode);
                            }
                            // System.out.println("There is edge between "+flightDetail[flightThatEndsHere].src+" "+flightDetail[flightThatEndsHere].dest+" "
                            //         +flightDetail[flightThatEndsHere].name+" and "+flightDetail[i].src+" "+flightDetail[i].dest+" "
                            //         +flightDetail[i].name+" with waiting time "+waitingTime);
                        }
                    }
                }
            }
            else if (startsAtCity.get(i)!=null && endsAtCity.get(i)==null) {
                // System.out.println("No flight ends at city number "+i);
                for (int flightThatStartsHere:startsAtCity.get(i)) {
                    int waitingTime;
                    if (i == source) {
                        if (flightDetail[i] == null) {
                            flightDetail[i] = new flightNode(i,i,"START",0,startTime);
                        }
                        waitingTime = flightDetail[flightThatStartsHere].start-flightDetail[i].end;
                        if (waitingTime>=0) {
                            waitingTime = 0;
                            graphNode nextFlightNode = new graphNode(flightThatStartsHere,waitingTime);
                            if (graphList[i] == null) {
                                graphList[i] = nextFlightNode;
                            }
                            else {
                                graphList[i]=graphNode.addConnectedFlight(graphList[i],nextFlightNode);
                            }
                            // System.out.println("There is edge between "+flightDetail[i].src+" "+flightDetail[i].dest+" "
                            //         +flightDetail[i].name+" and "+flightDetail[flightThatStartsHere].src+" "+flightDetail[flightThatStartsHere].dest+" "
                            //         +flightDetail[flightThatStartsHere].name+" with waiting time "+waitingTime);

                        }
                    }
                }
            }
            else if (startsAtCity.get(i)==null && endsAtCity.get(i)==null) {
                // System.out.println("No flight starts or ends at city number "+i);
            }
        }
        // for (int i=0;i<flightDetail.length;i++) {
        //     if (flightDetail[i] != null) {
        //         System.out.println("Flight ID is "+i);
        //         flightDetail[i].printNode();
        //     }
        // }
        // System.out.println("Printing the graph: ");
        // for (int i = 0; i <graphList.length ; i++) {
        //     System.out.println("Flight number: "+i+" :");
        //     if (graphList[i] != null) {
        //         graphList[i].printNode();
        //     }
        // }
        MinHeap myQueue = new MinHeap(graphList.length,source);
        while (!myQueue.isEmpty()){
//            System.out.println("Queue before updation: ");
//            for(Map.Entry m: myQueue.Heap.entrySet()){
//                System.out.println(m.getKey()+" "+m.getValue());
//            }
            // System.out.println("Printing the damn heap");
            // for (int i = 0; i <myQueue.presentSize ; i++) {
            //     System.out.println("Flight is : "+myQueue.Heap[i].flightID+" and it's distance from source is "+myQueue.Heap[i].distanceOfFlight);
            // }
            int minDistanceFlightID = myQueue.extractMin();
//            System.out.println("Updated queue: ");
//            for(Map.Entry m: myQueue.Heap.entrySet()){
//                System.out.println(m.getKey()+" "+m.getValue());
//            }
//            if (minDistanceFlightID == dest) {
//                break;
//            }
            // System.out.println("Yo yo Min is "+minDistanceFlightID);
            graphNode connectedFlight = graphList[minDistanceFlightID];
//            System.out.println(connectedFlight==null);
            while (connectedFlight!=null){
                int presentFlightID = connectedFlight.flightID;
                // System.out.println("presentFlightID is "+presentFlightID);
                if (myQueue.isThisIDInMinHeap(presentFlightID) && myQueue.indexOfFlightInHeap[presentFlightID]!=-1) {
                    int newDistance = connectedFlight.edgeWait + flightDetail[connectedFlight.flightID].duration;
                    // System.out.println("Duration is "+flightDetail[connectedFlight.flightID].duration);
                    myQueue.updateVertexDistance(presentFlightID,newDistance,minDistanceFlightID);
                    // System.out.println(minDistanceFlightID+" is the new parent.");
                }
                connectedFlight = connectedFlight.nextConnectedFlight;
            }
        }
        // myQueue.printParentsList();
        myQueue.printPathFromThisFlightToSource(dest,0);

    }
    static class flightNode{
        int src;
        int dest;
        String name;
        int start;
        int end;
        int duration;
        flightNode(int src, int dest, String name, int start, int end){
            this.src = src;
            this.dest = dest;
            this.name = name;
            this.start = start;
            this.end = end;
            this.duration = end-start;
        }
        void printNode(){
            System.out.println(src+" "+dest+" "+name+" "+start+" "+end+" .");
        }
    }
    static class graphNode{
        int flightID;
        int edgeWait;
        int duration;
        flightNode presentFlight;
        graphNode nextConnectedFlight;
        graphNode(int flightID, int edgeWait){
            this.flightID = flightID;
            presentFlight = flightDetail[flightID];
            this.edgeWait = edgeWait;
            this.duration = this.presentFlight.duration;
        }
        static graphNode addConnectedFlight(graphNode here,graphNode thisFlight){
            if (here == null) {
                return thisFlight;
            }
            else if (here == here.nextConnectedFlight) {
                // System.out.println("Stack overflow here");
                return null;
            }
            else {
                graphNode tmp = here.nextConnectedFlight;
                here.nextConnectedFlight = thisFlight;
                thisFlight.nextConnectedFlight = addConnectedFlight(thisFlight.nextConnectedFlight,tmp);
                return here;
            }
        }
        void printNode(){
            System.out.println(flightID+" "+flightDetail[flightID].src+" "+flightDetail[flightID].dest+" "+flightDetail[flightID].name+" "
                    +flightDetail[flightID].start+" "+flightDetail[flightID].end+" .");
            if (nextConnectedFlight != null && this!=nextConnectedFlight) {
                System.out.println("And the next connected flight is: ");
                nextConnectedFlight.printNode();
            }
        }
    }
    static class MinHeap{
        class node{
            int distanceOfFlight;
            int flightID;
            node(int flightID, int distanceOfFlight){
                this.flightID = flightID;
                this.distanceOfFlight = distanceOfFlight;
            }
        }
        static int[] indexOfFlightInHeap;
        static node[] Heap;
        int presentSize;
        int capacity;
        int source;
        static int[] parentOfFlightWithID;
        static HashSet<Integer> includedInSPT;
        MinHeap(int capacity, int sourceFlightID){
            this.capacity = capacity;
            presentSize = capacity;
//            this.presentSize = capacity;
            this.source = sourceFlightID;
            Heap = new node[this.capacity];
            indexOfFlightInHeap = new int[this.capacity+2];
            Heap[0]=new node(source,0);
            for (int i = 0; i < this.capacity; i++) {
                indexOfFlightInHeap[i]=-1;
            }
            indexOfFlightInHeap[source] = 0;
            parentOfFlightWithID = new int[this.capacity];
            for (int i = 0; i <this.capacity; i++) {
                parentOfFlightWithID[i] = -1;
            }
            int flight=0;
            for (int i = 1; i <this.capacity ; i++) {
                if (flight == source) {
                    flight++;
                }
                Heap[i] = new node(flight,Integer.MAX_VALUE);
                indexOfFlightInHeap[flight] = i;
                flight++;
            }
            includedInSPT = new HashSet<>(this.capacity);
        }
        boolean isEmpty(){
            return presentSize==0;
        }
        int extractMin(){
//            int min = Heap[0];
//
//            System.out.println(min+" has the least distance of "+ Heap.get(min));
//            includedInSPT.add(min);
////            capacity = Heap.capacity()-1;
//            Heap.remove(min);
//            return min;
            node last = Heap[presentSize-1];
            node min = Heap[0];
            includedInSPT.add(min.flightID);
            Heap[0] = last;
            indexOfFlightInHeap[min.flightID]=-1;
            indexOfFlightInHeap[last.flightID]=0;
            Heap[presentSize-1] = null;
            presentSize--;
            heapify(0, presentSize-1);
            return min.flightID;
        }
        public static void heapify(int i,int n) {
            if (!(i == 0 && n == 0) && i <= Math.floor((n-1)/2)) {
                int min = i;
//            System.out.println("i is : "+i+" and n is "+n+". ");
//            System.out.println("Is pqueue[i] null ? ; "+(pQueue[i]==null));
//            System.out.println("Is pqueue[2*i+1] null ? ; "+(pQueue[2*i + 1]==null));
                if (Heap[i].distanceOfFlight>Heap[2*i + 1].distanceOfFlight) {
//                    System.out.println("Distance of flight "+Heap[i].flightID+" -> "+Heap[i].distanceOfFlight+" is greater than distance" +
//                            "of flight"+Heap[2*i + 1].flightID+" -> "+Heap[2*i + 1].distanceOfFlight);
                    min = 2*i + 1;
                }
                if ((2*i + 2 <= n) && Heap[min].distanceOfFlight>Heap[2*i + 2].distanceOfFlight) {
//                    System.out.println("Distance of flight "+Heap[min].flightID+" -> "+Heap[min].distanceOfFlight+" is greater than distance" +
//                            "of flight"+Heap[2*i + 2].flightID+" -> "+Heap[2*i + 2].distanceOfFlight);
                    min = 2*i + 2;
                }
                if (min != i) {
//                    System.out.println("i is"+i+" with flight no."+Heap[i].flightID+" and min is"+min+" with flight no. "+Heap[min].flightID);
                    node tmp = Heap[i];
                    Heap[i] = Heap[min];
                    indexOfFlightInHeap[Heap[min].flightID] = i;
                    Heap[min] = tmp;
                    indexOfFlightInHeap[tmp.flightID] = min;
//                    System.out.println("After swap, i is"+i+" with flight no."+Heap[indexOfFlightInHeap[i]].flightID+" and min is"+min+" with flight no. "+Heap[indexOfFlightInHeap[min]].flightID);
                    heapify(min,n);
                }
            }
        }
        void printParentsList(){
            for (int i = 0; i <this.capacity; i++) {
                System.out.println("Parent flight of "+i+" is "+parentOfFlightWithID[i]);
            }
        }
        void updateVertexDistance(int vertex,int newDistance,int newParent){
            int oldDistance = Heap[indexOfFlightInHeap[vertex]].distanceOfFlight;
            if (oldDistance > newDistance) {
                Heap[indexOfFlightInHeap[vertex]].distanceOfFlight = newDistance;
                parentOfFlightWithID[vertex] = newParent;
                // System.out.println("Parent of "+vertex+" has been changed to "+parentOfFlightWithID[vertex]);
            }
//            for (int i = (int)Math.floor((presentSize-1)/2); i >=0 ; i--) {
//                heapify(i,presentSize-1);
//            }
            // System.out.println("Printing the damn heap first time");
            // for (int i = 0; i <presentSize ; i++) {
            //     System.out.println("Flight no "+i+" is : "+Heap[i].flightID+" and it's distance from source is "+Heap[i].distanceOfFlight);
            // }
            int index = indexOfFlightInHeap[vertex];
            while(index>=0){
                // System.out.println("Heapifying at"+index);
                heapify(index,presentSize-1);
                if (index == 0) {
                    break;
                }
                index=(index-1)/2;
            }
            // System.out.println("Printing the damn heap second time");
            // for (int i = 0; i <presentSize ; i++) {
            //     System.out.println("Flight no."+i+" is : "+Heap[i].flightID+" and it's distance from source is "+Heap[i].distanceOfFlight);
            // }
        }
        boolean isThisIDInMinHeap(int flightID){
            return !includedInSPT.contains(flightID);
        }
        boolean printPathFromThisFlightToSource(int flight,int flightsTillNow){
            if (parentOfFlightWithID[flight] == -1) {
                if (flight != source) {
                    // System.out.println("Flight - "+flight+" is not equal to source: "+source);
                    System.out.println(-1);
                    return false;
                }
                else {
                    System.out.println(flightsTillNow-1);
                    return true;
                }
            }
            else {
                boolean reachedSource = printPathFromThisFlightToSource(parentOfFlightWithID[flight],flightsTillNow+1);
                if (reachedSource && !flightDetail[flight].name.equals("DEST")) {
                    System.out.print(flightDetail[flight].name+" ");
                }
                return reachedSource;
            }
        }
    }
}
