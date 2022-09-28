package nz.ac.vuw.ecs.swen225.gp22.app;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import nz.ac.vuw.ecs.swen225.gp22.domain.Direction;
import nz.ac.vuw.ecs.swen225.gp22.domain.Domain;
import nz.ac.vuw.ecs.swen225.gp22.persistency.Parser;
import nz.ac.vuw.ecs.swen225.gp22.recorder.Recorder;

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
  static String currentLevel;

  static int keysCollected = 0; //number of keys collected
  static int treasuresLeft = 5; //number of treasures still needing collecting
  static pingTimer timer;

  public UserListener() {
    currentLevel = fileLevel.getStartingFileName();
    System.out.println("starting file name is " + currentLevel);
    timer = new pingTimer();
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

  public void ctrlCommands(KeyEvent e) {
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
  public static void exitGame() {}

  /**exit the game, saves the game state, game will resume next time the
application will be started
   */
  public static void saveGame() {
    //try {
    //Recorder.save();  //TODO
    //} catch (IOException e) {
    //  System.out.println("Saving the Recording threw an IOException " + e);
    //}
    fileLevel.saveStartingFileName(currentLevel);
    exitGame();
  }

  /**resume a saved game -- this will pop up a file selector to select a saved game
to be loaded
 */
  public static void loadSavedGame() {
    try {
      currentLevel = fileLevel.getLevelFilename();
      loadLevel();
    } catch (Exception e) {
      System.out.println("Level loading failed");
    }
  }

  /**Pauses game, displays a "Game is paused" dialog */
  public static void pauseGame() {
    System.out.println("The game is paused");
    paused = true;
    timer.cancel();
  }

  /**Removed "Game is paused" dialog, resumes game */
  public static void resumeGame() {
    System.out.println("The game has resumed");
    paused = false;
    timer = new pingTimer();
  }

  /**Starts the level of the game based on currentLevel string*/
  public static void loadLevel() {
    Recorder.newLevel();
    currentGame = Parser.loadLevel(currentLevel);
    loadTimer();
  }

  /**
   * Creates the timer for a level
   */
  public static void loadTimer() {
    timer.cancel();
    timer = new pingTimer();
  }

  /**Move Chap in a direction */
  public void up() {
    move = Direction.UP;
  }

  public void down() {
    move = Direction.DOWN;
  }

  public void left() {
    move = Direction.LEFT;
  }

  public void right() {
    move = Direction.RIGHT;
  }
}
