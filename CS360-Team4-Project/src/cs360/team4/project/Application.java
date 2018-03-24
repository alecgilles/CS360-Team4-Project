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
		
		EventTable sectionals = new EventTable();
		sectionals = allEvents.getSectionals();
		EventTable regionals = new EventTable();
		regionals = allEvents.getRegionals();
		EventTable semistates = new EventTable();
		semistates = allEvents.getSemiStates();
		
		for (int i = 0; i < sectionals.size(); i++){
			System.out.println(sectionals.getByIndex(i).toString());
		}

		for (int i = 0; i < regionals.size(); i++){
			System.out.println(regionals.getByIndex(i).toString());
		}

		for (int i = 0; i < semistates.size(); i++){
			System.out.println(semistates.getByIndex(i).toString());
		}

		//MainView mv = new MainView();
		
	}

}
