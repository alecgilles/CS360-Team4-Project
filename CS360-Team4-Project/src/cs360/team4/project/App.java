package cs360.team4.project;

import tables.*;

import java.util.Observable;
import java.util.Observer;

import events.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import readers.*;

public class App extends Application implements Observer {
	private final String APPLICATION_TITLE = "Tournament Workshop";
	private final String[] EVENT_LEVELS = {"Semi-State Events", "Regional Events", "Sectional Events"};
	private SchoolTable allSchools;
	private EventTable allEvents;

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
		
		Parent root = FXMLLoader.load(getClass().getResource("/view/MainView.fxml"));
		Scene scene = new Scene(root);
		
		primaryStage.setTitle(APPLICATION_TITLE);
		primaryStage.setScene(scene);
		primaryStage.setMaximized(true);
		primaryStage.show();
	}

}
