package events;

import cs360.team4.project.School;
import tables.EventTable;

public class Regional extends Event{
	
	protected EventTable sectionals;
	
	public Regional(){
		super();
		sectionals = null;
	}
	
	public Regional(int pId, School pHost, EventTable pSectionals){
		super(pId, pHost);
		sectionals = pSectionals;
	}
	
}
