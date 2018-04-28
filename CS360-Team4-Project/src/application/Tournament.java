package application;

import events.Event;
import events.Sectional;

import java.util.Observable;

import javax.swing.JOptionPane;

import tables.EventTable;
import tables.SchoolTable;
import tables.TimeTable;

public class Tournament extends Observable {

	private static Tournament tournament = new Tournament();
	private String name;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the events
	 */
	public EventTable getEvents() {
		return events;
	}

	/**
	 * @param events
	 *            the events to set
	 */
	public void setEvents(EventTable events) {
		this.events = events;
		setChanged();
		notifyObservers();
	}

	/**
	 * @return the schools
	 */
	public SchoolTable getSchools() {
		return schools;
	}

	/**
	 * @param schools
	 *            the schools to set
	 */
	public void setSchools(SchoolTable schools) {
		this.schools = schools;
		setChanged();
		notifyObservers();
	}

	/**
	 * @return the driveTimes
	 */
	public TimeTable getDriveTimes() {
		return driveTimes;
	}

	/**
	 * @param driveTimes
	 *            the driveTimes to set
	 */
	public void setDriveTimes(TimeTable driveTimes) {
		this.driveTimes = driveTimes;
		setChanged();
		notifyObservers();
	}

	/**
	 * @param newHost
	 *            The school that is now willing to host events.
	 */
	public void addWillingHost(School school) {
		school.setWillingHost(true);
	}

	/**
	 * @param newHost
	 *            The school that is now willing to host events.
	 */
	public void removeWillingHost(School school) {
		school.setWillingHost(false);
	}

	/**
	 * 
	 * @param school
	 *            The school that is moving sectionals.
	 * @param newSectional
	 *            The new sectional for the school.
	 */
	public void switchSchoolToSectional(School school, Sectional newSectional) {
		// find the current sectional and remove the school from its list of schools
		events.getData().forEach((id, event) -> {
			if (event instanceof Sectional) {
				SchoolTable secSchools = ((Sectional) event).getSchools();
				if (secSchools.getByKey(school.getId()).equals(school)) {
					if (event.getHost().equals(school)) {
						int response = JOptionPane.showConfirmDialog(null, "This school is the host of it's current"
								+ " sectional. If this school changes sectionals, the current sectional will no"
								+ " longer have a host. Do you really want to move this school to a new sectional?",
								"Move School to New Sectional", JOptionPane.YES_NO_OPTION);
						if (response != JOptionPane.YES_OPTION) {
							JOptionPane.showMessageDialog(null, "School sectional not changed.");
							return;
						} else {
							event.setHost(null);
						}
					}
					secSchools.remove(school.getId());
				}
			}
		});
		newSectional.getSchools().add(school);
	}

	/**
	 * Change an event's host to a new school.
	 * 
	 * @param event
	 *            The event that is changing hosts.
	 * @param school
	 *            The new host of event.
	 */
	public void changeEventHost(Event event, School school) {
		if (event.getWillingHostSchools().getByKey(school.getId()) != null) {
			event.setHost(school);
		} else {
			JOptionPane.showMessageDialog(null, "School is not a willing host. Event host not changed.");
			return;
		}
	}

}
