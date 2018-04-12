package events;

import application.School;
import java.util.ArrayList;

public abstract class Event {

	protected int id;
	protected School host;

	public Event() {
		id = -1;
		host = null;
	}

	public Event(int id, School host) {
		this.id = id;
		this.host = host;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setHost(School host) {
		this.host = host;
	}

	public School getHost() {
		return host;
	}

	public abstract ArrayList<School> getAttendingSchools();
	
	public abstract String getEventTypeAsString();

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
