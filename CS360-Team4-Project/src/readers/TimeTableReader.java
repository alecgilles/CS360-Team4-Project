package readers;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;

import tables.TimeTable;

/**
 * @author Cooper
 *
 */
public class TimeTableReader {

	/**
	 * Read in drive times from a file and add them to an array
	 * 
	 * @param fileIn filename of the file containing the drive times
	 */
	public TimeTable readFile(String fileIn, TimeTable driveTimes) {
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
			while (token.hasMoreTokens()){
				numSchools++;
				t = token.nextToken();
			}
			
			times = new double[numSchools][numSchools];

			for(int i = 0; i < numSchools; i++) {
				inputLine = br.readLine();
				token = new StringTokenizer(inputLine, ",");
				t = token.nextToken();
				for(int j = 0; j < numSchools; j++) {
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
	}// end readFile //

}