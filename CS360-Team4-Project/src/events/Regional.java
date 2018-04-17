package events;

import application.School;
import java.util.ArrayList;
import tables.EventTable;

public class Regional extends Event {

	protected EventTable sectionals;

	public Regional() {
		super();
		sectionals = null;
	}

	public Regional(int id, School host, EventTable sectionals) {
		super(id, host);
		this.sectionals = sectionals;
	}

	public EventTable getSectionals() {
		return sectionals;
	}

	public ArrayList<School> getAttendingSchools() {
		ArrayList<School> schoolList = new ArrayList<>();

		sectionals.getData().forEach((k, v) -> {
			schoolList.addAll(v.getAttendingSchools());
		});

		return schoolList;
	}

	@Override
	public String getEventTypeAsString() {
		return "Regional";
	}
}
