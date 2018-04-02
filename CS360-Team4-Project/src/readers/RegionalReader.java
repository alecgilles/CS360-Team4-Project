package readers;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;

import cs360.team4.project.School;
import events.Regional;
import tables.EventTable;
import tables.SchoolTable;


/**
 * @author Cooper
 *
 */
public class RegionalReader {
	
	/**
	 * Read in regionals from a file and add them to a list of events
	 * @param fileIn filename of the file containing the regionals
	 * @param allSchool SchoolTable containing all schools in the tournament
	 * @param allEvents EventTable containing all sectionals
	 * @return allEvents updated to include regionals
	 */
	public EventTable readFile(String fileIn, SchoolTable allSchools, EventTable allEvents) {
		String inputLine = null;
		try {
			FileReader fr = new FileReader(fileIn);
			BufferedReader br = new BufferedReader(fr);
			
			StringTokenizer token;
			StringTokenizer sectionalTokens;
			String t = null;
			School host = null;
			int id = -1;
			
			//Gobble up the header line
			inputLine = br.readLine();

			while ((inputLine = br.readLine()) != null) {
				token = new StringTokenizer(inputLine, ",");
				t = token.nextToken();
				t = t.trim();
				host = allSchools.getByKey(Integer.parseUnsignedInt(t));
				t = token.nextToken();
				t = t.trim();
				id = Integer.parseUnsignedInt(t);
				sectionalTokens = new StringTokenizer(token.nextToken(), " ");
				EventTable sectionalsInRegional = new EventTable();
				while(sectionalTokens.hasMoreTokens()){
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
	}// end readFile //
	
}