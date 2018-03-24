package events;

import cs360.team4.project.School;
import tables.SchoolTable;

public class Sectional extends Event{
	
	protected SchoolTable schools;
	
	public Sectional(){
		super();
		schools = null;
	}
	
	public Sectional(int pId, School pHost, SchoolTable pSchools){
		super(pId, pHost);
		schools = pSchools;
	}

	public SchoolTable getSchools(){
		return schools;
	}

	@Override
	public String toString() {
		String retVal = "";
		
		retVal += host.getName() + "\nFeeder Schools: ";
		
		//Add all school names from the sectional to the list of feeder schools
		for (int i = 0; i < schools.size(); i++){
			if (i == schools.size() - 1){
				retVal += schools.getByIndex(i).getName();
			}
			else{
				retVal += schools.getByIndex(i).getName() + ", ";	
			}
		}
		
		return retVal;
	}
		
}
