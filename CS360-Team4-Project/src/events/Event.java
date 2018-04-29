package events;

import application.School;
import java.util.ArrayList;
import tables.SchoolTable;

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

	/**
	 * 
	 * @return The list of schools that attend the event.
	 */
	public abstract ArrayList<School> getAttendingSchools();

	/**
	 * 
	 * @return The list of schools in the event that are willing to host.
	 */
	public SchoolTable getWillingHostSchools() {
		SchoolTable willingHosts = new SchoolTable();
		ArrayList<School> attendingSchools = getAttendingSchools();
		
		for (int i = 0; i < attendingSchools.size(); i++){
			if (attendingSchools.get(i).getWillingHost()) {
				willingHosts.add(attendingSchools.get(i));
			}
		}

		return willingHosts;
	}

	public abstract String getEventTypeAsString();

	@Override
	public String toString() {
		return host.getName();
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
