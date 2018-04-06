package tables;

import application.School;
import events.Event;

import java.util.ArrayList;
import java.util.Observable;

public class TimeTable extends Observable{
	
	private double[][] data;
	
	public TimeTable(){
		setData(null);
	}
	
	/**Calculates and returns the average drive time in hours and minutes for any event.
	 * @param event Any event for which average drive time is needed. 
	 * @return A string representation of max drive time for an event.
	 */
	public String calculateAverageDriveTime(Event event){
		
		double average = 0;
		double sum = 0;
		double count = 0;
		int hours = 0;
		int minutes = 0;
		
		ArrayList<School> schools = event.getAttendingSchools();
		School host = event.getHost();
		
		for(int i = 0; i < schools.size(); i++){
			sum += data[host.getId()][schools.get(i).getId()];
			count++;
		}
		
		average = sum/count;
		hours = (int) Math.floor(average / 60);
		minutes = (int) Math.round(average % 60);
		
		return hours + "h " + minutes + "mins";
	}

	/**Calculates the maximum drive time in hours and minutes for any event.
	 * @param event Any event for which a maximum drive time is needed.
	 * @return A string representation of max drive time for an event.
	 */
	public String calculateMaxDriveTime(Event event){
		
		double max = 0;
		double current;
		int hours = 0;
		int minutes = 0;

		ArrayList<School> schools = event.getAttendingSchools();
		School host = event.getHost();

		for(int i = 0; i < schools.size(); i++){
			current = data[host.getId()][schools.get(i).getId()];
			if(current > max){
				max = current;
			}
		}
		
		hours = (int) Math.floor(max / 60);
		minutes = (int) Math.round(max % 60);

		return hours + "h " + minutes + "mins";
	}

	/**
	 * @return the data
	 */
	public double[][] getData() {
		return data;
	}

	/**
	 * @param data the data to set
	 */
	public void setData(double[][] data) {
		this.data = data;
		setChanged();
		notifyObservers();
	}
	
	

}
