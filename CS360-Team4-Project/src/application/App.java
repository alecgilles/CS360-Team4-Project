package application;

import com.lynden.gmapsfx.GoogleMapView;
import com.lynden.gmapsfx.MapComponentInitializedListener;
import com.lynden.gmapsfx.javascript.object.GoogleMap;
import com.lynden.gmapsfx.javascript.object.LatLong;
import com.lynden.gmapsfx.javascript.object.MapOptions;
import com.lynden.gmapsfx.javascript.object.MapTypeIdEnum;
import events.Event;
import java.util.Observable;
import java.util.Observer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.stage.Screen;
import javafx.stage.Stage;
import readers.RegionalReader;
import readers.SchoolReader;
import readers.SectionalReader;
import readers.SemistateReader;
import tables.EventTable;
import tables.SchoolTable;

public class App extends Application implements Observer {
	private static final String APPLICATION_TITLE = "Tournament Workshop";
	private static final double MINIMUM_WIDTH = 800.0;
	private static final double MINIMUM_HEIGHT = 600.0;
	private static final String[] EVENT_LEVELS = { "Semi-State Events", "Regional Events", "Sectional Events" };
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
		 * EventTable sectionals = new EventTable(); sectionals =
		 * allEvents.getSectionals(); EventTable regionals = new EventTable(); regionals
		 * = allEvents.getRegionals(); EventTable semistates = new EventTable();
		 * semistates = allEvents.getSemiStates();
		 * 
		 * for (int i = 0; i < sectionals.size(); i++){
		 * System.out.println(sectionals.getByIndex(i).toString()); }
		 * 
		 * for (int i = 0; i < regionals.size(); i++){
		 * System.out.println(regionals.getByIndex(i).toString()); }
		 * 
		 * for (int i = 0; i < semistates.size(); i++){
		 * System.out.println(semistates.getByIndex(i).toString()); }
		 */
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/MainView.fxml"));
		controller = new MainController();
		loader.setController(controller);

		Parent root = loader.load();
		Scene scene = new Scene(root);

		// Workaround that keeps Application from flashing during initial load.
		Screen screen = Screen.getPrimary();
		Rectangle2D bounds = screen.getBounds();
		primaryStage.setWidth(bounds.getWidth());
		primaryStage.setHeight(bounds.getHeight());

		primaryStage.setTitle(APPLICATION_TITLE);
		primaryStage.setScene(scene);
		primaryStage.setMinWidth(MINIMUM_WIDTH);
		primaryStage.setMinHeight(MINIMUM_HEIGHT);
		primaryStage.setMaximized(true);
		primaryStage.show();
	}

	public class MainController implements MapComponentInitializedListener {

		@FXML
		private ComboBox<String> levelSelectCombo;

		@FXML
		private ListView<Event> tierEventList;
		
		@FXML
		private GoogleMapView mapView;
		
		private GoogleMap map;

		public void initialize() {
			levelSelectCombo.getItems().setAll(EVENT_LEVELS);
			levelSelectCombo.getSelectionModel().select(0);

			tierEventList.setCellFactory(lv -> new EventCell(lv));
			
			mapView.addMapInializedListener(this);
		}
		
		@Override
		public void mapInitialized() {
			MapOptions mapOptions = new MapOptions();

			mapOptions.center(new LatLong(39.774, -86.155))
            .mapType(MapTypeIdEnum.ROADMAP)
            .streetViewControl(false)
            .zoom(8);
			
			map = mapView.createMap(mapOptions);
			
			// levelSelectCombo is disabled by default to prevent user interaction before the map is initialized.
			levelSelectCombo.setDisable(false);
			onLevelSelect(null);
		}

		@FXML
		protected void onCloseButton(ActionEvent event) {
			Platform.exit();
		}

		@FXML
		protected void onLevelSelect(ActionEvent e) {
			EventTable hostSchools = null;
			int currentTier = levelSelectCombo.getSelectionModel().getSelectedIndex();

			switch (currentTier) {
			case 0:
				hostSchools = allEvents.getSemiStates();
				break;
			case 1:
				hostSchools = allEvents.getRegionals();
				break;
			case 2:
				hostSchools = allEvents.getSectionals();
				break;
			default:
				hostSchools = null;
			}

			tierEventList.getItems().clear();
			tierEventList.getItems().addAll(hostSchools.getData().values());
			
			/*
			MarkerOptions markerOptions = new MarkerOptions();
			
			markerOptions.position(new LatLong(39.774, -86.155));
			Marker testMarker = new Marker(markerOptions);
			map.addUIEventHandler(testMarker, UIEventType.click, (marker) -> {
				System.out.println(marker);
			});
			map.addMarker(testMarker);
			*/
		}
	}
}