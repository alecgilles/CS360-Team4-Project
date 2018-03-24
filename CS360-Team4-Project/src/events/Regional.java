package events;

import cs360.team4.project.School;
import tables.EventTable;
import tables.SchoolTable;

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

	public EventTable getSectionals(){
		return sectionals;
	}

	@Override
	public String toString() {
		String retVal = "";
		
		retVal += host.getName() + "\nFeeder Schools: ";
		
		//Loop through sectionals to get all schools per regional
		for (int i = 0; i < sectionals.size(); i++){
			Sectional curSectional = (Sectional) sectionals.getByIndex(i);
			SchoolTable curSectionalSchools = curSectional.getSchools();
			
			//Get all schools in the current sectional
			for (int j = 0; j < curSectionalSchools.size(); j++){
				//Add the school to the string, and add a comma if it isn't the last school in the regional
				if ((i == sectionals.size() - 1) && (j == curSectionalSchools.size() - 1)){
					retVal += curSectionalSchools.getByIndex(j).getName();
				}
				else{
					retVal += curSectionalSchools.getByIndex(j).getName() + ", ";	
				}
			}
		}
		
		return retVal;
	}
	
}
