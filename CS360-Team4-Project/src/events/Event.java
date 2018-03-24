package events;

import cs360.team4.project.School;

abstract public class Event {

	protected int id;
	protected School host;
	
	public Event(){
		id = -1;
		host = null;
	}
	
	public Event(int pId, School pHost){
		id = pId;
		host = pHost;
	}
	
	public void setId(int pId){
		id = pId;
	}
	
	public int getId(){
		return id;
	}
	
	public void setHost(School pHost){
		host = pHost;
	}
	
	public School getHost(){
		return host;
	}
	
	abstract public String toString();
	
}
