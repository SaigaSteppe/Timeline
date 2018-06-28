import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class RunTimeline {
	
	public static void main(String[] args) {
		ArrayList<TimeEvent> timeEvent = new ArrayList<TimeEvent>();
		timeEvent = readCSV("timeline1.csv");
		Map<Integer, ArrayList<String>> map1 = mapDateEvents(timeEvent);
		
		
		int timelineStart = calculateStart(timeEvent);
		int timelineEnd = calculateEnd(timeEvent);
		
		printTimeline(map1, timelineStart,timelineEnd, calculateIncrement(timelineStart,timelineEnd));
	}
	
	/**
	 * Reads the CSV file and put all items into a list
	 */
	public static ArrayList<TimeEvent> readCSV (String filename){
	    ArrayList<TimeEvent> timeEvents = new ArrayList<TimeEvent> ();
	    try{
			Scanner scan = new Scanner(Paths.get(filename));
			//String line = scan.nextLine();	    
			while (scan.hasNextLine()) {
				String line = scan.nextLine();
				String[] lineArray = line.split(",");
				int date = Integer.parseInt(lineArray[0]);
				String event = lineArray[1].toString();
				
	        
	        //int,String
			timeEvents.add(new TimeEvent(date,event));
			}

	    }
	    catch(IOException e){
			System.out.println("IO exception");
		}
		return timeEvents;
	}
	
	/**
	 * Multivalue map, maps the unique dates with events
	 */
	public static Map<Integer, ArrayList<String>> mapDateEvents(ArrayList<TimeEvent>timeEvent){
		
		//array list with all the dates from the timeEvent array list
		ArrayList<Integer> dates = new ArrayList<Integer>();
		for(TimeEvent i:timeEvent) {
			dates.add(i.getDate());
		}
				
		//map with unique dates
		Map<Integer, ArrayList<String>> multiValueMap = new HashMap<Integer, ArrayList<String>>();
		for(Integer i:dates) {
			multiValueMap.put(i, new ArrayList<String>());
		}

		//map dates with events
		for(TimeEvent i: timeEvent){
			multiValueMap.get(i.getDate()).add(i.getEvent());
		}
		
		return multiValueMap;
	}
	
	/**
	 * Returns the start date of the timeline
	 */
	public static int calculateStart( ArrayList<TimeEvent> t) {
		int firstEnteredDate = t.get(0).getDate();
		return (int) (10*(Math.floor(Math.abs(firstEnteredDate/10.0))));
	}
	
	/**
	 * Returns the end date of the timeline
	 */
	public static int calculateEnd( ArrayList<TimeEvent> t) {
		int lastEnteredDate = t.get(t.size()-1).getDate();
		return (int) (10*(Math.ceil(Math.abs(lastEnteredDate/10.0))));
	}
	
	/**
	 * Returns an Arraylist of increments of the timeline
	 */
	public static ArrayList<Integer> calculateIncrement(int calculateStart,int calculateEnd) {
		int increment;
		ArrayList<Integer> timeIncrement = new ArrayList<Integer> ();
		int difference = calculateEnd - calculateStart;
		
		if(difference < 50) {
			increment = 5;
		}
		else if(50<= difference  && difference < 100) {
			increment = 10;
		}
		else if(difference < 200) {
			increment = 20;
		}
		
		else {
			increment = (int) (10*(Math.floor(Math.abs(difference/100.0))));
		}
		
		for(int i = calculateStart; i<=calculateEnd; i += increment) {
			timeIncrement.add(i);
		}
		return timeIncrement;
		
	}
	
	/**
	 * Prints the timeline
	 */
	public static void printTimeline(Map<Integer, ArrayList<String>> dateEventMap,int calStart, int calEnd, ArrayList<Integer> calIncrement) {
		int incrementIndex = 0;
	
		for(int i = calStart; i<=calEnd; i++) {

			//i is equal to the date  in the map
			if(dateEventMap.containsKey(i)) {
				
				//the date of an event is equal to an increment date of the timeline
				if(i == calIncrement.get(incrementIndex)) {
					System.out.println("-" + i + " " + dateEventMap.get(i));
					
					if(incrementIndex < calIncrement.size()-1) {
						++incrementIndex;
					}
				}
				
				else {
					System.out.println(i + " " + dateEventMap.get(i));
				}
			}
			
			//i is equal to an increment date of the timeline
			else if(i == calIncrement.get(incrementIndex)) {
				System.out.println("-" + calIncrement.get(incrementIndex));
				
				if(incrementIndex < calIncrement.size()-1) {
					++incrementIndex;
				}
			}
				
			else {
				System.out.println(".");
			}
		}
		
	}


}
