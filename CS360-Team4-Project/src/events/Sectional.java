package events;

import java.util.ArrayList;

import cs360.team4.project.School;
import tables.SchoolTable;

public class Sectional extends Event {

	protected SchoolTable schools;

	public Sectional() {
		super();
		schools = null;
	}

	public Sectional(int pId, School pHost, SchoolTable pSchools) {
		super(pId, pHost);
		schools = pSchools;
	}

	public SchoolTable getSchools() {
		return schools;
	}

	public ArrayList<School> getAttendingSchools() {
		ArrayList<School> schoolList = new ArrayList<>();

		for (int i = 0; i < schools.size(); i++) {
			schoolList.add(schools.getByIndex(i));
		}

		return schoolList;
	}
}
