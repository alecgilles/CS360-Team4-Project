/**
 * Cooper
 * CS360-Team4-Project
 * Apr 13, 2018
 */
package readers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;

import application.School;
import application.Tournament;
import events.Regional;
import events.Sectional;
import events.SemiState;
import tables.EventTable;
import tables.SchoolTable;
import tables.TimeTable;

/**
 * @author Cooper
 *
 */
public class TournamentReader {

	public Tournament tournamentRead(String name, SchoolTable allSchools, EventTable allEvents, TimeTable driveTimes)
			throws IOException {
		// create standard path and tournament
		Tournament tournament = Tournament.getTournament();
		String path = "resources/data/";

		// read in timetable before entering data>name
		driveTimes = driveTimeRead(path + "DriveTimesTable.csv", driveTimes);
		tournament.setDriveTimes(driveTimes);

		// checks if name is empty string (standard load)
		if (name != "") {
			path = path + name + "/";
		}

		// check for name as dir in data
		File checkDir = new File(path);
		if (checkDir.exists() && checkDir.isDirectory()) {

			// reads SchoolTable before EventTable
			allSchools = schoolRead(path + "Schools.csv", allSchools);
			tournament.setSchools(allSchools);

			// check in data>name for Sec, Reg, Semi csv files
			allEvents = sectionalRead(path + "Sectionals.csv", allSchools, allEvents);
			allEvents = regionalRead(path + "Regionals.csv", allSchools, allEvents);
			allEvents = semiStateRead(path + "SemiStates.csv", allSchools, allEvents);
			tournament.setEvents(allEvents);

		} else {
			System.out.println("No directory " + checkDir.getName());
		}
		System.out.println("Read : " + name);
		// return tournament object
		return tournament;

	}// end tournamentRead

	/**
	 * looks in resources>data and returns a list of directories found
	 * 
	 * @return tournamentList list of tournaments found in data
	 */
	public ArrayList<String> findTournaments() {
		String dataPath = "resources/data/";
		ArrayList<String> tournamentList = new ArrayList<String>();
		File[] list = new File(dataPath).listFiles();

		for (int i = 0; i < list.length; i++) {
			if (list[i].isDirectory()) {
				tournamentList.add(list[i].toString());
			}
		}
		return tournamentList;
	}

	/**
	 * Read in schools from a file and add them to a list of schools in the
	 * tournament
	 * 
	 * @param fileIn
	 *            filename of the file containing the sectionals
	 * @param allSchool
	 *            SchoolTable containing all schools in the tournament
	 * @return allSchools updated to include all schools in the tournament
	 */
	private SchoolTable schoolRead(String fileIn, SchoolTable allSchools) {
		String inputLine = null;
		try {
			FileReader fr = new FileReader(fileIn);
			BufferedReader br = new BufferedReader(fr);

			StringTokenizer token;
			String t = null;
			String name = null;
			String address = null;
			double lat = -1;
			double lon = -1;
			int id = -1;

			// Gobble up the header line
			inputLine = br.readLine();
			while ((inputLine = br.readLine()) != null) {
				token = new StringTokenizer(inputLine, ",");
				// set School name
				name = token.nextToken();
				t = token.nextToken();
				t = t.trim();
				// set School id
				id = Integer.parseUnsignedInt(t);
				// set School address
				address = token.nextToken() + "," + token.nextToken() + "," + token.nextToken() + ","
						+ token.nextToken();
				t = token.nextToken();
				t = t.trim();
				// set School latitude
				lat = Double.parseDouble(t);
				t = token.nextToken();
				t = t.trim();
				// set School longitude
				lon = Double.parseDouble(t);

				allSchools.add(new School(name, id, address, lat, lon));

			}
			fr.close();
			br.close();

		} catch (IOException e) {
			System.out.println("IOException: " + e);
		}
		return allSchools;
	}// end schoolRead

	/**
	 * Read in sectionals from a file and add them to a list of events
	 * 
	 * @param fileIn
	 *            filename of the file containing the sectionals
	 * @param allSchool
	 *            SchoolTable containing all schools in the tournament
	 * @param allEvents
	 *            EventTable to contain all the events of a tournament
	 * @return allEvents updated to include sectionals
	 */
	private EventTable sectionalRead(String fileIn, SchoolTable allSchools, EventTable allEvents) {
		String inputLine = null;
		try {
			FileReader fr = new FileReader(fileIn);
			BufferedReader br = new BufferedReader(fr);

			StringTokenizer token;
			StringTokenizer schoolTokens;
			String t = null;
			School host = null;
			int id = -1;

			// Gobble up the header line
			inputLine = br.readLine();
			while ((inputLine = br.readLine()) != null) {
				token = new StringTokenizer(inputLine, ",");
				t = token.nextToken();
				t = t.trim();
				// set Sectional host
				host = allSchools.getByKey(Integer.parseUnsignedInt(t));
				t = token.nextToken();
				t = t.trim();
				// set Sectional id
				id = Integer.parseUnsignedInt(t);
				schoolTokens = new StringTokenizer(token.nextToken(), " ");
				SchoolTable schoolsInSectional = new SchoolTable();
				// set Schools in Sectional
				while (schoolTokens.hasMoreTokens()) {
					t = schoolTokens.nextToken();
					t = t.trim();
					int tempSchoolId = Integer.parseUnsignedInt(t);
					schoolsInSectional.add(allSchools.getByKey(tempSchoolId));
				}
				allEvents.add(new Sectional(id, host, schoolsInSectional));
			}
			fr.close();
			br.close();

		} catch (IOException e) {
			System.out.println("IOException: " + e);
		}
		return allEvents;
	}// end sectionalRead

