package application;

import java.util.ArrayList;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.GridPane;

public class OpenTournamentDialog extends Dialog<String> {
	private static final String DIALOG_TITLE = "Open Tournament";

	public OpenTournamentDialog(ArrayList<String> options) {
		this.setTitle(DIALOG_TITLE);
		this.setHeaderText(null);
		this.setGraphic(null);

		ButtonType openButtonType = new ButtonType("Open", ButtonData.OK_DONE);
		this.getDialogPane().getButtonTypes().addAll(openButtonType, ButtonType.CANCEL);

		Button openButton = (Button) this.getDialogPane().lookupButton(openButtonType);
		openButton.setDisable(true);

		GridPane pane = new GridPane();
		pane.setPadding(new Insets(5, 5, 5, 5));

		ListView<String> tournamentList = new ListView<String>();
		tournamentList.getItems().addAll(options);
		pane.add(tournamentList, 0, 0);

		this.getDialogPane().setContent(pane);

		tournamentList.getSelectionModel().selectedIndexProperty().addListener((event, oldVal, newVal) -> {
			if (newVal != null) {
				openButton.setDisable(false);
			}
		});

		// Allows the user to double click a tournament to load it
		tournamentList.setOnMouseClicked(mouseEvent -> {
			// This is hacky and bad but the best way to do this since list items are
			// returned as an anonymous inner class that can't be cast to.
			if (mouseEvent.getButton() == MouseButton.PRIMARY && mouseEvent.getClickCount() == 2
					&& !(mouseEvent.getTarget().toString().contains("list-cell")
							&& mouseEvent.getTarget().toString().contains("null"))) {
				openButton.fire();
			}
		});

		this.setResultConverter(dialogButton -> {
			if (dialogButton == openButtonType) {
				return tournamentList.getSelectionModel().getSelectedItem();
			}

			return null;
		});
	}
}
