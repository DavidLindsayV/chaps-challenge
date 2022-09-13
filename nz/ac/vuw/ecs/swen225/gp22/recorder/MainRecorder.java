package nz.ac.vuw.ecs.swen225.gp22.recorder;

import java.io.IOException;

/**
 * An executable class which starts the game recorder for debugging
 */
public class MainRecorder {
  public static void main(String[] args) throws IOException {
    Recorder.save();
  }
}
