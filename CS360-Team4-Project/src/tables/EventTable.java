package tables;

import events.Event;
import events.Regional;
import events.Sectional;
import events.SemiState;
import java.util.HashMap;
import java.util.Observable;

public class EventTable extends Observable{

	private HashMap<Integer, Event> data;

	public EventTable() {
		data = new HashMap<>();
	}

	/**
	 * Add a Event to the ArrayList
	 * 
	 * @param element
	 *            A Event to be added.
	 * @return false if element's site_Number has already been used, true if element
	 *         was added.
	 */
	public boolean add(Event element) {
		// add a site to the table
		int key = element.getId();
		if (data.containsKey(key)) {
			// Don't re-add anything that has already been added.
			return false;
		}
		// add data
		data.put(key, element);
		setChanged();
		notifyObservers();
		return true;
	}

	/**
	 * Function to allow users to retrieve a site from the current list of sites.
	 * 
	 * @param key
	 *            the Site_Number of the desired Event
	 * @return null if key is not found in list of Site_Numbers, otherwise return
	 *         the associated Event
	 */
	public Event getByKey(int key) {
		// get a site from the table
		return data.get(key);
	}

	/**
	 * Get whole data.
	 */
	public HashMap<Integer, Event> getData() {
		return data;
	}

	/**
	 * 
	 * @param key
	 *            the Site_Number of the Event to be removed
	 * @return the Event removed
	 */
	public Event remove(int key) {
		// remove an a site from the table
		setChanged();
		notifyObservers();
		return data.remove(key);
	}

	/**
	 * Allow user to update an existing Event by replace the existing with a new one
	 * 
	 * @param element
	 *            the updated Event
	 * @return the old Event that was replaced
	 */
	public Event edit(Event element) {
		// replace a site with updated site
		if (data.containsKey(element.getId())) {
			Event answer = data.remove(element.getId());
			data.put(element.getId(), element);
			setChanged();
			notifyObservers();
			return answer;
		} else {
			return null;
		}
	}

	/**
	 * Get how many objects are in the table.
	 * 
	 * @return the size of the table
	 */
	public int size() {
		return data.size();
	}

	/**
	 * Get all the sectional in a tournament.
	 * 
	 * @return a table of all events
	 */
	public EventTable getSectionals() {
		EventTable sectionals = new EventTable();
		data.forEach((k, v) -> {
			if (v instanceof Sectional) {
				sectionals.add(v);
			}
		});

		return sectionals;
	}

	/**
	 * Get all the regionals in a tournament.
	 * 
	 * @return a table of all regionals
	 */
	public EventTable getRegionals() {
		EventTable regionals = new EventTable();
		data.forEach((k, v) -> {
			if (v instanceof Regional) {
				regionals.add(v);
			}
		});

		return regionals;
	}

	/**
	 * Get all the semi-states in a tournament.
	 * 
	 * @return a table of all semi-states
	 */
	public EventTable getSemiStates() {
		EventTable semistates = new EventTable();
		data.forEach((k, v) -> {
			if (v instanceof SemiState) {
				semistates.add(v);
			}
		});

		return semistates;
	}

	/**
	 * Print method for testing.
	 */
	public void print() {
		System.out.println("Schools:");
		data.forEach((k, v) -> {
			System.out.println(v + "\n");
		});
	}

}