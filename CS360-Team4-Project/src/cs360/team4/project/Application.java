package cs360.team4.project;

import tables.*;
import events.*;
import readers.*;

public class Application {

	public static void main(String[] args) {
		
		SchoolTable allSchools = new SchoolTable();
		EventTable allEvents = new EventTable();
		
		SchoolReader schoolReader = new SchoolReader();
		SectionalReader sectionalReader = new SectionalReader();
		RegionalReader regionalReader = new RegionalReader();
		SemistateReader semistateReader = new SemistateReader();

		allSchools = schoolReader.readFile("Schools.csv", allSchools);
		allSchools.print();
		
		allEvents = sectionalReader.readFile("Sectionals.csv", allSchools, allEvents);
		
		allEvents = regionalReader.readFile("Regionals.csv", allSchools, allEvents);
		
		allEvents = semistateReader.readFile("SemiStates.csv", allSchools, allEvents);
		
		for (int i = 0; i < allEvents.size(); i++){
			if (allEvents.getByIndex(i) instanceof Sectional){
				System.out.println(((Sectional) allEvents.getByIndex(i)).toString() + "\n");
			}
			else if (allEvents.getByIndex(i) instanceof Regional){
				System.out.println(((Regional) allEvents.getByIndex(i)).toString() + "\n");
			}
			else if (allEvents.getByIndex(i) instanceof SemiState){
				System.out.println(((SemiState) allEvents.getByIndex(i)).toString() + "\n");
			}

		}
		
	}

}
