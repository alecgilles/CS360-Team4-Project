package application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import tables.SchoolTable;

public class EditSchoolsDialog extends Dialog<Boolean> {
	private static final String DIALOG_TITLE = "Edit Schools";

	private SchoolTable schools;
	private Tournament tournament;

	public EditSchoolsDialog(SchoolTable schools, Tournament tournament) {
		Parent root;

		this.schools = schools;
		this.tournament = tournament;

		this.setTitle(DIALOG_TITLE);
		this.setHeaderText(null);
		this.setGraphic(null);

		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/EditSchoolsDialogView.fxml"));
			loader.setController(new EditSchoolsDialogController());
			root = loader.load();
			this.getDialogPane().setContent(root);
		} catch (IOException e) {
			e.printStackTrace();
		}

		this.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

		this.setResultConverter(dialogButton -> {
			if (dialogButton == ButtonType.OK) {
				// Update school table
				return true;
			}

			return false;
		});
	}

	private class EditSchoolsDialogController implements Initializable {
		@FXML
		private ListView<School> schoolList;

		@FXML
		private Button editButton;

		@Override
		public void initialize(URL location, ResourceBundle resources) {
			schoolList.setCellFactory(lv -> new SchoolCell());
			schoolList.getItems().addAll(schools.getData().values());

			schoolList.getSelectionModel().selectedItemProperty().addListener((event, oldVal, newVal) -> {
				if (newVal != null) {
					editButton.setDisable(false);
				}
			});
		}

		@FXML
		protected void onEditButton(ActionEvent e) {
			new EditSchoolDialog(schoolList.getSelectionModel().getSelectedItem(), tournament).showAndWait();
			schoolList.refresh();
		}
	}

	private class SchoolCell extends ListCell<School> {

		@Override
		protected void updateItem(School item, boolean isEmpty) {
			super.updateItem(item, isEmpty);
			if (isEmpty) {
				setGraphic(null);
			} else {
				TextFlow itemDescription = new TextFlow();
				Text txtName = new Text(item.getName());
				txtName.setStyle("-fx-font-weight: bold");
				Text txtWillingToHost = new Text("\nWilling to Host: " + item.getWillingHost());

				itemDescription.getChildren().addAll(txtName, txtWillingToHost);

				setGraphic(itemDescription);
			}
		}
	}
}
