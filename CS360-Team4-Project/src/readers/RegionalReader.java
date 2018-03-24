package readers;

import java.io.*;
import java.util.*;

import cs360.team4.project.School;
import events.Regional;
import tables.EventTable;
import tables.SchoolTable;


/**
 * @author Cooper
 *
 */
class RegionalReader {
	
	/**
	 * Read in regionals from a file and add them to a list of events
	 * @param fileIn filename of the file containing the regionals
	 * @param allSchool SchoolTable containing all schools in the tournament
	 * @param allEvents EventTable containing all sectionals
	 * @return allEvents updated to include regionals
	 */
	public EventTable readFile(String fileIn, SchoolTable allSchools, EventTable allEvents) {
		EventTable sectionalsInRegional = new EventTable();
		String inputLine = null;
		try {
			FileReader fr = new FileReader(fileIn);
			BufferedReader br = new BufferedReader(fr);
			
			StringTokenizer token;
			StringTokenizer sectionalTokens;
			String t = null;
			School host = null;
			int id = -1;
			

			while ((inputLine = br.readLine()) != null) {
				token = new StringTokenizer(inputLine, ",");
				t = token.nextToken();
				t = t.trim();
				host = allSchools.getByKey(Integer.parseUnsignedInt(t));
				t = token.nextToken();
				t = t.trim();
				id = Integer.parseUnsignedInt(t);
				sectionalTokens = new StringTokenizer(token.nextToken());
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