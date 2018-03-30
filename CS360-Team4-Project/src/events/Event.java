package events;

import java.util.ArrayList;

import cs360.team4.project.School;

abstract public class Event {

	protected int id;
	protected School host;

	public Event() {
		id = -1;
		host = null;
	}

	public Event(int pId, School pHost) {
		id = pId;
		host = pHost;
	}

	public void setId(int pId) {
		id = pId;
	}

	public int getId() {
		return id;
	}

	public void setHost(School pHost) {
		host = pHost;
	}

	public School getHost() {
		return host;
	}

	abstract public ArrayList<School> getAttendingSchools();

	@Override
	public String toString() {
		String retVal = "";
		ArrayList<School> schools = getAttendingSchools();

		return retVal += host.getName() + "\n" + schools.size() + " Feeder Schools: \n" + getAttendingSchoolsAsString();
	}

	public String getAttendingSchoolsAsString() {
		String retVal = "";
		ArrayList<School> schools = getAttendingSchools();

		for (int i = 0; i < schools.size(); i++) {
			// Add the school to the string, and add a comma if it isn't the last school
			if (i == schools.size() - 1) {
				retVal += schools.get(i).getName();
			} else {
				retVal += schools.get(i).getName() + ", ";
			}
		}

		return retVal;
	}

}
