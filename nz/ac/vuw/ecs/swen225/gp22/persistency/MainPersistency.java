package nz.ac.vuw.ecs.swen225.gp22.persistency;

/**
 * Class to run the persistency mock for testing purposes.
 * 
 * Student ID: 3005 30113
 * 
 * @author GeorgiaBarrand
 *
 */

public class MainPersistency {
	/**
	 * Run a mock of the persistency module for testing purposes.
	 * 
	 * 
	 * @param args arguments passed to main
	 */
	public static void main(String[] args) {
		MockPersistency.run("level1.xml");
	}
}
