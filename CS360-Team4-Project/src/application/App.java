package application;

import com.lynden.gmapsfx.GoogleMapView;
import com.lynden.gmapsfx.MapComponentInitializedListener;
import com.lynden.gmapsfx.javascript.event.UIEventType;
import com.lynden.gmapsfx.javascript.object.GoogleMap;
import com.lynden.gmapsfx.javascript.object.InfoWindow;
import com.lynden.gmapsfx.javascript.object.LatLong;
import com.lynden.gmapsfx.javascript.object.MapOptions;
import com.lynden.gmapsfx.javascript.object.MapTypeIdEnum;
import com.lynden.gmapsfx.javascript.object.Marker;
import com.lynden.gmapsfx.javascript.object.MarkerOptions;
import com.lynden.gmapsfx.util.MarkerImageFactory;
import events.Event;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;
import java.util.Optional;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.geometry.Side;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;
import netscape.javascript.JSObject;
import readers.TournamentReader;
import tables.EventTable;
import writers.TournamentWriter;

public class App extends Application {
	private static final String APPLICATION_TITLE = "Tournament Workshop";
	private static final double MINIMUM_WIDTH = 800.0;
	private static final double MINIMUM_HEIGHT = 600.0;
	private static final String[] EVENT_LEVELS = { "Semi-State Events", "Regional Events", "Sectional Events" };

	private Stage primaryStage;
	private Tournament tournament;
	private MainController controller;
	private TournamentReader tr;

	public static void main(String[] args) {
		launch();
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		this.primaryStage = primaryStage;

		tournament = Tournament.getTournament();
		// fancy new tournament reader and directory sniffer
		tr = new TournamentReader();

		FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/MainView.fxml"));
		controller = new MainController();
		loader.setController(controller);
		tournament.addObserver(controller);

		Parent root = loader.load();
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("/view/css/stylesheet.css").toExternalForm());

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
		private TabPane mapTablePane;

		@FXML
		private ComboBox<String> levelSelectCombo;

		@FXML
		private ListView<Event> tierEventList;

		@FXML
		private GoogleMapView mapView;

		@FXML
		private Text avgTime;

		@FXML
		private Text maxTime;

		@FXML
		private Label eventHostName;

		@FXML
		private GridPane eventInfoPane;

		@FXML
		private MenuItem saveButton;

		@FXML
		private MenuItem saveAsButton;

		@FXML
		private MenuItem editSchoolsButton;

		private GoogleMap map;
		private Marker selectedMarker;
		private HashMap<Integer, Marker> eventMarkers;
		private ArrayList<Marker> schoolMarkers;
		private InfoWindow openInfoWindow;
		private ContextMenu openContextMenu;

		public void initialize() {
			levelSelectCombo.getItems().setAll(EVENT_LEVELS);
			levelSelectCombo.getSelectionModel().select(0);

			tierEventList.setCellFactory(lv -> new EventCell(lv));

			mapView.addMapInializedListener(this);
		}

		@Override
		public void mapInitialized() {
			MapOptions mapOptions = new MapOptions();

			mapOptions.center(new LatLong(39.874, -86.155)).mapType(MapTypeIdEnum.ROADMAP).streetViewControl(false)
					.zoom(8);
			mapOptions.getJSObject().setMember("fullscreenControl", false);

			try {
				mapView.getWebview().getEngine().executeScript(
						"var mapStyles = [{featureType: \"poi\", elementType: \"labels\", stylers: [{ visibility: \"off\" }]}, {featureType: \"transit\", elementType: \"labels\", stylers: [{ visibility: \"off\" }]}];");
				JSObject mapStyles = (JSObject) mapView.getWebview().getEngine().executeScript("mapStyles");
				mapOptions.getJSObject().setMember("styles", mapStyles);
			} catch (Exception e) {
				// Exception here is possible but will only result in Points of
				// Interest being
				// shown, which is hardly fatal
			}

			map = mapView.createMap(mapOptions);
			eventMarkers = new HashMap<>();
			schoolMarkers = new ArrayList<>();

			tierEventList.getSelectionModel().selectedItemProperty().addListener((event, oldVal, newVal) -> {
				if (newVal != null) {
					onEventClick((EventMarker) eventMarkers.get(newVal.getId()));
				}
			});

			map.addUIEventHandler(UIEventType.click, (e) -> {
				if (selectedMarker != null) {
					selectedMarker.getJSObject().call("setIcon",
							MarkerImageFactory.createMarkerImage("/view/img/school_icon_red.png", "png")
									.replace("(", "").replace(")", ""));
					removeSchoolMarkers();

					if (openInfoWindow != null) {
						openInfoWindow.close();
					}

					if (openContextMenu != null) {
						openContextMenu.hide();
					}

					tierEventList.getSelectionModel().clearSelection();
				}
			});

			mapView.setOnMouseClicked(event -> {
				if (event.getButton() != MouseButton.SECONDARY && openContextMenu != null) {
					openContextMenu.hide();
				}
			});
		}

