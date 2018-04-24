package application;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Dialog;

public class EditSchoolDialog extends Dialog<Boolean> {
	private boolean isWillingToHost;

	public EditSchoolDialog(School school) {
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
				school.setWillingHost(isWillingToHost);
				return true;
			}

			return false;
		});
	}

	private class EditSchoolDialogController {
		@FXML
		CheckBox willingToHost;

		@FXML
		protected void onWillingToHostClick(ActionEvent event) {
			isWillingToHost = willingToHost.isSelected();
		}
	}
}
