package cs360.team4.project;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;

import events.Event;
import events.Regional;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import readers.RegionalReader;
import readers.SchoolReader;
import readers.SectionalReader;
import readers.SemistateReader;
import tables.EventTable;
import tables.SchoolTable;

public class App extends Application implements Observer {
	private final String APPLICATION_TITLE = "Tournament Workshop";
	private final String[] EVENT_LEVELS = {"Semi-State Events", "Regional Events", "Sectional Events"};
	private SchoolTable allSchools;
	private EventTable allEvents;
	private MainController controller;

	public static void main(String[] args) {
		launch();
	}

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		allSchools = new SchoolTable();
		allEvents = new EventTable();
		
		SchoolReader schoolReader = new SchoolReader();
		SectionalReader sectionalReader = new SectionalReader();
		RegionalReader regionalReader = new RegionalReader();
		SemistateReader semistateReader = new SemistateReader();

		allSchools = schoolReader.readFile("resources/data/Schools.csv", allSchools);
		allSchools.print();
		
		allEvents = sectionalReader.readFile("resources/data/Sectionals.csv", allSchools, allEvents);
		
		allEvents = regionalReader.readFile("resources/data/Regionals.csv", allSchools, allEvents);
		
		allEvents = semistateReader.readFile("resources/data/SemiStates.csv", allSchools, allEvents);
		/*
		EventTable sectionals = new EventTable();
		sectionals = allEvents.getSectionals();
		EventTable regionals = new EventTable();
		regionals = allEvents.getRegionals();
		EventTable semistates = new EventTable();
		semistates = allEvents.getSemiStates();
		
		for (int i = 0; i < sectionals.size(); i++){
			System.out.println(sectionals.getByIndex(i).toString());
		}

		for (int i = 0; i < regionals.size(); i++){
			System.out.println(regionals.getByIndex(i).toString());
		}

		for (int i = 0; i < semistates.size(); i++){
			System.out.println(semistates.getByIndex(i).toString());
		}
		*/
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/MainView.fxml"));
		controller = new MainController();
		loader.setController(controller);
		
		Parent root = loader.load();
		Scene scene = new Scene(root);
		
		primaryStage.setTitle(APPLICATION_TITLE);
		primaryStage.setScene(scene);
		primaryStage.setMaximized(true);
		primaryStage.show();
	}

	public class MainController {
		
		@FXML
		private ComboBox<String> levelSelectCombo;
		
		@FXML
		private ListView<Event> tierEventList;
		
		public void initialize() {
			levelSelectCombo.getItems().setAll(EVENT_LEVELS);
			levelSelectCombo.getSelectionModel().select(0);
			onLevelSelect(null);
			
			tierEventList.setCellFactory(lv -> new EventCell(tierEventList));
		}
		
		@FXML
		protected void onCloseButton(ActionEvent event) {
			Platform.exit();
		}
		
		@FXML
		protected void onLevelSelect(ActionEvent e) {
			EventTable hostSchools = null;
			int currentTier = levelSelectCombo.getSelectionModel().getSelectedIndex();

			tierEventList.getItems().clear();
			
			switch(currentTier) {
				case 0:
					hostSchools = allEvents.getSemiStates();
					break;
				case 1:
					hostSchools = allEvents.getRegionals();
					break;
				case 2:
					hostSchools = allEvents.getSectionals();
			}
			
			for(int i = 0; i < hostSchools.size(); i++) {
				tierEventList.getItems().add(hostSchools.getByIndex(i));
			}
		}
	}
}
