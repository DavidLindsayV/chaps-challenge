package nz.ac.vuw.ecs.swen225.gp22.persistency;

import java.io.IOException;

import nz.ac.vuw.ecs.swen225.gp22.app.UserListener;
import nz.ac.vuw.ecs.swen225.gp22.domain.Direction;
import nz.ac.vuw.ecs.swen225.gp22.domain.Domain;
import nz.ac.vuw.ecs.swen225.gp22.recorder.MockRecorder;
import nz.ac.vuw.ecs.swen225.gp22.recorder.Recorder;
import org.dom4j.DocumentException;

/**
 * Class to create a mock of the persistency loading and saving files for
 * testing purposes.
 * 
 * Student ID: 3005 30113
 * 
 * @author GeorgiaBarrand
 *
 */
public class MockPersistency {

  /**
   * Runs a simulation of a game.
   */
  public static void run(String filename) {
    try {
      Recorder.setUp(filename);
      for (int i = 0; i < 10; i++) {
        Recorder.tick(MockRecorder.randomEnum(Direction.class));
      }
      UserListener l = new UserListener();
      UserListener.loadTimer(1600);
   
      Domain d = Parser.loadLevel(filename);
      Parser.saveLevel(d);
    } catch (IOException e) {
      e.printStackTrace();
    } catch (DocumentException e) {
      e.printStackTrace();
    }
  }
}
