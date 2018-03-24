package readers;

import java.io.*;
import java.util.*;

import cs360.team4.project.School;
import events.Sectional;
import tables.EventTable;
import tables.SchoolTable;


/**
 * @author Cooper
 *
 */
class SectionalReader {
	
	/**
	 * Read in sectionals from a file and add them to a list of events
	 * @param fileIn filename of the file containing the sectionals
	 * @param allSchool SchoolTable containing all schools in the tournament
	 * @param allEvents EventTable to contain all the events of a tournament
	 * @return allEvents updated to include sectionals
	 */
	public EventTable readFile(String fileIn, SchoolTable allSchools, EventTable allEvents) {
		SchoolTable schoolsInSectional = new SchoolTable();
		String inputLine = null;
		try {
			FileReader fr = new FileReader(fileIn);
			BufferedReader br = new BufferedReader(fr);
			
			StringTokenizer token;
			StringTokenizer schoolTokens;
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
				schoolTokens = new StringTokenizer(token.nextToken());
				while(schoolTokens.hasMoreTokens()){
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
	}// end readFile //
	
}