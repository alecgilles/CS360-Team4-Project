package readers;

import application.School;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;
import tables.SchoolTable;

/**
 * @author Cooper
 *
 */
public class SchoolReader {

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
	public SchoolTable readFile(String fileIn, SchoolTable allSchools) {
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
				name = token.nextToken();
				t = token.nextToken();
				t = t.trim();
				id = Integer.parseUnsignedInt(t);
				address = token.nextToken() + "," + token.nextToken() + "," + token.nextToken() +","+ token.nextToken();
				t = token.nextToken();
				t = t.trim();
				lat = Double.parseDouble(t);
				t = token.nextToken();
				t = t.trim();
				lon = Double.parseDouble(t);

				allSchools.add(new School(name, id, address, lat, lon));

			}
			fr.close();
			br.close();

		} catch (IOException e) {
			System.out.println("IOException: " + e);
		}
		return allSchools;
	}// end readFile //

}