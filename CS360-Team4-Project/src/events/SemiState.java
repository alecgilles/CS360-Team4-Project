package events;

import cs360.team4.project.School;
import tables.EventTable;
import tables.SchoolTable;

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

	public EventTable getRegionals(){
		return regionals;
	}

	@Override
	public String toString() {
		String retVal = "";
		
		retVal += host.getName() + "\nFeeder Schools: ";
		
		//Loop through the regionals to get to all sectionals
		for (int i = 0; i < regionals.size(); i++){
			
			Regional curRegional = (Regional) regionals.getByIndex(i);
			EventTable curRegionalSectionals = curRegional.getSectionals();
			
			//Loop through sectionals to get all schools per regional
			for (int j = 0; j < curRegionalSectionals.size(); j++){
				
				Sectional curSectional = (Sectional) curRegionalSectionals.getByIndex(j);
				SchoolTable curSectionalSchools = curSectional.getSchools();
				
				//Get all schools in the current sectional
				for (int k = 0; k < curSectionalSchools.size(); k++){
					//Add the school to the string, and add a comma if it isn't the last school in the semi-state
					if ((i == regionals.size() - 1) && (j == curRegionalSectionals.size() - 1) && (k == curSectionalSchools.size() - 1)){
						retVal += curSectionalSchools.getByIndex(k).getName();
					}
					else{
						retVal += curSectionalSchools.getByIndex(k).getName() + ", ";	
					}
					
				}
				
			}
			
		}
		
		return retVal;
	}
	
}
