package events;

import java.util.ArrayList;

import cs360.team4.project.School;
import tables.EventTable;

public class Regional extends Event {

	protected EventTable sectionals;

	public Regional() {
		super();
		sectionals = null;
	}

	public Regional(int pId, School pHost, EventTable pSectionals) {
		super(pId, pHost);
		sectionals = pSectionals;
	}

	public EventTable getSectionals() {
		return sectionals;
	}

	public ArrayList<School> getAttendingSchools() {
		ArrayList<School> schoolList = new ArrayList<>();

		for (int i = 0; i < sectionals.size(); i++) {
			Sectional currSectional = (Sectional) sectionals.getByIndex(i);
			schoolList.addAll(currSectional.getAttendingSchools());
		}

		return schoolList;
	}
}
