package nz.ac.vuw.ecs.swen225.gp22.app;

import java.awt.event.*;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.net.MalformedURLException;
import javax.swing.SwingUtilities;
import nz.ac.vuw.ecs.swen225.gp22.domain.Direction;
import nz.ac.vuw.ecs.swen225.gp22.domain.Domain;
import nz.ac.vuw.ecs.swen225.gp22.persistency.Parser;
import nz.ac.vuw.ecs.swen225.gp22.recorder.Recorder;
import nz.ac.vuw.ecs.swen225.gp22.renderer.BoardPanel;
import nz.ac.vuw.ecs.swen225.gp22.renderer.Sounds.SoundEffects;
import org.dom4j.DocumentException;

/**
 * This class listens and reacts to keypresses of the user
 *
 * @author David Lindsay
 */
public class UserListener implements KeyListener {

  // Stores the Domain of the current level
  public static Domain currentGame;
  // The direction the player will move this ping
  private static Direction move;
  // Whether the game is paused
  private static boolean paused = false;
  // The current level being played
  private static String currentLevel;
  // The timer that calls ping
  private static pingTimer timer;
  // Time remaing when starting the level
  public static int timeRemaining;

  /**
   * Constructor for UserListener
   */
  public UserListener() {
    move = Direction.NONE;
    currentLevel = fileLevel.getStartingFileName();

    // Sets up new recorder
    Recorder.setUp(currentLevel);

    // Create new timer and load level
    timer = new pingTimer(currentLevel);
    loadLevel();
  }

  /*
   * (non-Javadoc)
   *
   * @see java.awt.event.KeyListener#keyTyped(java.awt.event.KeyEvent)
   */
  @Override
  public void keyTyped(KeyEvent e) {}

