package application;

import com.lynden.gmapsfx.javascript.object.Marker;
import com.lynden.gmapsfx.javascript.object.MarkerOptions;
import com.lynden.gmapsfx.util.MarkerImageFactory;

public class EventMarker extends Marker {
	private int eventId;
	
	public EventMarker(int eventId, MarkerOptions markerOptions) {
		super(markerOptions);
		
		markerOptions.icon(MarkerImageFactory.createMarkerImage("/view/img/school_icon_red.png", "png").replace("(", "").replace(")", ""));
		this.setOptions(markerOptions);
		this.eventId = eventId;
	}
	
	public int getEventId() {
		return eventId;
	}
}
