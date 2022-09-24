package nz.ac.vuw.ecs.swen225.gp22.app;
import java.awt.*;
import java.awt.event.*;
import java.util.HashSet;
import java.util.Set;

/**
 * This class listens and reacts to keypresses of the user
 */
public class UserListener implements KeyListener {

  public static moveType move;

  public static boolean paused = false;
  static int currentLevel = 1;

  static Set<mockKey> keysCollected = new HashSet<mockKey>();
  static int treasuresLeft; //number of treasures still needing collecting
  static pingTimer timer = new pingTimer();

  public UserListener() {}

  public static void run() {
    loadLevel();
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

  /**Loads the level of the game based on currentLevel */
  public static void loadLevel() {
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
    move = moveType.moveUp;
  }

  public void down() {
    move = moveType.moveDown;
  }

  public void left() {
    move = moveType.moveLeft;
  }

  public void right() {
    move = moveType.moveRight;
  }
}
