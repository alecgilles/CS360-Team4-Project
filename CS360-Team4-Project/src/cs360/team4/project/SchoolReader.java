package cs360.team4.project;

import java.io.*;
import java.util.*;

/**
 * @author Cooper
 *
 */
class SchoolReader {
	ArrayList<School> schoolArr = new ArrayList<School>();

	
	// give school list and it returns an array of schools
	public ArrayList<School> readFile(String fileIn) {
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
				
				School temp = createSchool(name, id);
				schoolArr.add(temp);
				
			}
			fr.close();
			br.close();
	
		} catch (IOException e) {
			System.out.println("IOException: " + e);
		}
		return schoolArr;
	}// end readFile //
	
	
	public School createSchool(String tName, int tId){
		School tS = new School();
		tS.name = tName;
		tS.id = tId;
		return tS;
	} 
}