package tables;

import java.util.HashMap;

import cs360.team4.project.School;

public class SchoolTable {

	private HashMap<Integer, School> data;

	/**
	 * Instantiate new ArrayLists.
	 */
	public SchoolTable() {
		data = new HashMap<>(200);
	}

	/**
	 * Add a School to the ArrayList
	 * 
	 * @param element
	 *            A School to be added.
	 * @return false if element's site_Number has already been used, true if element
	 *         was added.
	 */
	public boolean add(School element) {
		// add a site to the table
		int key = element.getId();
		if (data.containsKey(key)) {
			// Don't re-add anything that has already been added.
			return false;
		}
		// add data
		data.put(key, element);
		return true;
	}

	/**
	 * Function to allow users to retrieve a site from the current list of sites.
	 * 
	 * @param key
	 *            the Site_Number of the desired School
	 * @return null if key is not found in list of Site_Numbers, otherwise return
	 *         the associated School
	 */
	public School getByKey(int key) {
		// get a site from the table
		return data.get(key);
	}

	/**
	 * Get whole data.
	 */
	public HashMap<Integer, School> getData() {
		return data;
	}

	/**
	 * 
	 * @param key
	 *            the Site_Number of the School to be removed
	 * @return the School removed
	 */
	public School remove(int key) {
		// remove an a site from the table
		return data.remove(key);
	}

	/**
	 * Allow user to update an existing School by replace the existing with a new
	 * one
	 * 
	 * @param element
	 *            the updated School
	 * @return the old School that was replaced
	 */
	public School edit(School element) {
		// replace a site with updated site
		if (data.containsKey(element.getId())) {
			School answer = data.remove(element.getId());
			data.put(element.getId(), element);
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
	 * Print method for testing
	 */
	public void print() {
		System.out.println("Schools:");
		data.forEach((k, v) -> {
			System.out.println(v + "\n");
		});
	}

}
