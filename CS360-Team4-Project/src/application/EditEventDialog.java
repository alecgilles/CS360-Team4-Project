package application;

import events.Event;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;

public class EditEventDialog extends Dialog<Boolean> {
	private Tournament tournament = Tournament.getTournament();
	private Event event;
	private ReadOnlyObjectProperty<School> selectedSchool;

	public EditEventDialog(Event event) {
		this.event = event;
		
		this.setTitle("Edit " + event.getEventTypeAsString() + " Event");
		this.setHeaderText(null);
		this.setGraphic(null);

		this.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

		Parent root;

		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/EditEventDialogView.fxml"));
			loader.setController(new EditEventDialogController());
			root = loader.load();
			this.getDialogPane().setContent(root);
		} catch (IOException e) {
			e.printStackTrace();
		}

		this.setResultConverter(dialogButton -> {
			if (dialogButton == ButtonType.OK) {
				School newHost = selectedSchool.get();

				if (event != null && newHost != null && event.getHost().getId() != newHost.getId()) {
					tournament.changeEventHost(event, newHost);
				}
				return true;
			}

			return false;
		});
	}

	private class EditEventDialogController implements Initializable {
		@FXML
		private ComboBox<School> curEventHost;

		@Override
		public void initialize(URL arg0, ResourceBundle arg1) {
			curEventHost.getItems().addAll(event.getWillingHostSchools().getData().values());
			curEventHost.getSelectionModel().select(event.getHost());
			
			selectedSchool = curEventHost.getSelectionModel().selectedItemProperty();
		}
	}
}
