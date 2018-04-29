package application;

import events.Event;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;

public class EventCell extends ListCell<Event> {
	private final EventCellController eventCellController;
	private final Parent cellRoot;
	private final Tournament tournament;

	public EventCell(ListView<Event> listView) {
		tournament = Tournament.getTournament();
		eventCellController = new EventCellController(listView);
		cellRoot = eventCellController.getRoot();

		this.prefWidthProperty().bind(listView.widthProperty());
		setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
	}

	@Override
	protected void updateItem(Event item, boolean isEmpty) {
		super.updateItem(item, isEmpty);
		if (isEmpty) {
			setGraphic(null);
		} else {
			eventCellController.setEvent(item);
			setGraphic(cellRoot);
		}
	}

	private class EventCellController {
		@FXML
		private Label hostSchoolName;

		@FXML
		private Label attendingSchools;

		private Parent root;
		private Event event;

		public EventCellController(ListView<Event> listView) {
			try {
				FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/EventCellView.fxml"));
				loader.setController(this);
				root = loader.load();
			} catch (IOException e) {
				e.printStackTrace();
			}

			attendingSchools.prefWidthProperty().bind(listView.widthProperty().subtract(35));
		}

		public Parent getRoot() {
			return root;
		}

		public void setEvent(Event event) {
			this.event = event;
			hostSchoolName.setText(event.getHost().getName() + ": Average Distance: "
					+ tournament.getDriveTimes().calculateAverageEventDriveTime(event) + " - Max Distance: "
					+ tournament.getDriveTimes().calculateMaxEventDriveTime(event));
			attendingSchools.setText(event.getAttendingSchoolsAsString());
		}

		@FXML
		protected void onEditButton(ActionEvent e) {
			new EditEventDialog(event).showAndWait();
		}
	}
}
