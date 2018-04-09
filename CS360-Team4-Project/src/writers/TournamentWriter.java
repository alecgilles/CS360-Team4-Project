/**
 * Cooper
 * CS360-Team4-Project
 * Apr 4, 2018
 */
package writers;

import application.School;
import application.Tournament;
import events.Regional;
import events.SemiState;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import tables.EventTable;
import tables.SchoolTable;

/**
 * @author Cooper
 *
 */
public class TournamentWriter {

	private String schoolStr = null;
	private String secStr = null;
	private String regStr = null;
	private String semiStr = null;

	public void tournamentWrite(Tournament current, String name) throws IOException {
		String path = "resources\\" + name;

		// call xWrite() to return string with raw csv content
		schoolStr = schoolWrite(current.getSchools());
		secStr = sectionalWrite(current.getEvents().getSectionals());
		regStr = regionalWrite(current.getEvents().getRegionals());
		semiStr = semiWrite(current.getEvents().getSemiStates());

		// create a directory named after Tournament in resources>name.csv
		makeFiles(path + "description.txt");
	}

	public boolean makeFiles(String name) throws IOException {
		// dir contains outFile, which contains description.txt
		// outFile is not used other than to create dir

		File outFile = new File(name);
		File dir = outFile.getParentFile();

		// check if folder with current name exists
		if (!dir.exists()) {
			dir.mkdir();
			System.out.println("Folder created : " + dir.getName());
			// below will create a description.txt for tournament
			// outFile.createNewFile();
			// writer(outFile, outFile.getName());

		} else {
			System.out.println("Folder exists: " + outFile.getParent());
		}

		// add csv files to directory
		populate(dir, "Sectionals.csv", secStr);
		populate(dir, "Regionals.csv", regStr);
		populate(dir, "SemiState.csv", semiStr);
		populate(dir, "Schools.csv", schoolStr);

		System.out.println("Done");
		return true;
	}

	/**
	 * @param parent Directory that new file should be in
	 * @param name Name of file to be created
	 * @param content Data that file should contain
	 * @throws IOException
	 */
	public void populate(File parent, String name, String content) throws IOException {

		// check if folder with same name exists
		if (parent.exists()) {
			File t = new File(parent, name);

			// check if file within folder shares the new files name
			if (t.exists()) {
				System.out.println("File exists: " + name);
			} else {
				// create new file and write in csv content
				t.createNewFile();
				writer(t, content);
			}
		} else {
			System.out.println("No directory " + parent.getName());
		}
	}

	/**
	 * @param target Given file to write
	 * @param content Data to write into target file
	 * @throws IOException
	 */
	public void writer(File target, String content) throws IOException {
		FileWriter fw = new FileWriter(target);
		fw.write(content);
		fw.flush();
		fw.close();
		System.out.println("File written : " + target.toString());
	}

	/**
	 * @param table Table from current tournament object
	 * @return String of formatted csv content
	 */
	public String schoolWrite(SchoolTable table) {

		final String Header = "SchoolName,SchoolID,Address,City,State,Zip,Lat,Lon\n";
		StringBuilder sb = new StringBuilder();
		sb.append(Header);

		// grabs all events in a table
		table.getData().forEach((id, school) -> {
			sb.append(school.getName() + ",");
			sb.append(school.getId() + ",");
			sb.append(school.getAddress() + ",");
			sb.append(school.getLon() + ",");
			sb.append(school.getLat());
			sb.append("\n");
		});

		return sb.toString();
	}

	/**
	 * @param table Table from current tournament object
	 * @return String of formatted csv content
	 */
	public String sectionalWrite(EventTable table) {

		final String Header = "Host,SectionalID,Schools\n";
		StringBuilder sb = new StringBuilder();
		sb.append(Header);
		// grabs all events in a table
		table.getData().forEach((id, event) -> {
			sb.append(event.getHost().getId() + ",");
			sb.append(event.getId() + ",");
			// grab all schools in sectional
			for (School school : event.getAttendingSchools()) {
				sb.append(school.getId() + " ");
			}
			sb.append("\n");
		});

		return sb.toString();
	}

	/**
	 * @param table Table from current tournament object
	 * @return String of formatted csv content
	 */
	public String regionalWrite(EventTable table) {

		final String Header = "Host,RegionalID,Sectionals\n";
		StringBuilder sb = new StringBuilder();
		sb.append(Header);

		// grabs all events in a table
		table.getData().forEach((id, event) -> {
			sb.append(event.getHost().getId() + ",");
			sb.append(event.getId() + ",");
			// grabs all sectional in regional
			((Regional) event).getSectionals().getData().forEach((secId, secEvent) -> {
				sb.append(secId + " ");
			});
			sb.append("\n");
		});
		return sb.toString();
	}

	/**
	 * @param table Table from current tournament object
	 * @return String of formatted csv content
	 */
	public String semiWrite(EventTable table) {

		final String Header = "Host,SemiStatesID,Regionals\n";
		StringBuilder sb = new StringBuilder();
		sb.append(Header);

		// grabs all events in a table
		table.getData().forEach((id, event) -> {
			sb.append(event.getHost().getId() + ",");
			sb.append(event.getId() + ",");
			// grabs all regional in SemiState
			((SemiState) event).getRegionals().getData().forEach((regId, regEvent) -> {
				sb.append(regId + " ");
			});
			sb.append("\n");
		});
		return sb.toString();
	}
}