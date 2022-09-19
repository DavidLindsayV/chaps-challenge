package nz.ac.vuw.ecs.swen225.gp22.recorder;

import java.io.IOException;

import org.dom4j.DocumentException;

/*
 * An executable class which starts the game recorder for debugging
 */
public class MainRecorder {
  public static void main(String[] args) throws IOException, DocumentException {
    MockRecorder mock = new MockRecorder();

    mock.left();
    mock.left();
    mock.left();
    mock.up();
    mock.right();
    mock.right();
    mock.right();
    mock.right();
    mock.down();
    mock.down();
    mock.down();
    mock.down();
    mock.down();
    mock.down();
    mock.right();
    mock.right();
    mock.down();
    mock.right();
    mock.right();
    mock.down();
    mock.right();
    mock.right();
    mock.down();
    mock.left();
    mock.left();
    mock.up();
    mock.up();
    mock.up();
    mock.up();
    mock.up();
    mock.up();
    mock.up();
    mock.saveGame();
    mock.loadGame();

  }
}