		@Override
		public void update(Observable object, Object arg) {
			Event currentlySelected = tierEventList.getSelectionModel().getSelectedItem();

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

			eventInfoPane.setVisible(false);

			primaryStage.setTitle(APPLICATION_TITLE + " - " + tournament.getName());

			MarkerOptions markerOptions = new MarkerOptions();

			removeSchoolMarkers();
			removeEventMarkers();
			events.getData().forEach((id, event) -> {
				School host = event.getHost();
				markerOptions.position(new LatLong(host.getLat(), host.getLon()));
				markerOptions.title(host.getName());

				EventMarker marker = new EventMarker(event.getId(), markerOptions);
				map.addMarker(marker);
				map.addUIEventHandler(marker, UIEventType.click, (m) -> {
					Event eventToSelect = tournament.getEvents().getByKey(marker.getEventId());
					tierEventList.getSelectionModel().select(eventToSelect);
					tierEventList.scrollTo(eventToSelect);

					if (openInfoWindow != null) {
						openInfoWindow.close();
					}

					InfoWindow schoolInfo = new InfoWindow();
					schoolInfo.setContent(event.getEventTypeAsString() + ": " + event.getHost().getName()
							+ "<hr>Average Distance: "
							+ tournament.getDriveTimes().calculateAverageEventDriveTime(event) + "<br>\nMax Distance: "
							+ tournament.getDriveTimes().calculateMaxEventDriveTime(event));

					schoolInfo.open(map, marker);
					openInfoWindow = schoolInfo;
				});

				map.addUIEventHandler(marker, UIEventType.rightclick, (m) -> {
					ContextMenu markerContextMenu = new ContextMenu();

					MenuItem editEvent = new MenuItem("Edit Event");
					editEvent.setOnAction(e -> {
						new EditEventDialog(event).showAndWait();
					});

					MenuItem editSchool = new MenuItem("Edit School");
					editSchool.setOnAction(e -> {
						new EditSchoolDialog(event.getHost(), tournament).showAndWait();
					});

					markerContextMenu.getItems().addAll(editEvent, editSchool);

					Point2D scrSpacePos = map
							.fromLatLngToPoint(new LatLong(event.getHost().getLat(), event.getHost().getLon()));

					if (openContextMenu != null) {
						openContextMenu.hide();
					}
					markerContextMenu.show(mapView, Side.LEFT, scrSpacePos.getX(), scrSpacePos.getY());
					openContextMenu = markerContextMenu;
				});

				eventMarkers.put(id, marker);
			});

			if (currentlySelected != null && tierEventList.getItems().contains(currentlySelected)) {
				tierEventList.getSelectionModel().select(currentlySelected);
			}

			eventInfoPane.setVisible(true);
			avgTime.textProperty()
					.set(tournament.getDriveTimes().calculateAverageLevelDriveTime(tournament, currentTier));
			maxTime.textProperty().set(tournament.getDriveTimes().calculateMaxLevelDriveTime(tournament, currentTier));
		}

		private void removeEventMarkers() {
			map.removeMarkers(eventMarkers.values());
			eventMarkers.clear();
		}

		private void removeSchoolMarkers() {
			map.removeMarkers(schoolMarkers);
			schoolMarkers.clear();
		}

