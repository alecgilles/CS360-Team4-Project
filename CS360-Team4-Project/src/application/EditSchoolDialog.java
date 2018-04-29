package application;

import events.Event;
import events.Sectional;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.property.BooleanProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import tables.SchoolTable;

public class EditSchoolDialog extends Dialog<Boolean> {
	private BooleanProperty isWillingToHost;
	private School school;
	private Sectional sectional;
	private Tournament tournament;

	public EditSchoolDialog(School school, Tournament tournament) {
		this.school = school;
		this.tournament = tournament;
		Parent root;

		this.setTitle("Edit " + school.getName());
		this.setHeaderText(null);
		this.setGraphic(null);

		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/EditSchoolDialogView.fxml"));
			loader.setController(new EditSchoolDialogController());
			root = loader.load();
			this.getDialogPane().setContent(root);
		} catch (IOException e) {
			e.printStackTrace();
		}

		this.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

		this.setResultConverter(dialogButton -> {
			if (dialogButton == ButtonType.OK) {
				school.setWillingHost(isWillingToHost.get());
				tournament.switchSchoolToSectional(school, sectional);
				return true;
			}

			return false;
		});
	}

	private class EditSchoolDialogController implements Initializable {
		@FXML
		CheckBox willingToHost;
		@FXML
		ComboBox<Event> curSectional;
		
		@Override
		public void initialize(URL location, ResourceBundle resources) {
			willingToHost.setSelected(school.getWillingHost());
			isWillingToHost = willingToHost.selectedProperty();
			curSectional.getItems().clear();
			curSectional.getItems().addAll(tournament.getEvents().getSectionals().getData().values());
			// find the current sectional and select it as default
			tournament.getEvents().getData().forEach((id, event) -> {
				if (event instanceof Sectional) {
					SchoolTable secSchools = ((Sectional) event).getSchools();
					if (secSchools.getByKey(school.getId()).equals(school)) {
						curSectional.getSelectionModel().select(event);
					}
				}
			});
			sectional = (Sectional) curSectional.getSelectionModel().getSelectedItem();
			//curSectional.getSelectionModel().select();
		}
	}
}
