package events;

import application.School;
import java.util.ArrayList;
import tables.EventTable;

public class SemiState extends Event {

	protected EventTable regionals;

	public SemiState() {
		super();
		regionals = null;
	}

	public SemiState(int id, School host, EventTable regionals) {
		super(id, host);
		this.regionals = regionals;
	}

	public EventTable getRegionals() {
		return regionals;
	}

	public ArrayList<School> getAttendingSchools() {
		ArrayList<School> schoolList = new ArrayList<>();

		regionals.getData().forEach((k, v) -> {
			schoolList.addAll(v.getAttendingSchools());
		});

		return schoolList;
	}

	@Override
	public String getEventTypeAsString() {
		return "Semi-State";
	}
}
