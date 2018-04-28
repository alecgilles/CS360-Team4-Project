package application;

import java.util.Optional;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import readers.TournamentReader;

public class OpenTournamentDialog extends Dialog<String> {
	private static final String DIALOG_TITLE = "Open Tournament";
	
	private TournamentReader tr = new TournamentReader();
	private ListView<String> tournamentList;

	public OpenTournamentDialog() {
		this.setTitle(DIALOG_TITLE);
		this.setHeaderText(null);
		this.setGraphic(null);

		ButtonType openButtonType = new ButtonType("Open", ButtonData.OK_DONE);
		this.getDialogPane().getButtonTypes().addAll(openButtonType, ButtonType.CANCEL);

		Button openButton = (Button) this.getDialogPane().lookupButton(openButtonType);
		openButton.setDisable(true);

		GridPane pane = new GridPane();
		pane.setPadding(new Insets(5, 5, 5, 5));

		tournamentList = new ListView<String>();
		tournamentList.setCellFactory(lv -> new TournamentCell());
		tournamentList.getItems().addAll(tr.findTournaments());
		pane.add(tournamentList, 0, 0);

		this.getDialogPane().setContent(pane);

		tournamentList.getSelectionModel().selectedItemProperty().addListener((event, oldVal, newVal) -> {
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
	
	private class TournamentCell extends ListCell<String> {

		@Override
		protected void updateItem(String value, boolean isEmpty) {
			super.updateItem(value, isEmpty);
			if (isEmpty) {
				setGraphic(null);
			} else {
				BorderPane itemRoot = new BorderPane();
				
				Text name = new Text(value);
				itemRoot.setLeft(name);
				
				Button deleteButton = new Button("Delete");
				deleteButton.setOnAction( event -> {
					Alert deleteConfirmation = new Alert(AlertType.CONFIRMATION);
					deleteConfirmation.setTitle("Delete "+value+"?");
					deleteConfirmation.setHeaderText("Are you sure you want to delete tournament \""+value+"\"?");
					deleteConfirmation.setContentText("This operation cannot be undone.");
					
					Optional<ButtonType> result = deleteConfirmation.showAndWait();
					
					if(result.get() == ButtonType.OK) {
						tr.deleteTournament(value);
						tournamentList.getItems().remove(value);
					}
				});
				itemRoot.setRight(deleteButton);

				setGraphic(itemRoot);
			}
		}
	}
}
