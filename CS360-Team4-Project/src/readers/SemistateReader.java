package readers;

import application.School;
import events.SemiState;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;
import tables.EventTable;
import tables.SchoolTable;

/**
 * @author Cooper
 *
 */
public class SemistateReader {

	/**
	 * Read in semi-states from a file and add them to a list of events
	 * 
	 * @param fileIn filename of the file containing the semi-states
	 * @param allSchool SchoolTable containing all schools in the tournament
	 * @param allEvents EventTable containing all sectionals and regionals
	 * @return allEvents updated to include semi-states
	 */
	public EventTable readFile(String fileIn, SchoolTable allSchools, EventTable allEvents) {
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
				host = allSchools.getByKey(Integer.parseUnsignedInt(t));
				t = token.nextToken();
				t = t.trim();
				id = Integer.parseUnsignedInt(t);
				regionalTokens = new StringTokenizer(token.nextToken(), " ");
				EventTable regionalsInSemistate = new EventTable();
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
	}// end readFile //

}