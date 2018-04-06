package tables;

import application.School;
import java.util.ArrayList;

public class TimeTable {
	
	private double[][] data;
	
	public TimeTable(){
		setData(null);
	}
	
	/**
	 * 
	 */
	public double calculateAverageDriveTime(School host, ArrayList<School> schools){
		
		double average = 0;
		double sum = 0;
		double count = 0;
		
		for(int i = 0; i < schools.size(); i++){
			sum += data[host.getId()][schools.get(i).getId()];
			count++;
		}
		
		average = sum/count;
		return average;
	}

	/**
	 * 
	 */
	public double calculateMaxDriveTime(School host, ArrayList<School> schools){
		
		double max = 0;
		double current;
		
		for(int i = 0; i < schools.size(); i++){
			current = data[host.getId()][schools.get(i).getId()];
			if(current > max){
				max = current;
			}
		}
		
		return max;
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
	}
	
	

}
