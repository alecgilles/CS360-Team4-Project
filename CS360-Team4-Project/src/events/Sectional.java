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
	
}
