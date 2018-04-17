package application;

import java.util.ArrayList;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;

public class OpenTournamentDialog extends Dialog<String> {
	private static final String DIALOG_TITLE = "Open Tournament";
	
	public OpenTournamentDialog(ArrayList<String> options) {
		this.setTitle(DIALOG_TITLE);
		this.setHeaderText(null);
		this.setGraphic(null);
		
		ButtonType openButtonType = new ButtonType("Open", ButtonData.OK_DONE);
		this.getDialogPane().getButtonTypes().addAll(openButtonType, ButtonType.CANCEL);
		
		Node openButton = this.getDialogPane().lookupButton(openButtonType);
		openButton.setDisable(true);
		
		GridPane pane = new GridPane();
		pane.setPadding(new Insets(5,5,5,5));
		
		ListView<String> tournamentList = new ListView<String>();
		tournamentList.getItems().addAll(options);
		pane.add(tournamentList, 0, 0);
		
		this.getDialogPane().setContent(pane);
		
		tournamentList.getSelectionModel().selectedIndexProperty().addListener((event, oldVal, newVal) -> {
			if(newVal != null) {
				openButton.setDisable(false);
			}
		});
		
		this.setResultConverter(dialogButton -> {
			if(dialogButton == openButtonType) {
				return tournamentList.getSelectionModel().getSelectedItem();
			}
			
			return null;
		});
	}
}
