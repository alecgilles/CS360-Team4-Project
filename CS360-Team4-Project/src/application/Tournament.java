package application;

import java.util.Observable;

import tables.EventTable;
import tables.SchoolTable;
import tables.TimeTable;

public class Tournament extends Observable {

	private static Tournament tournament = new Tournament();
	private EventTable events;
	private SchoolTable schools;
	private TimeTable driveTimes;

	private Tournament() {
		setEvents(new EventTable());
		setSchools(new SchoolTable());
		setDriveTimes(new TimeTable());
		
	}

	/**
	 * @return the tournament
	 */
	public static Tournament getTournament() {
		return tournament;
	}

	/**
	 * @param tournament
	 *            the tournament to set
	 */
	public static void setTournament(Tournament tournament) {
		Tournament.tournament = tournament;
	}

	/**
	 * @return the events
	 */
	public EventTable getEvents() {
		return events;
	}

	/**
	 * @param events the events to set
	 */
	public void setEvents(EventTable events) {
		setChanged();
		notifyObservers();
		this.events = events;
	}

	/**
	 * @return the schools
	 */
	public SchoolTable getSchools() {
		return schools;
	}

	/**
	 * @param schools the schools to set
	 */
	public void setSchools(SchoolTable schools) {
		setChanged();
		notifyObservers();
		this.schools = schools;
	}

	/**
	 * @return the driveTimes
	 */
	public TimeTable getDriveTimes() {
		return driveTimes;
	}

	/**
	 * @param driveTimes the driveTimes to set
	 */
	public void setDriveTimes(TimeTable driveTimes) {
		this.driveTimes = driveTimes;
	}

}
