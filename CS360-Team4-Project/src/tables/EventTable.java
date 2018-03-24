package tables;

/**
 * @author Skyler Haas
 * SOFTENG_Assignment8
 * Mar 24, 2018
 */

import java.util.ArrayList;

import events.Event;

public class EventTable {
	
	
	private ArrayList<Integer> keys;
	private ArrayList<Event> data;
	
	
	/**
	 * Instantiate new ArrayLists.
	 */
	public EventTable(){
		keys = new ArrayList<Integer>();
		data = new ArrayList<Event>();
	}
	
	
	/**
	 * Add a Event to the ArrayList
	 * @param element A Event to be added.
	 * @return false if element's site_Number has already been used, true if element was added.
	 */
	public boolean add(Event element){
		//add a site to the table
		int key = element.getId();
		if (keys.contains(key)){
			//Don't re-add anything that has already been added.
			return false;
		}
		//add data
		keys.add(key);
		data.add(element);
		sortTable();
		return true;
	}
	
	
	/**
	 * Function to allow users to retrieve a site from the current list of sites.
	 * @param key the Site_Number of the desired Event
	 * @return null if key is not found in list of Site_Numbers, otherwise return the associated Event
	 */
	public Event getByKey(int key){
		//get a site from the table
		int index = keys.indexOf(key);
		if (index == -1)
			return null;
		return data.get(index);
	}

	
	/**
	 * Function to allow users to retrieve a site from the current list of sites.
	 * @param index the index of the desired Event
	 * @return null if index index is out of bounds, otherwise return the associated Event
	 */
	public Event getByIndex(int index){
		//get a site from the table
		if (index < -1 || index > data.size() - 1)
			return null;
		return data.get(index);
	}
	
	
	/**
	 * Get whole data.
	 */
	public ArrayList<Event> getData(){
		return data;
	}
	
	/**
	 * 
	 * @param key the Site_Number of the Event to be removed
	 * @return the Event removed
	 */
	public Event remove(int key){
		//remove an a site from the table
		int indexToRemove = keys.indexOf(key);
		if (indexToRemove == -1)
			return null;
		keys.remove(indexToRemove);
		return data.remove(indexToRemove);
	}
	
	
	/**
	 * Allow user to update an existing Event by replace the existing with a new one
	 * @param element the updated Event
	 * @return the old Event that was replaced
	 */
	public Event edit(Event element){
		//replace a site with updated site
		if(keys.contains(element.getId())){
			int index = keys.indexOf(element.getId());
			Event answer = data.remove(index);
			data.add(index, element);
			return answer;
		}
		else{
			return null;
		}
	}
	
	
	/**
	 * Sort the ArrayLists by creating new ones, then looping and adding the smallest one until all have been added
	 */
	private void sortTable(){
		//If there is less than 2 sites, there is no need to sort.
		if(data.size() < 2)
			return;
		
		//Create new ArrayLists for the sorted sites and keys.
		ArrayList<Integer> sortedKeys = new ArrayList<Integer>();
		ArrayList<Event> sortedData = new ArrayList<Event>();
		int minIndex = 0;	//index of the current minimum Site_Number
		
		int i = 0; 	//track temp min
		int j = 1;	//track next index to check
		
		while (data.size() > 1){
			if(keys.get(i) < keys.get(j)){
				j++;
			}
			else{
				minIndex = j;
				i = j;
				j++;
			}
			//If j is greater than the number of sites, add the min to the new ArrayLists and remove the min from the current ArrayLists.
			if(j >= data.size()){
				sortedKeys.add(keys.get(minIndex));
				sortedData.add(data.get(minIndex));
				remove(keys.get(minIndex));
				i = 0;
				minIndex = i;
				j = 1;
			}
		}
		//add last item
		sortedKeys.add(keys.get(0));
		sortedData.add(data.get(0));
		
		//set the keys and data to the sorted keys and data.
		keys = sortedKeys;
		data = sortedData;
		
	}
	
	
	/**
	 * Get how many objects are in the table.
	 * @return the size of the table
	 */
	public int size(){
		return data.size();
	}

	
	/**
	 * Print method for testing
	 */
	public void print(){
		System.out.println("Schools:");
		for(int i = 0; i < data.size(); i++){
			System.out.println(data.get(i).toString() + "\n");
		}
	}

}

