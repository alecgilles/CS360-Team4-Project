package application;

import com.lynden.gmapsfx.javascript.object.Marker;
import com.lynden.gmapsfx.javascript.object.MarkerOptions;

public class EventMarker extends Marker {
	private int eventId;
	
	public EventMarker(int eventId, MarkerOptions markerOptions) {
		super(markerOptions);
		
		markerOptions.icon("https://cdn4.iconfinder.com/data/icons/building-1/512/build2-512.png");
		//System.out.println(getClass().getResource("/view/img/event.png").());
		this.setOptions(markerOptions);
		this.eventId = eventId;
	}
	
	public int getEventId() {
		return eventId;
	}
}
