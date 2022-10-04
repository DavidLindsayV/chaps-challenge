package nz.ac.vuw.ecs.swen225.gp22.app;

import java.awt.event.*;
import java.io.IOException;
import javax.swing.JOptionPane;
import nz.ac.vuw.ecs.swen225.gp22.domain.Direction;
import nz.ac.vuw.ecs.swen225.gp22.domain.Domain;
import nz.ac.vuw.ecs.swen225.gp22.persistency.Parser;
import nz.ac.vuw.ecs.swen225.gp22.recorder.Recorder;
import org.dom4j.DocumentException;

/**
 * This class listens and reacts to keypresses of the user
 */
public class UserListener implements KeyListener {

  //Stores the Domain of the current level
  public static Domain currentGame;
  //The direction the player will move this ping
  public static Direction move;
  //Whether the game is paused
  public static boolean paused = false;
  //The current level being played
  public static String currentLevel;
  //The timer that calls ping
  private static pingTimer timer;

  public UserListener() {
    //Sets up new recorder
    Recorder.setUp();
    move = Direction.NONE;
    currentLevel = fileLevel.getStartingFileName();
    System.out.println("starting file name is " + currentLevel);
    timer = new pingTimer();
    System.out.println("BREAKPOINT: Loading level...");
    loadLevel();
    System.out.println("BREAKPOINT: Loaded level.");
  }

  @Override
  public void keyTyped(KeyEvent e) {}

  @Override
  public void keyPressed(KeyEvent e) {
    switch (e.getKeyCode()) {
      case KeyEvent.VK_UP:
        up();
        break;
      case KeyEvent.VK_DOWN:
        down();
        break;
      case KeyEvent.VK_LEFT:
        left();
        break;
      case KeyEvent.VK_RIGHT:
        right();
        break;
    }
  }

  @Override
  public void keyReleased(KeyEvent e) {
    switch (e.getKeyCode()) {
      case KeyEvent.VK_SPACE:
        pauseGame();
        break;
      case KeyEvent.VK_ESCAPE:
        resumeGame();
        break;
      default:
        ctrlCommands(e);
        break;
    }
  }

  private void ctrlCommands(KeyEvent e) {
    if (e.isControlDown()) {
      switch (e.getKeyCode()) {
        case KeyEvent.VK_X:
          exitGame();
          break;
        case KeyEvent.VK_S:
          saveGame();
          break;
        case KeyEvent.VK_R:
          loadSavedGame();
          break;
        case KeyEvent.VK_1:
          currentLevel = "level1.xml";
          loadLevel();
          break;
        case KeyEvent.VK_2:
          currentLevel = "level2.xml";
          loadLevel();
          break;
      }
    }
  }

  /**Exits the game. The current game state will be lost,
   * the next time the game is started, it will resume from the last unfinished level
   */
  public static void exitGame() {
    GUI.closeAll();
    System.exit(0);
  }

  /**exit the game, saves the game state, game will resume next time the
application will be started
   */
  public static void saveGame() {
    try {
      Parser.saveLevel(currentGame);
    } catch (IOException e) {
      System.out.println("Error occurred saving game");
    }
    fileLevel.saveStartingFileName(currentLevel);
    exitGame();
  }

  /**resume a saved game -- this will pop up a file selector to select a saved game
to be loaded
 */
  private static void loadSavedGame() {
    try {
      currentLevel = fileLevel.getLevelFilename();
      loadLevel();
    } catch (Exception e) {
      System.out.println("Level loading failed");
    }
  }

  /**Pauses game, displays a "Game is paused" dialog */
  public static void pauseGame() {
    if (!paused) {
      System.out.println("The game is paused");
      paused = true;
      timer.cancel();
      JOptionPane.showMessageDialog(GUI.instance, "The game is Paused");
    }
  }

  /**Removed "Game is paused" dialog, resumes game */
  public static void resumeGame() {
    if (paused) {
      System.out.println("The game has resumed");
      paused = false;
      timer = new pingTimer(timer);
      JOptionPane.getRootFrame().dispose();
    }
  }

  /**Starts the level of the game based on currentLevel string*/
  public static void loadLevel() {
    Recorder.setUp();
    move = Direction.NONE;
    try {
      currentGame = Parser.loadLevel(currentLevel);
      System.out.println("BREAKPOINT: Parser has parsed a level file!");
    } catch (DocumentException e) {
      System.out.println("Exception loading a level");
      exitGame();
    }
    loadTimer();
  }

  /**
   * Creates the timer for a level
   */
  private static void loadTimer() {
    System.out.println("BREAKPOINT: Ping timer is loaded.");
    timer.cancel();
    timer = new pingTimer();
  }

  /**Move Chap in a direction */
  private void up() {
    move = Direction.UP;
  }

  private void down() {
    move = Direction.DOWN;
  }

  private void left() {
    move = Direction.LEFT;
  }

  private void right() {
    move = Direction.RIGHT;
  }

  /**Called when the level is lost by Domain*/
  public static void loseLevel() {
    JOptionPane.showMessageDialog(
      GUI.instance,
      "The level is lost! Restarting the level"
    );
    loadLevel();
  }

  /** Called when the user runs out of time on a level*/
  public static void timeOutLevel() {
    JOptionPane.showMessageDialog(
      GUI.instance,
      "The level is lost! Your time has run out. Restarting the level"
    );
    loadLevel();
  }

  /**Loads the next level in the game */
  public static void nextLevel() {
    JOptionPane.showMessageDialog(
      GUI.instance,
      "The level " + currentLevel + " is won! Now starting level 2"
    );
    currentLevel = "level2.xml";
    loadLevel();
  }
}
