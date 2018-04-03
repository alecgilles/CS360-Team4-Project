package events;

import application.School;
import java.util.ArrayList;
import tables.SchoolTable;

public class Sectional extends Event {

	protected SchoolTable schools;

	public Sectional() {
		super();
		schools = null;
	}

	public Sectional(int id, School host, SchoolTable schools) {
		super(id, host);
		this.schools = schools;
	}

	public SchoolTable getSchools() {
		return schools;
	}

	public ArrayList<School> getAttendingSchools() {
		ArrayList<School> schoolList = new ArrayList<>();
		schoolList.addAll(schools.getData().values());

		return schoolList;
	}
}