		private void onEventClick(EventMarker marker) {
			Event event = tournament.getEvents().getByKey(marker.getEventId());

			if (selectedMarker != null) {
				selectedMarker.getJSObject().call("setIcon", MarkerImageFactory
						.createMarkerImage("/view/img/school_icon_red.png", "png").replace("(", "").replace(")", ""));
			}

			onEventSelected(event);

			marker.getJSObject().call("setIcon", MarkerImageFactory
					.createMarkerImage("/view/img/school_icon_green.png", "png").replace("(", "").replace(")", ""));
			selectedMarker = marker;
		}

		private void onEventSelected(Event event) {
			MarkerOptions markerOptions = new MarkerOptions();
			markerOptions.icon(MarkerImageFactory.createMarkerImage("/view/img/school_icon_small.png", "png")
					.replace("(", "").replace(")", ""));

			removeSchoolMarkers();
			event.getAttendingSchools().forEach((school) -> {
				if (school.getId() != event.getHost().getId()) {
					markerOptions.position(new LatLong(school.getLat(), school.getLon()));
					markerOptions.title(school.getName());
					Marker marker = new Marker(markerOptions);

					map.addUIEventHandler(marker, UIEventType.click, (m) -> {
						if (openInfoWindow != null) {
							openInfoWindow.close();
						}

						InfoWindow schoolInfo = new InfoWindow();
						schoolInfo.setContent(school.getName());

						schoolInfo.open(map, marker);
						openInfoWindow = schoolInfo;
					});

					map.addUIEventHandler(marker, UIEventType.rightclick, (m) -> {
						ContextMenu markerContextMenu = new ContextMenu();

						MenuItem editSchool = new MenuItem("Edit School");
						editSchool.setOnAction(e -> {
							new EditSchoolDialog(school, tournament).showAndWait();
						});

						markerContextMenu.getItems().addAll(editSchool);

						Point2D scrSpacePos = map.fromLatLngToPoint(new LatLong(school.getLat(), school.getLon()));

						if (openContextMenu != null) {
							openContextMenu.hide();
						}
						markerContextMenu.show(mapView, Side.LEFT, scrSpacePos.getX(), scrSpacePos.getY());
						openContextMenu = markerContextMenu;
					});

					map.addMarker(marker);
					schoolMarkers.add(marker);
				}
			});

		}

		private void setInterfaceDisabled(boolean disable) {
			levelSelectCombo.setDisable(disable);
			saveButton.setDisable(disable);
			saveAsButton.setDisable(disable);
			editSchoolsButton.setDisable(disable);
			mapTablePane.setDisable(disable);
		}

		@FXML
		protected void onOpenButton(ActionEvent e) {
			OpenTournamentDialog openDialog = new OpenTournamentDialog();

			Optional<String> tournamentToLoad = openDialog.showAndWait();

			try {
				if (tournamentToLoad.isPresent()) {
					tournament = tr.tournamentRead(tournamentToLoad.get());
					setInterfaceDisabled(false);
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}

		@FXML
		protected void onSaveButton(ActionEvent e) {
			TournamentWriter tw = new TournamentWriter();
			try {
				tw.tournamentWrite(tournament, tournament.getName());
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}

		@FXML
		protected void onSaveAsButton(ActionEvent e) {
			TextInputDialog saveDialog = new TextInputDialog();

			saveDialog.setTitle("Save Tournament");
			saveDialog.setHeaderText(null);
			saveDialog.setGraphic(null);
			saveDialog.setContentText("Tournament Name:");

			Optional<String> tournamentName = saveDialog.showAndWait();

			if (tournamentName.isPresent() && !tournamentName.get().equals("")) {
				TournamentWriter tw = new TournamentWriter();
				try {
					tw.tournamentWrite(tournament, tournamentName.get());
					tournament = tr.tournamentRead(tournamentName.get());
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			}
		}

		@FXML
		protected void onCloseButton(ActionEvent e) {
			Platform.exit();
		}

		@FXML
		protected void onEditSchoolsButton(ActionEvent e) {
			EditSchoolsDialog editSchoolsDialog = new EditSchoolsDialog(tournament.getSchools(), tournament);
			editSchoolsDialog.showAndWait();
		}

		@FXML
		protected void onLevelSelect(ActionEvent e) {
			update(null, null);
		}
	}
}