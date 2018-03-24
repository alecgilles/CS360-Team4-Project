package events;

import cs360.team4.project.School;
import tables.EventTable;

public class SemiState extends Event{
	
	protected EventTable regionals;
	
	public SemiState(){
		super();
		regionals = null;
	}
	
	public SemiState(int pId, School pHost, EventTable pRegionals){
		super(pId, pHost);
		regionals = pRegionals;
	}
	
}
