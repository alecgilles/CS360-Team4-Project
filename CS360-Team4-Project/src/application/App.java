package application;

import com.lynden.gmapsfx.GoogleMapView;
import com.lynden.gmapsfx.MapComponentInitializedListener;
import com.lynden.gmapsfx.javascript.event.UIEventType;
import com.lynden.gmapsfx.javascript.object.GoogleMap;
import com.lynden.gmapsfx.javascript.object.LatLong;
import com.lynden.gmapsfx.javascript.object.MapOptions;
import com.lynden.gmapsfx.javascript.object.MapTypeIdEnum;
import com.lynden.gmapsfx.javascript.object.Marker;
import com.lynden.gmapsfx.javascript.object.MarkerOptions;
import events.Event;
import java.util.ArrayList;
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
import readers.TimeTableReader;
import tables.EventTable;
import tables.SchoolTable;
import tables.TimeTable;

public class App extends Application {
	private static final String APPLICATION_TITLE = "Tournament Workshop";
	private static final double MINIMUM_WIDTH = 800.0;
	private static final double MINIMUM_HEIGHT = 600.0;
	private static final String[] EVENT_LEVELS = { "Semi-State Events", "Regional Events", "Sectional Events" };

	private Tournament tournament;
	private MainController controller;

	public static void main(String[] args) {
		launch();
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		tournament = Tournament.getTournament();
		
		SchoolTable allSchools = new SchoolTable();
		EventTable allEvents = new EventTable();
		TimeTable driveTimes = new TimeTable();

		SchoolReader schoolReader = new SchoolReader();
		SectionalReader sectionalReader = new SectionalReader();
		RegionalReader regionalReader = new RegionalReader();
		SemistateReader semistateReader = new SemistateReader();
		TimeTableReader timeTableReader = new TimeTableReader();

		allSchools = schoolReader.readFile("resources/data/Schools.csv", allSchools);
		tournament.setSchools(allSchools);

		allEvents = sectionalReader.readFile("resources/data/Sectionals.csv", allSchools, allEvents);
		allEvents = regionalReader.readFile("resources/data/Regionals.csv", allSchools, allEvents);
		allEvents = semistateReader.readFile("resources/data/SemiStates.csv", allSchools, allEvents);
		tournament.setEvents(allEvents);
		
		driveTimes = timeTableReader.readFile("resources/data/DriveTimesTable.csv", driveTimes);
		tournament.setDriveTimes(driveTimes);

		FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/MainView.fxml"));
		controller = new MainController();
		loader.setController(controller);
		tournament.addObserver(controller);

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

	public class MainController implements MapComponentInitializedListener, Observer {

		@FXML
		private ComboBox<String> levelSelectCombo;

		@FXML
		private ListView<Event> tierEventList;

		@FXML
		private GoogleMapView mapView;

		private GoogleMap map;
		private ArrayList<Marker> mapMarkers;

		public void initialize() {
			levelSelectCombo.getItems().setAll(EVENT_LEVELS);
			levelSelectCombo.getSelectionModel().select(0);

			tierEventList.setCellFactory(lv -> new EventCell(lv));

			mapView.addMapInializedListener(this);
		}

		@Override
		public void mapInitialized() {
			MapOptions mapOptions = new MapOptions();

			mapOptions.center(new LatLong(39.774, -86.155)).mapType(MapTypeIdEnum.ROADMAP).streetViewControl(false)
					.zoom(7);

			map = mapView.createMap(mapOptions);
			mapMarkers = new ArrayList<>();

			// levelSelectCombo is disabled by default to prevent user interaction before
			// the map is initialized.
			levelSelectCombo.setDisable(false);
			onLevelSelect(null);
		}
		
		@Override
		public void update(Observable object, Object arg) {
			EventTable events = null;
			int currentTier = levelSelectCombo.getSelectionModel().getSelectedIndex();

			switch (currentTier) {
			case 0:
				events = tournament.getEvents().getSemiStates();
				break;
			case 1:
				events = tournament.getEvents().getRegionals();
				break;
			case 2:
				events = tournament.getEvents().getSectionals();
				break;
			default:
				events = null;
			}

			tierEventList.getItems().clear();
			tierEventList.getItems().addAll(events.getData().values());

			MarkerOptions markerOptions = new MarkerOptions();
			
			removeMapMarkers();
			events.getData().forEach((id, event) -> {
				School host = event.getHost();
				markerOptions.position(new LatLong(host.getLat(), host.getLon()));
				
				EventMarker marker = new EventMarker(event.getId(), markerOptions);
				map.addMarker(marker);
				map.addUIEventHandler(marker, UIEventType.click, (m) -> {
					onEventClick(tournament.getEvents().getByKey(marker.getEventId()));
				});
				mapMarkers.add(marker);
			});
		}
		
		private void removeMapMarkers() {
			map.removeMarkers(mapMarkers);
			mapMarkers.clear();
		}
		
		private void onEventClick(Event event) {
			System.out.println(event);
		}

		@FXML
		protected void onCloseButton(ActionEvent event) {
			Platform.exit();
		}

		@FXML
		protected void onLevelSelect(ActionEvent e) {
			update(null, null);
		}
	}
}