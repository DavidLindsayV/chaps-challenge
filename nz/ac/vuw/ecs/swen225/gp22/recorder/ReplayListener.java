package nz.ac.vuw.ecs.swen225.gp22.recorder;

import java.awt.event.*;
import java.util.List;
import nz.ac.vuw.ecs.swen225.gp22.app.fileLevel;
import nz.ac.vuw.ecs.swen225.gp22.domain.Direction;
import nz.ac.vuw.ecs.swen225.gp22.domain.Domain;
import nz.ac.vuw.ecs.swen225.gp22.persistency.Parser;
import org.dom4j.DocumentException;

/**
 * This class listens and reacts to keypresses of the user
 */
public class ReplayListener implements KeyListener {

  //Stores the Domain of the current level
  public static Domain currentGame;
  public static Direction move;
  public static boolean paused = false;
  public static String currentLevel;

  public static boolean isAutoPlay = false;

  //The current index for the move we are on
  private static int index;
  //The list of moves
  private static List<Direction> moves;

  public ReplayListener() {
    moves = Recorder.load();
    index = 0;
    move = moves.get(index);

    currentLevel = Recorder.getLevel();

    System.out.println("REPLAY LISTENER: starting file name is " + currentLevel);
    System.out.println("REPLAY LISTENER: Loading level...");
    loadLevel();
    System.out.println("REPLAY LISTENER: Loaded level.");
  }

  @Override
  public void keyTyped(KeyEvent e) {}

  @Override
  public void keyPressed(KeyEvent e) {
    switch (e.getKeyCode()) {
      case KeyEvent.VK_RIGHT:
        nextMove();
        break;
    }
  }

  @Override
  public void keyReleased(KeyEvent e) {
    switch (e.getKeyCode()) {
      case KeyEvent.VK_SPACE:
        // pauseGame();
        break;
      case KeyEvent.VK_ESCAPE:
        // resumeGame();
        break;
    }
  }

  /**
   * Exits the replay GUI
   */
  public static void exitGame() {
    ReplayGui.closeAll();
    System.exit(0);
  }


  // TODO: Pause and play for auto play

  // /**
  //  * Pauses game, displays a "Game is paused" dialog
  //  */
  // public static void pauseGame() {
  //   System.out.println("The game is paused");
  //   paused = true;
  // }

  // /**
  //  * Removed "Game is paused" dialog, resumes game 
  //  */
  // public static void resumeGame() {
  //   System.out.println("The game has resumed");
  //   paused = false;
  // }

  /**
   * Starts the level of the game based on currentLevel string
   */
  public static void loadLevel() {
    try {
      currentGame = Parser.loadLevel(currentLevel);
      System.out.println("REPLAY LISTENER: Parser has parsed a level file!");
    } catch (DocumentException e) {
      System.out.println("Exception loading a level");
      exitGame();
    }
  }

  /**
   * Go to the next move
   */
  public static void nextMove() {
    currentGame.moveActors();
    if(index<moves.size()-1){
      move = moves.get(index);
      currentGame.movePlayer(move);
      System.out.println("Tick number: "+index+" Move: "+move.name());
      index++;
    }else System.out.println("All the moves have been completed"); 
    if (MainRecorder.gui != null) {
      MainRecorder.gui.panel.revalidate();
      MainRecorder.gui.panel.repaint();
    }   
  }


  public static void setAutoPlay(){
    isAutoPlay = true;

    //TODO: FIX FOR FUCK SAKE

    if (MainRecorder.gui != null) {
      MainRecorder.gui.panel.revalidate();
      MainRecorder.gui.panel.repaint();
    }
  }

  public static void setStepByStep(){
    isAutoPlay = false;

    //TODO: PLEASE FOR THE lOVE OF GOD

    if (MainRecorder.gui != null) {
      MainRecorder.gui.panel.revalidate();
      MainRecorder.gui.panel.repaint();
    }
  }

  /**
   * Called when the level is lost 
   */
  public static void loseLevel() {
    System.out.println(
      "The level is lost! Hark, the faithless have risen and the worlds have fallen! Behold the end of days!"
    );
  }
}
