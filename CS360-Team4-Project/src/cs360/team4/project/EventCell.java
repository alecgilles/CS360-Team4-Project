package cs360.team4.project;

import java.io.IOException;

import events.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;

public class EventCell extends ListCell<Event> {
	private final EventCellController eventCellController = new EventCellController();
	private final Parent cellRoot = eventCellController.getRoot();
	
	public EventCell(ListView<Event> listView) {
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
		private Text hostSchoolName;
		
		@FXML
		private TextArea attendingSchools;
		
		private Parent root;
		
		public EventCellController() {
			try {
				FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/EventCellView.fxml"));
				loader.setController(this);
				root = loader.load();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		public Parent getRoot() {
			return root;
		}
		
		public void setEvent(Event event) {
			hostSchoolName.setText(event.getHost().getName());
			attendingSchools.setText(event.getAttendingSchoolsAsString()); 
		}
	}
}
