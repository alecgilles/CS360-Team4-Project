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
		String path = "resources/data/" + name;

		// call xWrite() to return string with raw csv content
		schoolStr = schoolWrite(current.getSchools());
		secStr = sectionalWrite(current.getEvents().getSectionals());
		regStr = regionalWrite(current.getEvents().getRegionals());
		semiStr = semiWrite(current.getEvents().getSemiStates());

		// create a directory named after Tournament in resources>name.csv
		if (makeFiles(path + "/description.txt")) {
			System.out.println("Saved: " + name);
		} else {
			System.out.println("Could not create " + name);
		}
	}

	private boolean makeFiles(String name) throws IOException {
		boolean result = false;
		// dir contains outFile, which contains description.txt
		// outFile is not used other than to create dir
		File outFile = new File(name);
		File dir = outFile.getParentFile();

		// check if folder with current name exists
		if (!dir.exists()) {
			dir.mkdir();
			// System.out.println("Folder created : " + dir.getName());
		} else {
			System.out.println("Folder exists: " + outFile.getParent());
			return false;
		}

		// add csv files to directory
		result = populate(dir, "Sectionals.csv", secStr);
		result = populate(dir, "Regionals.csv", regStr);
		result = populate(dir, "SemiStates.csv", semiStr);
		result = populate(dir, "Schools.csv", schoolStr);

		return result;
	}

	/**
	 * @param parent
	 *            Directory that new file should be in
	 * @param name
	 *            Name of file to be created
	 * @param content
	 *            Data that file should contain
	 * @throws IOException
	 */
	private boolean populate(File parent, String name, String content) throws IOException {
		// check if folder with same name exists
		if (parent.exists()) {
			File t = new File(parent, name);

			// check if file within folder shares the new files name
			if (t.exists()) {
				System.out.println("File exists: " + name);
				return false;

			} else {
				// create new file and write in csv content
				t.createNewFile();
				writer(t, content);
			}
		} else {
			System.out.println("No directory " + parent.getName());
			return false;
		}
		return true;
	}

	/**
	 * @param target
	 *            Given file to write
	 * @param content
	 *            Data to write into target file
	 * @throws IOException
	 */
	private void writer(File target, String content) throws IOException {
		FileWriter fw = new FileWriter(target);
		fw.write(content);
		fw.flush();
		fw.close();
		// System.out.println("File written : " + target.toString());
	}

	/**
	 * @param table
	 *            Table from current tournament object
	 * @return String of formatted csv content
	 */
	private String schoolWrite(SchoolTable table) {

		final String Header = "SchoolName,SchoolID,Address,City,State,Zip,Lat,Lon,WillingHost\n";
		StringBuilder sb = new StringBuilder();
		sb.append(Header);

		// grabs all events in a table
		table.getData().forEach((id, school) -> {
			sb.append(school.getName() + ",");
			sb.append(school.getId() + ",");
			sb.append(school.getAddress() + ",");
			sb.append(school.getLat() + ",");
			sb.append(school.getLon() + ",");
			sb.append(school.getWillingHost());
			sb.append("\n");
		});

		return sb.toString();
	}

	/**
	 * @param table
	 *            Table from current tournament object
	 * @return String of formatted csv content
	 */
	private String sectionalWrite(EventTable table) {

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
	 * @param table
	 *            Table from current tournament object
	 * @return String of formatted csv content
	 */
	private String regionalWrite(EventTable table) {

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
	 * @param table
	 *            Table from current tournament object
	 * @return String of formatted csv content
	 */
	private String semiWrite(EventTable table) {

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