  /*
   * (non-Javadoc)
   *
   * @see java.awt.event.KeyListener#keyPressed(java.awt.event.KeyEvent)
   */
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
        BoardPanel.chapDirection = true;
        left();
        break;
      case KeyEvent.VK_RIGHT:
        BoardPanel.chapDirection = false;
        right();
        break;
      case KeyEvent.VK_F:
        SoundEffects.playSound("Test");
        break;
    }
  }

  /*
   * (non-Javadoc)
   *
   * @see java.awt.event.KeyListener#keyReleased(java.awt.event.KeyEvent)
   */
  @Override
  public void keyReleased(KeyEvent e) {
    switch (e.getKeyCode()) {
      case KeyEvent.VK_SPACE:
        GUI.instance.pauseGame(true);
        break;
      case KeyEvent.VK_ESCAPE:
        GUI.instance.resumeGame();
        break;
      default:
        ctrlCommands(e);
        break;
    }
  }

  /**
   * Runs the commands accessed by Ctrl (eg ctrl-1, ctrl-2, ctrl-x)
   *
   * @param e
   */
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

  /**
   * Exits the game. The current game state will be lost, the next time the game
   * is started, it will resume from the last unfinished level
   */
  public static void exitGame() {
	if (timer != null) timer.cancel();
    GUI.closeAll();
    SwingUtilities.invokeLater(
      () -> {
        new TitleScreen();
      }
    );
  }

  /**
   * exit the game, saves the game state, game will resume next time the
   * application will be started
   */
  public static void saveGame() {
    MessageBox.showMessage("Saving game", "Saving current game");
    try {
      Parser.saveLevel(currentGame);
    } catch (IOException e) {
      System.out.println("Error occurred while saving game");
    }
    fileLevel.saveStartingFileName(currentLevel);
    exitGame();
  }

  /**
   * resume a saved game -- this will pop up a file selector to select a saved
   * game to be loaded
   */
  public static void loadSavedGame() {
    try {
      //Allow user to select directory of saved level to load
      MessageBox.showMessage("Level choosing", "Choose a saved level to load!");
      String folderURL = fileLevel.getLevelFilename();
      folderURL =
        new File(System.getProperty("user.dir")).getAbsolutePath() +
        folderURL.substring(
          folderURL.indexOf("/nz/ac/vuw/ecs/swen225/gp22/"),
          folderURL.length()
        );
      File f = new File(folderURL);
      //Saved game url
      File[] savedGame = f.listFiles(
        new FilenameFilter() {
          public boolean accept(File dir, String name) {
            System.out.println(name);
            return name.contains("saved_game");
          }
        }
      );
      //Recorded game url
      File[] recordedGame = f.listFiles(
        new FilenameFilter() {
          public boolean accept(File dir, String name) {
            System.out.println(name);
            return name.contains("game_record");
          }
        }
      );
      if(savedGame == null || recordedGame == null || savedGame.length == 0 || recordedGame.length == 0) { throw new IllegalStateException(); }
      String urlRecord = recordedGame[0].toURI().toURL().toString();
      String urlSaved = savedGame[0].toURI().toURL().toString();
      currentLevel =
        urlSaved
          .toString()
          .substring(
            urlSaved.toString().indexOf("levels/") + 7,
            urlSaved.toString().length()
          );

      //This is the actual level, not the saved level state
      String baseLevel;

      if (currentLevel.contains("level1.xml")) {
        baseLevel = "level1.xml";
      } else {
        baseLevel = "level2.xml";
      }

      //TODO: This needs to be here BEFORE my methods, or we need to change how load level works

      loadLevel();
      Recorder.setUp(baseLevel);
      Recorder.loadPartial(urlRecord);
    } catch (MalformedURLException | DocumentException e) {
      loadLevel();
      System.out.println("Level loading failed");
    }
    loadLevel();
    timer.cancel();
    timer = new pingTimer(timeRemaining);
    GUI.instance.resumeGame();
  }

  /** Pauses game, displays a "Game is paused" dialog */
  public static void pauseGame(boolean showMessage) {
    if (!paused) {
      paused = true;
      timer.cancel();
      if (showMessage) {
        MessageBox.showMessage("PAUSED", "The game is Paused");
      }
    }
  }

  /** Removed "Game is paused" dialog, resumes game */
  public static void resumeGame() {
    if (paused) {
      paused = false;
      if (timer != null) {
        timer.cancel();
        timer = new pingTimer(timer);
      }else {
    	  timer = new pingTimer(currentLevel);
      }
      MessageBox.closeMessages();
    }
  }

  /** Starts the level of the game based on currentLevel string */
  public static void loadLevel() {
    Recorder.setUp(currentLevel);
    move = Direction.NONE;
    try {
      currentGame = Parser.loadLevel(currentLevel);
    } catch (DocumentException e) {
      System.out.println("Exception loading a level");
      exitGame();
    }
  }

  /**
   * Creates the timer for a level
   */
  private static void loadTimer() {
    timer.cancel();
    timer = new pingTimer(currentLevel);
  }

  /**
   * Loads a new timer with the time left passed in
   *
   * @param timeLeftToPlay
   */
  public static void loadTimer(int timeLeftToPlay) {
    timer.cancel();
    timer = new pingTimer(timeLeftToPlay);
  }

  /**
   * @return the time left for the ping timer
   */
  public static int getTimeLeft() {
    return timer.timeLeftToPlay();
  }

  /** Move up */
  private void up() {
    move = Direction.UP;
  }

  /** Move down */
  private void down() {
    move = Direction.DOWN;
  }

  /** Move left */
  private void left() {
    move = Direction.LEFT;
  }

  /** Move right */
  private void right() {
    move = Direction.RIGHT;
  }

  /** Called when the level is lost by Domain */
  public static void loseLevel() {
    GUI.redrawJFrame();
    SoundEffects.playSound("Death");
    MessageBox.showMessage(
      "Level Lost",
      "The level is lost! Restarting the level"
    );
    if(pingTimer.getLevelNum(currentLevel) == 1) { currentLevel = "level1.xml"; }else { currentLevel = "level2.xml"; }
    loadLevel();
    loadTimer();
  }
 

  /** Called when the user runs out of time on a level */
  public static void timeOutLevel() {
    GUI.redrawJFrame();
    SoundEffects.playSound("Death");
    MessageBox.showMessage(
      "Timeout",
      "The level is lost! Your time has run out. Restarting the level"
    );
    if(pingTimer.getLevelNum(currentLevel) == 1) { currentLevel = "level1.xml"; }else { currentLevel = "level2.xml"; }
    loadLevel();
    loadTimer();
  }

  /**
   * Loads the next level in the game Since there are only 2 levels, nextLevel
   * just loads level 2 level1 goes to level2, level2 also loads level 2
   */
  public static void nextLevel() {
    Recorder.save("nz/ac/vuw/ecs/swen225/gp22/levels/completed_records/");

    GUI.redrawJFrame();
    MessageBox.showMessage(
      "Level Won!",
      "The level \"" +
      GUI.shortenLevelName(currentLevel) +
      "\" is won! \n Now starting level 2"
    );
    currentLevel = "level2.xml";
    loadLevel();
    loadTimer();
  }

  /**
   * @return the listeners pingTmer
   */
  public static pingTimer timer() {
    return timer;
  }

  /**
   * @return move
   */
  public static Direction move() {
    return move;
  }

  /**
   * Sets the move
   *
   * @param move
   */
  public static void setMove(Direction move) {
    UserListener.move = move;
  }

  /**
   * @return paused
   */
  public static boolean paused() {
    return paused;
  }

  /**
   * @return currentLevel
   */
  public static String currentLevel() {
    return currentLevel;
  }
}
