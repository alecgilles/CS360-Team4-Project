package application;

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
import javafx.scene.control.Dialog;

public class EditSchoolDialog extends Dialog<Boolean> {
	private BooleanProperty isWillingToHost;
	private School school;

	public EditSchoolDialog(School school) {
		this.school = school;
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
				return true;
			}

			return false;
		});
	}

	private class EditSchoolDialogController implements Initializable {
		@FXML
		CheckBox willingToHost;

		@Override
		public void initialize(URL location, ResourceBundle resources) {
			willingToHost.setSelected(school.getWillingHost());
			isWillingToHost = willingToHost.selectedProperty();
		}
	}
}
