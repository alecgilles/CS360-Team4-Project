package cs360.team4.project;

public class Application {

	public static void main(String[] args) {
		SchoolReader sr = new SchoolReader();
		sr.readFile("School_File.csv");
		System.out.println(sr.schoolArr.toString());
	}

}
