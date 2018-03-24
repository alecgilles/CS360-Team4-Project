package readers;

import java.io.*;
import java.util.*;

import cs360.team4.project.School;
import tables.SchoolTable;


/**
 * @author Cooper
 *
 */
class SchoolReader {
	
	/**
	 * Read in schools from a file and add them to a list of schools in the tournament
	 * @param fileIn filename of the file containing the sectionals
	 * @param allSchool SchoolTable containing all schools in the tournament
	 * @return allSchools updated to include all schools in the tournament
	 */
	public SchoolTable readFile(String fileIn, SchoolTable allSchools) {
		String inputLine = null;	
		try {
			FileReader fr = new FileReader(fileIn);
			BufferedReader br = new BufferedReader(fr);
			
			StringTokenizer token;
			String t = null;
			String name = null;
			int id = -1;
			

			while ((inputLine = br.readLine()) != null) {
				token = new StringTokenizer(inputLine, ",");
				name = token.nextToken();
				t = token.nextToken();
				t = t.trim();
				id = Integer.parseUnsignedInt(t);
				
				allSchools.add(new School(name, id));
				
			}
			fr.close();
			br.close();
	
		} catch (IOException e) {
			System.out.println("IOException: " + e);
		}
		return allSchools;
	}// end readFile //
	
}