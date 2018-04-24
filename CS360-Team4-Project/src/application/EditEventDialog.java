package application;

import events.Event;
import javafx.geometry.Insets;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.layout.GridPane;

public class EditEventDialog extends Dialog<Boolean> {
	public EditEventDialog(Event event) {
		this.setTitle("Edit "+event.getEventTypeAsString()+" Event");
		this.setHeaderText(null);
		this.setGraphic(null);
		
		this.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
		
		GridPane pane = new GridPane();
		pane.setPadding(new Insets(5, 5, 5, 5));
		
		
		
		this.getDialogPane().setContent(pane);
		
		this.setResultConverter(dialogButton -> {
			if (dialogButton == ButtonType.OK) {
				//Update event
				return true;
			}

			return false;
		});
	}
}
