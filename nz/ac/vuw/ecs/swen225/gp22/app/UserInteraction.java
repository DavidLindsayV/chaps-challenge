package nz.ac.vuw.ecs.swen225.gp22.app;

import java.awt.*;
import java.awt.event.*;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.*;

/**
 *
 */
public class UserInteraction implements KeyListener {

  static int currentLevel = 1;
  static int timeLeftToPlay = 0; //time left to play current level in milliseconds
  static Set<mockKey> keysCollected;
  static int treasuresLeft; //number of treasures still needing collecting
  static Timer timer = new Timer();
  static final int pingRate = 500; //the timer will refresh every 500 ms

  public static void run() {
    loadLevel();
  }

  @Override
  public void keyTyped(KeyEvent e) {}

  @Override
  public void keyPressed(KeyEvent e) {
    switch (e.getKeyCode()) {
      case KeyEvent.VK_UP:
        break;
      case KeyEvent.VK_DOWN:
        break;
      case KeyEvent.VK_LEFT:
        break;
      case KeyEvent.VK_RIGHT:
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
      case KeyEvent.VK_UP:
        break;
      case KeyEvent.VK_DOWN:
        break;
      case KeyEvent.VK_LEFT:
        break;
      case KeyEvent.VK_RIGHT:
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
          level1();
          break;
        case KeyEvent.VK_2:
          level1();
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
  public static void saveGame() {}

  /**resume a saved game -- this will pop up a file selector to select a saved game
to be loaded
 */
  public static void loadSavedGame() {}

  /** Starts a game at level 1 */
  public static void level1() {
    currentLevel = 1;
  }

  /** Starts a game at level 2 */
  public static void level2() {
    currentLevel = 2;
  }

  /**Pauses game, displays a "Game is paused" dialog */
  public static void pauseGame() {}

  /**Removed "Game is paused" dialog, resumes game */
  public static void resumeGame() {}

  /**Loads the level of the game based on currentLevel */
  public static void loadLevel() {
    loadTimer();
  }

  /**
   * Creates the timer for a level
   */
  public static void loadTimer() {
    timer.cancel();
    timer = new Timer();
    timeLeftToPlay = 60 * 1000 * currentLevel;
    TimerTask t = new TimerTask() {
      public void run() {
        ping();
      }
    };
    timer.scheduleAtFixedRate(t, 0, (long) pingRate); //this timer will trigger every half second
  }

  /**Function that runs whenever the timer triggers */
  public static void ping() {
    timeLeftToPlay -= pingRate;
    System.out.println(timeLeftToPlay);
    if (timeLeftToPlay == 0) {
      System.out.println("Ran out of time. Restarting level");
      loadLevel();
    }
  }

  /**Move Chap in a direction */
  public void up() {}

  public void down() {}

  public void left() {}

  public void right() {}

  /**Stop moving Chap in a direction */
  public void unUp() {}

  public void unDown() {}

  public void unLeft() {}

  public void unRight() {}
}
