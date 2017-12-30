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
//    static HashMap
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
        boolean flightFromSourceExists = false;
        boolean flightFromDestExists = false;
        while (flights>0){
            while (flightID == source || flightID == dest) {
                flightID++;
            }
//            System.out.println("FlightID is "+flightID);
            int c1 = InReader.nextInt();
            if (c1==source) {
                flightFromSourceExists=true;
            }
            int c2 = InReader.nextInt();
            if (c2 == dest) {
                flightFromDestExists = true;
            }
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
//            System.out.println(startsAtCity==null);
            if (c2 != source && c1!=dest) {
                // System.out.println("source is "+source+" and c2 is "+c2);
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
        if (!flightFromDestExists || !flightFromSourceExists) {
            System.out.println(-1);
        }
        else{
            // for (int i=0;i<flightDetail.length;i++) {
            //     if (flightDetail[i] != null) {
            //         System.out.println("Flight ID is "+i);
            //         flightDetail[i].printNode();
            //     }
            // }
//        flightDetail[source] = new flightNode(source,source,"START",0,startTime);
//        flightDetail[dest] = new flightNode(dest,dest,"DEST",endTime,0);
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
//                                flightDetail[source] = new flightNode(source,source,"START",0,startTime);
                                }
                                else if (flightThatStartsHere == dest) {
//                                flightDetail[dest] = new flightNode(dest,dest,"DEST",endTime,0);
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
//                        if (flightDetail[i] == null) {
//                            flightDetail[i] = new flightNode(i,i,"DEST",endTime,0);
//                        }
                            flightDetail[i] = new flightNode(i,i,"DEST",endTime,0);
                            flightDetail[i].duration=0;
//                        else {
//                            System.out.println("Dest place is already occupied by the following flight: ");
//                            flightDetail[i].printNode();
//                        }
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
//                for (int key:startsAtCity.keySet()) {
//                    System.out.println("a key is "+key);
//                }
                    for (int flightThatStartsHere:startsAtCity.get(i)) {
                        int waitingTime;
                        if (i == source) {
                            // System.out.println("i is source . flightDetails[source]==null is "+(flightDetail[i] == null));
//                        if (flightDetail[i] == null) {
//                            flightDetail[i] = new flightNode(i,i,"START",0,startTime);
//                        }
//                        else{
//                            System.out.println(flightDetail[i].src+" "+flightDetail[i].dest+" "+flightDetail[i].name);
//                        }
                            flightDetail[i] = new flightNode(i,i,"START",0,startTime);
                            flightDetail[i].duration=0;
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
            int iMax=0;
            for (int i=0;i<flightDetail.length;i++) {
                if (flightDetail[i] != null) {
                    // System.out.println("Flight ID is "+i);
                    // flightDetail[i].printNode();
                    iMax = Math.max(iMax,i);
                }
                else {
                    // System.out.println(i+" position is null");
                }
            }
            // System.out.println("Flight Details length is: "+flightDetail.length);
            // System.out.println("Printing the graph: ");
            for (int i = 0; i <graphList.length ; i++) {
                if (graphList[i] != null) {
//                System.out.println(i+" is less than 10");
//                if (i<=iMax) {
                    // System.out.println("Flight number: "+i+" :");
                    // graphList[i].printNode();
//                }
//                else {
//                    graphList[i]=null;
//                }
                    if (flightDetail[i] == null) {
                        // System.out.println("\nNull boi\n");
                    }
                }
            }
            MinHeap myQueue = new MinHeap(graphList.length,source,dest);
            while (!myQueue.isEmpty()){
//            System.out.println("Queue before updation: ");
//            for(Map.Entry m: myQueue.Heap.entrySet()){
//                System.out.println(m.getKey()+" "+m.getValue());
//            }
                // System.out.println("Printing the damn heap");
                int newHeapSize=0;
                for (int i = 0; i <myQueue.presentSize ; i++) {
                    if (myQueue.Heap[i].flightID<=iMax) {
                        // System.out.println("Flight is : "+myQueue.Heap[i].flightID+" and it's distance from source is "+myQueue.Heap[i].distanceOfFlight);
                        newHeapSize = i;
                        // System.out.println("Heap size is "+myQueue.presentSize);
                    }
                    else {
                        myQueue.presentSize =newHeapSize+1  ;
                        // System.out.println("Heap size is "+myQueue.presentSize);
                        myQueue.Heap[i]=null;
                    }
                }
                int minDistanceFlightID = myQueue.extractMin();
//            System.out.println("Updated queue: ");
//            for(Map.Entry m: myQueue.Heap.entrySet()){
//                System.out.println(m.getKey()+" "+m.getValue());
//            }
//            if (minDistanceFlightID == dest) {
//                break;
//            }
                // System.out.println("Yo yo Min is "+minDistanceFlightID);
//            if (myQueue.Heap[minDistanceFlightID].distanceOfFlight == Integer.MAX_VALUE) {
//                break;
//            }
                graphNode connectedFlight = graphList[minDistanceFlightID];
//            System.out.println(connectedFlight==null);
                while (connectedFlight!=null){
                    int presentFlightID = connectedFlight.flightID;
                    // System.out.println("presentFlightID is "+presentFlightID);
                    if (myQueue.isThisIDInMinHeap(presentFlightID) && myQueue.indexOfFlightInHeap[presentFlightID]!=-1) {
                        int newDistance = connectedFlight.edgeWait + flightDetail[connectedFlight.flightID].duration;
                        if (connectedFlight.flightID==dest) {
                            // System.out.println("My new distance is "+newDistance+" because edgeWait is "+connectedFlight.edgeWait+" and duration is "+
                                    // flightDetail[connectedFlight.flightID].duration);
                        }
                        // System.out.println("Duration is "+flightDetail[connectedFlight.flightID].duration);
                        myQueue.updateVertexDistance(presentFlightID,newDistance,minDistanceFlightID);
                        // System.out.println(minDistanceFlightID+" is the new parent.");
                    }
                    connectedFlight = connectedFlight.nextConnectedFlight;
                }
            }
//            myQueue.printParentsList();
            // for (int i = 0; i < myQueue.NumOfFlightsTillHere.length; i++) {
            //     System.out.println("There are "+myQueue.numOfFlightsTillHere(i)+" flights till flight number "+i+".");
            // }
            myQueue.printPathFromThisFlightToSource(dest,0);
        }

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
        int dest;
        static int[] parentOfFlightWithID;
        static int[] NumOfFlightsTillHere;
        static int[] startTimeOfThisTrack;
        static boolean[] NumOfFlightsCalculated;
        static boolean[] startTimeOfThisTrackCalculated;
        static HashSet<Integer> includedInSPT;
        MinHeap(int capacity, int sourceFlightID, int destFlightID){
            this.capacity = capacity;
            presentSize = capacity;
//            this.presentSize = capacity;
            this.source = sourceFlightID;
            this.dest = destFlightID;
            Heap = new node[this.capacity];
            indexOfFlightInHeap = new int[this.capacity+2];
            NumOfFlightsTillHere = new int[this.capacity+2];
            startTimeOfThisTrack = new int[this.capacity+2];
            NumOfFlightsCalculated = new boolean[this.capacity+2];
            startTimeOfThisTrackCalculated = new boolean[this.capacity+2];
            Heap[0]=new node(source,0);
            for (int i = 0; i < indexOfFlightInHeap.length; i++) {
                indexOfFlightInHeap[i]=-1;
            }
            indexOfFlightInHeap[source] = 0;
            parentOfFlightWithID = new int[this.capacity+2];
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
                if (Heap[i].distanceOfFlight>=Heap[2*i + 1].distanceOfFlight) {
                    // System.out.println("In heap first IF");
                    // System.out.println("Flight no "+i+" is : "+Heap[i].flightID+" and it's distance from source is "+Heap[i].distanceOfFlight);
                    // System.out.println("Flight no "+((2*i)+1)+" is : "+Heap[(2*i)+1].flightID+" and it's distance from source is "+Heap[(2*i)+1].distanceOfFlight);
                    // System.out.println("Heap first IF done");
// System.out.println("Distance of flight "+Heap[i].flightID+" -> "+Heap[i].distanceOfFlight+" is greater than distance" +
//                            "of flight"+Heap[2*i + 1].flightID+" -> "+Heap[2*i + 1].distanceOfFlight);
                    if (Heap[i].distanceOfFlight==Heap[2*i + 1].distanceOfFlight) {
//                         System.out.println("It is SAME!");
// //                        System.out.println("Flight number "+Heap[i].flightID+" has start time "+startTimeOfThisTrack(Heap[i].flightID)+"" +
// //                                " and flight no. "+Heap[2*i + 1].flightID+" has start time "+startTimeOfThisTrack(Heap[2*i + 1].flightID));
// //                        if (startTimeOfThisTrackCalculated[Heap[i].flightID] && startTimeOfThisTrackCalculated[Heap[2*i + 1].flightID]) {
//                         System.out.println("Flight number "+Heap[i].flightID+" has start time "+startTimeOfThisTrack(Heap[i].flightID)+"" +
//                                 " and flight no. "+Heap[2*i + 1].flightID+" has start time "+startTimeOfThisTrack(Heap[2*i + 1].flightID));
                        if ((startTimeOfThisTrack(Heap[i].flightID) < startTimeOfThisTrack(Heap[2*i + 1].flightID))){
                            min = i;
                        }
                        else {
                            min = 2*i + 1;
                        }
                    }
                    else {
                        min = 2 * i + 1;
                    }
                }
                if ((2*i + 2 <= n) && Heap[min].distanceOfFlight>=Heap[2*i + 2].distanceOfFlight) {
//                    System.out.println("Distance of flight "+Heap[min].flightID+" -> "+Heap[min].distanceOfFlight+" is greater than distance" +
//                            "of flight"+Heap[2*i + 2].flightID+" -> "+Heap[2*i + 2].distanceOfFlight);
                    if (Heap[i].distanceOfFlight==Heap[2*i + 2].distanceOfFlight) {
//                        if (startTimeOfThisTrackCalculated[Heap[i].flightID] && startTimeOfThisTrackCalculated[Heap[2*i + 2].flightID]) {
                        if ((startTimeOfThisTrack(Heap[i].flightID) <= startTimeOfThisTrack(Heap[2*i + 2].flightID))){
                            min = i;
                        }
                        else {
                            min = 2*i + 2;
                        }
//                        }
                    }
                    else {
                        min = 2 * i + 2;
                    }
                }
                if (min != i) {
                    // System.out.println("Stuff has changed bro!");
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
        static int startTimeOfThisTrack(int flightID){
            int myParent = parentOfFlightWithID[flightID];
            if (myParent==-1 || flightDetail[myParent].name.equals("START")) {
                return flightDetail[flightID].start;
            }
            else {
                if (startTimeOfThisTrackCalculated[myParent]) {
                    startTimeOfThisTrack[flightID] = startTimeOfThisTrack[myParent];
                    startTimeOfThisTrackCalculated[flightID] = true;
                    return startTimeOfThisTrack[flightID];
                }
                else {
                    startTimeOfThisTrack[flightID] = startTimeOfThisTrack(myParent);
                    startTimeOfThisTrackCalculated[flightID] = true;
                    return startTimeOfThisTrack[flightID];
                }
            }
        }
        int numOfFlightsTillHere(int flightID){
            if (NumOfFlightsCalculated[flightID]) {
                return NumOfFlightsTillHere[flightID];
            }
            int myParent = parentOfFlightWithID[flightID];
            if (myParent == -1) {
                return 0;
            }
            else{
                if (NumOfFlightsCalculated[myParent]) {
                    NumOfFlightsTillHere[flightID]=NumOfFlightsTillHere[myParent]+1;
                    return NumOfFlightsTillHere[flightID];
                }
                else {
                    NumOfFlightsTillHere[myParent]= numOfFlightsTillHere(myParent);
                    NumOfFlightsCalculated[myParent]=true;
                    NumOfFlightsCalculated[flightID]=true;
                    int addValue=1;
                    if (flightDetail[flightID].name == "DEST") {
                        addValue=0;
                    }
                    NumOfFlightsTillHere[flightID]=NumOfFlightsTillHere[myParent]+addValue;
                    return NumOfFlightsTillHere[flightID];
                }
            }
        }
        boolean updateVertexDistance(int vertex,int newDistance,int newParent){
            boolean parentChanged = false;
            if (indexOfFlightInHeap[vertex] >=0 && Heap[indexOfFlightInHeap[vertex]]!=null) {
                // System.out.println("Index of "+vertex+" is "+indexOfFlightInHeap[vertex]);
//                System.out.println("Heap thingie is null is : "+(Heap[indexOfFlightInHeap[vertex]]==null));
                int oldDistance = Heap[indexOfFlightInHeap[vertex]].distanceOfFlight;
                if (oldDistance > newDistance) {
                    // System.out.println("Old distance : "+oldDistance+" , new distance : "+newDistance);
                    Heap[indexOfFlightInHeap[vertex]].distanceOfFlight = newDistance;
                    parentOfFlightWithID[vertex] = newParent;
                    // System.out.println("Parent of "+vertex+" has been changed to "+parentOfFlightWithID[vertex]);
                    parentChanged = true;
                }
                else if (oldDistance == newDistance) {
                    if (startTimeOfThisTrack(parentOfFlightWithID[vertex])> startTimeOfThisTrack(newParent)) {
//                        Heap[indexOfFlightInHeap[vertex]].distanceOfFlight = newDistance;
                        parentOfFlightWithID[vertex] = newParent;
                        parentChanged = true;
                    }
                    else if (startTimeOfThisTrack(parentOfFlightWithID[vertex])== startTimeOfThisTrack(newParent) && numOfFlightsTillHere(parentOfFlightWithID[vertex])>= numOfFlightsTillHere(newParent)) {
                        parentOfFlightWithID[vertex] = newParent;
                        parentChanged = true;
                    }
                }
//            for (int i = (int)Math.floor((presentSize-1)/2); i >=0 ; i--) {
//                heapify(i,presentSize-1);
//            }
                int index = indexOfFlightInHeap[vertex];
                while(index>=0){
                    // System.out.println("Heapifying at "+index+" with size "+presentSize);
                    heapify(index,presentSize-1);
                    if (index == 0) {
                        break;
                    }
                    index=(index-1)/2;
                }
            }
            return parentChanged;
        }
        boolean isThisIDInMinHeap(int flightID){
            return !includedInSPT.contains(flightID);
        }
        boolean printPathFromThisFlightToSource(int flight,int flightsTillNow){
            if (parentOfFlightWithID[flight] == -1) {
                if (flight != source) {
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
