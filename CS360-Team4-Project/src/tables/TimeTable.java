package tables;

import application.School;
import application.Tournament;
import events.Event;

import java.util.ArrayList;
import java.util.Map;
import java.util.Observable;

public class TimeTable extends Observable {

	private double[][] data;

	public TimeTable() {
		setData(null);
	}

	/**
	 * Calculates and returns the average distance in miles for any event.
	 * 
	 * @param event
	 *            Any event for which average drive time is needed.
	 * @return A string representation of max drive time for an event.
	 */
	public String calculateAverageEventDriveTime(Event event) {

		double average = 0;
		double sum = 0;
		double count = 0;

		ArrayList<School> schools = event.getAttendingSchools();
		School host = event.getHost();

		for (int i = 0; i < schools.size(); i++) {
			sum += data[host.getId() - 1][schools.get(i).getId() - 1];
			count++;
		}

		average = sum / count;

		return String.format("%1$, .2f", average) + " miles";
	}

	/**
	 * Calculates the maximum distance in miles for any event.
	 * 
	 * @param event
	 *            Any event for which a maximum drive time is needed.
	 * @return A string representation of max drive time for an event.
	 */
	public String calculateMaxEventDriveTime(Event event) {

		double max = 0;
		double current;

		ArrayList<School> schools = event.getAttendingSchools();
		School host = event.getHost();

		for (int i = 0; i < schools.size(); i++) {
			current = data[host.getId() - 1][schools.get(i).getId() - 1];
			if (current > max) {
				max = current;
			}
		}

		return String.format("%1$, .2f", max) + " miles";
	}

	/**
	 * @return the data
	 */
	public double[][] getData() {
		return data;
	}

	/**
	 * Calculates and returns the average distance in miles for any level of
	 * competition.
	 * 
	 * @param tournament
	 *            The tournament.
	 * @param level
	 *            The level of competition in the tournament.
	 * @return A string representation of average distance for the tournament level.
	 */
	public String calculateAverageLevelDriveTime(Tournament tournament, int level) {

		double average = 0;
		double sum = 0;
		double count = 0;

		EventTable levelEvents = null;

		if (level == 2) {
			levelEvents = tournament.getEvents().getSectionals();
		} else if (level == 1) {
			levelEvents = tournament.getEvents().getRegionals();
		} else {
			levelEvents = tournament.getEvents().getSemiStates();
		}

		for (Map.Entry<Integer, Event> cursor : levelEvents.getData().entrySet()) {
			ArrayList<School> schools = cursor.getValue().getAttendingSchools();
			School host = cursor.getValue().getHost();

			for (int j = 0; j < schools.size(); j++) {
				sum += data[host.getId() - 1][schools.get(j).getId() - 1];
				count++;
			}
		}

		average = sum / count;

		return String.format("%1$, .2f", average) + " miles";
	}

	/**
	 * Calculates the maximum distance in miles for any level of competition.
	 * 
	 * @param tournament
	 *            The tournament.
	 * @param level
	 *            The level of competition in the tournament.
	 * @return A string representation of max distance for the tournament level.
	 */
	public String calculateMaxLevelDriveTime(Tournament tournament, int level) {

		double max = 0;
		double current;

		EventTable levelEvents = null;

		if (level == 2) {
			levelEvents = tournament.getEvents().getSectionals();
		} else if (level == 1) {
			levelEvents = tournament.getEvents().getRegionals();
		} else {
			levelEvents = tournament.getEvents().getSemiStates();
		}

		for (Map.Entry<Integer, Event> cursor : levelEvents.getData().entrySet()) {
			ArrayList<School> schools = cursor.getValue().getAttendingSchools();
			School host = cursor.getValue().getHost();

			for (int j = 0; j < schools.size(); j++) {
				current = data[host.getId() - 1][schools.get(j).getId() - 1];
				if (current > max) {
					max = current;
				}
			}
		}

		return String.format("%1$, .2f", max) + " miles";
	}

	/**
	 * @param data
	 *            the data to set
	 */
	public void setData(double[][] data) {
		this.data = data;
		setChanged();
		notifyObservers();
	}

}
