// Lab12.java
// Author: Nihesh Anderson K (2016059)
// 12 April, 2016

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.StringTokenizer;


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

class flight{
	int departure;
	int arrival;
	int destination;
	String id;
	flight(int departure, int arrival, int destination, String id){
		this.departure = departure;
		this.arrival = arrival;
		this.destination = destination;
		this.id = id;
	}
}

class node{
	flight flightDetails;
	node next;
	node(flight flightDetails){
		this.flightDetails = flightDetails;
		next = null;
	}
}

class airport{
	node front;
	airport(){
		front = null;
	}
	public void insert(flight value){
		node ptr = new node(value);
		if(front == null){
			front = ptr;
		}
		else{
			ptr.next = front;
			front = ptr;
		}
	}
}

class sequence{
	int time;
	String flightSequence;
	int count;
	int firstDeparture;
	sequence(){
		time = 2400;
		flightSequence = "";
		count = 0;
		firstDeparture = 2400;
	}
}

public class Lab12_bonus{
	static int minjourneytime=2400;
	public static sequence fastestreach(int sourceCity, int destinationCity, int[] visitedArray, airport[] listOfCities, int presentTime, String flightID, int count, int initialDeparture){
		if(sourceCity == destinationCity){
			sequence minimumData = new sequence();
			minimumData.time = 0;
			minimumData.flightSequence = flightID;
			minimumData.count = count;
			minimumData.firstDeparture = initialDeparture;
			int journeytime = calculateTime(initialDeparture,presentTime);
			if(minjourneytime>journeytime){
				minjourneytime = journeytime;
			}	
			return minimumData;
		}
		else{
			sequence minimumData = new sequence();
			int journeytime = calculateTime(initialDeparture,presentTime);
			if(journeytime>minjourneytime){
				return minimumData;
			}	
			sequence travelData;
			visitedArray[sourceCity] = 1;
			node temp = listOfCities[sourceCity].front;
			int travelTime;
			while(temp!=null){
				if(visitedArray[temp.flightDetails.destination]!=1 && presentTime <= temp.flightDetails.departure){
					travelData = fastestreach(temp.flightDetails.destination, destinationCity, visitedArray, listOfCities, temp.flightDetails.arrival, temp.flightDetails.id, count+1, initialDeparture);
					travelTime = calculateTime(presentTime,temp.flightDetails.arrival)+travelData.time;
					if(minimumData.time > travelTime){
						minimumData.time = travelTime;
						minimumData.flightSequence = flightID+" "+travelData.flightSequence;
						minimumData.count = travelData.count;
						minimumData.firstDeparture = travelData.firstDeparture;
					}
					else if(minimumData.time == travelTime){
						if(minimumData.firstDeparture == travelData.firstDeparture){
							if(minimumData.count > travelData.count){
								minimumData.flightSequence = flightID+" "+travelData.flightSequence;
								minimumData.count = travelData.count;
								minimumData.firstDeparture = travelData.firstDeparture;
							}
						}
					}
				}
				temp = temp.next;
			}
			visitedArray[sourceCity] = 0;
			return minimumData;
		}
	}
    public static int calculateTime(int sourcetime, int destinationtime){
        int hours = (int)(destinationtime/100)-(int)(sourcetime/100);
        int minutes = (destinationtime%100) - (sourcetime%100);
        return (hours*60)+minutes;
    }
	public static void main(String[] args) throws IOException{
		Reader.init(System.in);
		int sourceCity, destinationCity, startTime, reachTime, N, noOfFlights;
		N = Reader.nextInt();
		sourceCity = Reader.nextInt();
		destinationCity = Reader.nextInt();
		startTime = Reader.nextInt();
		reachTime = Reader.nextInt();
		int[] visitedArray = new int[501];
		noOfFlights = Reader.nextInt();
		airport[] listOfCities = new airport[N+1];
		for(int i=1; i<=N; i++){
			listOfCities[i] = new airport();
		}
		int flightSource, flightDestination, flightDeparture, flightArrival;
		String flightID;
		flight newFlight;
		for(int i=0; i<noOfFlights; i++){
			flightSource = Reader.nextInt();
			flightDestination = Reader.nextInt();
			flightID = Reader.next();
			flightDeparture = Reader.nextInt();
			flightArrival = Reader.nextInt();
			if(flightDeparture < startTime || flightArrival > reachTime){
				continue;
			}
			newFlight = new flight(flightDeparture, flightArrival, flightDestination, flightID);
			listOfCities[flightSource].insert(newFlight);
		}
		visitedArray[sourceCity] = 1;
		node temp = listOfCities[sourceCity].front;
		sequence minimumData = new sequence();
		sequence travelData;
		int travelTime;
		while(temp!=null){	
			travelData = fastestreach(temp.flightDetails.destination, destinationCity, visitedArray, listOfCities, temp.flightDetails.arrival, temp.flightDetails.id, 1, temp.flightDetails.departure);
			travelTime = calculateTime(temp.flightDetails.departure,temp.flightDetails.arrival)+travelData.time;
			if(minimumData.time > travelTime){
				minimumData.time = travelTime;
				minimumData.flightSequence = travelData.flightSequence;
				minimumData.count = travelData.count;
				minimumData.firstDeparture = travelData.firstDeparture;
			}
			else if(minimumData.time == travelTime){
				if(minimumData.firstDeparture > travelData.firstDeparture){
					minimumData.flightSequence = travelData.flightSequence;
					minimumData.count = travelData.count;
					minimumData.firstDeparture = travelData.firstDeparture;
				}
				else if(minimumData.firstDeparture == travelData.firstDeparture){
					if(minimumData.count > travelData.count){
						minimumData.flightSequence = travelData.flightSequence;
						minimumData.count = travelData.count;
						minimumData.firstDeparture = travelData.firstDeparture;
					}
				}
			}
			temp = temp.next;
		}
		if(minimumData.time%2400 == 0){

			System.out.println("-1");
		}
		else{
			System.out.println(minimumData.count);
			System.out.println(minimumData.flightSequence);
		}
	}
}