	/**
	 * \ Read in regionals from a file and add them to a list of events
	 * 
	 * @param fileIn
	 *            filename of the file containing the regionals
	 * @param allSchool
	 *            SchoolTable containing all schools in the tournament
	 * @param allEvents
	 *            EventTable containing all sectionals
	 * @return allEvents updated to include regionals
	 */
	private EventTable regionalRead(String fileIn, SchoolTable allSchools, EventTable allEvents) {
		String inputLine = null;
		try {
			FileReader fr = new FileReader(fileIn);
			BufferedReader br = new BufferedReader(fr);

			StringTokenizer token;
			StringTokenizer sectionalTokens;
			String t = null;
			School host = null;
			int id = -1;

			// Gobble up the header line
			inputLine = br.readLine();
			while ((inputLine = br.readLine()) != null) {
				token = new StringTokenizer(inputLine, ",");
				t = token.nextToken();
				t = t.trim();
				// set Regional host
				host = allSchools.getByKey(Integer.parseUnsignedInt(t));
				t = token.nextToken();
				t = t.trim();
				// set Regional id
				id = Integer.parseUnsignedInt(t);
				sectionalTokens = new StringTokenizer(token.nextToken(), " ");
				EventTable sectionalsInRegional = new EventTable();
				// set Sectional within a Regional
				while (sectionalTokens.hasMoreTokens()) {
					t = sectionalTokens.nextToken();
					t = t.trim();
					int tempSectionalId = Integer.parseUnsignedInt(t);
					sectionalsInRegional.add(allEvents.getByKey(tempSectionalId));
				}
				allEvents.add(new Regional(id, host, sectionalsInRegional));
			}
			fr.close();
			br.close();

		} catch (IOException e) {
			System.out.println("IOException: " + e);
		}
		return allEvents;
	}// end regionalRead

	/**
	 * Read in semi-states from a file and add them to a list of events
	 * 
	 * @param fileIn
	 *            filename of the file containing the semi-states
	 * @param allSchool
	 *            SchoolTable containing all schools in the tournament
	 * @param allEvents
	 *            EventTable containing all sectionals and regionals
	 * @return allEvents updated to include semi-states
	 */
	private EventTable semiStateRead(String fileIn, SchoolTable allSchools, EventTable allEvents) {
		String inputLine = null;
		try {
			FileReader fr = new FileReader(fileIn);
			BufferedReader br = new BufferedReader(fr);

			StringTokenizer token;
			StringTokenizer regionalTokens;
			String t = null;
			School host = null;
			int id = -1;

			// Gobble up the header line
			inputLine = br.readLine();
			while ((inputLine = br.readLine()) != null) {
				token = new StringTokenizer(inputLine, ",");
				t = token.nextToken();
				t = t.trim();
				// set SemiState host
				host = allSchools.getByKey(Integer.parseUnsignedInt(t));
				t = token.nextToken();
				t = t.trim();
				// set SemiState id
				id = Integer.parseUnsignedInt(t);
				regionalTokens = new StringTokenizer(token.nextToken(), " ");
				EventTable regionalsInSemistate = new EventTable();
				// set Regionals in SemiState
				while (regionalTokens.hasMoreTokens()) {
					t = regionalTokens.nextToken();
					t = t.trim();
					int tempRegionalId = Integer.parseUnsignedInt(t);
					regionalsInSemistate.add(allEvents.getByKey(tempRegionalId));
				}
				allEvents.add(new SemiState(id, host, regionalsInSemistate));
			}
			fr.close();
			br.close();

		} catch (IOException e) {
			System.out.println("IOException: " + e);
		}
		return allEvents;
	}// end semiStateRead

	/**
	 * Read in drive times from a file and add them to an array
	 * 
	 * @param fileIn
	 *            filename of the file containing the semi-states
	 * @param driveTimes
	 *            driveTable to contain all drive times of a tournament
	 * @return driveTable updated to inclue drive times
	 */
	private TimeTable driveTimeRead(String fileIn, TimeTable driveTimes) {
		String inputLine = null;
		try {
			FileReader fr = new FileReader(fileIn);
			BufferedReader br = new BufferedReader(fr);

			StringTokenizer token;
			double[][] times;
			String t = null;
			double time = 0;
			int numSchools = 0;

			// Gobble up the header line
			inputLine = br.readLine();
			token = new StringTokenizer(inputLine, ",");
			t = token.nextToken();
			while (token.hasMoreTokens()) {
				numSchools++;
				t = token.nextToken();
			}

			times = new double[numSchools][numSchools];

			for (int i = 0; i < numSchools; i++) {
				inputLine = br.readLine();
				token = new StringTokenizer(inputLine, ",");
				t = token.nextToken();
				for (int j = 0; j < numSchools; j++) {
					t = token.nextToken();
					t = t.trim();
					time = Double.parseDouble(t);
					times[i][j] = time;
				}
			}
			fr.close();
			br.close();
			driveTimes.setData(times);

			return driveTimes;

		} catch (IOException e) {
			System.out.println("IOException: " + e);
		}
		return null;
	}// end driveTimeRead

}
