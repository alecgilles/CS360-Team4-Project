package events;

import java.util.ArrayList;

import cs360.team4.project.School;
import tables.EventTable;

public class SemiState extends Event {

	protected EventTable regionals;

	public SemiState() {
		super();
		regionals = null;
	}

	public SemiState(int pId, School pHost, EventTable pRegionals) {
		super(pId, pHost);
		regionals = pRegionals;
	}

	public EventTable getRegionals() {
		return regionals;
	}

	public ArrayList<School> getAttendingSchools() {
		ArrayList<School> schoolList = new ArrayList<>();

		for (int i = 0; i < regionals.size(); i++) {
			Regional currRegional = (Regional) regionals.getByIndex(i);
			schoolList.addAll(currRegional.getAttendingSchools());
		}

		return schoolList;
	}
}
