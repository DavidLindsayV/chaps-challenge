package nz.ac.vuw.ecs.swen225.gp22.recorder;

import java.awt.event.*;
import java.util.List;

import javax.swing.JOptionPane;

import nz.ac.vuw.ecs.swen225.gp22.domain.Direction;
import nz.ac.vuw.ecs.swen225.gp22.domain.Domain;
import nz.ac.vuw.ecs.swen225.gp22.persistency.Parser;
import org.dom4j.DocumentException;
import nz.ac.vuw.ecs.swen225.gp22.app.pingTimer;

/**
 * This class listens and reacts to keypresses of the user
 */
public class ReplayListener implements KeyListener {

  //Stores the Domain of the current level
  public static Domain currentGame;
  public static Direction move;
  public static boolean paused = false;
  public static String currentLevel;

  public static int displayTime;

  //The timer for when the game is in autoPlay
  private static ReplayTimer timer;

  public static boolean isAutoPlay = false;

  //The current index for the move we are on
  private static int index;
  //The list of moves
  private static List<Direction> moves;

  public ReplayListener() {
    isAutoPlay = false;
    moves = Recorder.load();
    index = 0;
    move = moves.get(index);
    currentLevel = Recorder.getLevel();

    displayTime =  60 * 1000 * pingTimer.getLevelNum(currentLevel);

    timer = new ReplayTimer(currentLevel);
    System.out.println("REPLAY LISTENER: starting file name is " + currentLevel);
    System.out.println("REPLAY LISTENER: Loading level...");
    loadLevel();
    System.out.println("REPLAY LISTENER: Loaded level.");
  }

  @Override
  public void keyTyped(KeyEvent e) {}

  @Override
  public void keyPressed(KeyEvent e) { }

  @Override
  public void keyReleased(KeyEvent e) {
    switch (e.getKeyCode()) {
      case KeyEvent.VK_SPACE:
        pauseGame();
        break;
      case KeyEvent.VK_ESCAPE:
        resumeGame();
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

  /**
   * Pauses game, displays a "Game is paused" dialog
   */
  public static void pauseGame() {
    System.out.println("The game is paused");
    paused = true;
    timer.cancel();
    JOptionPane.showMessageDialog(ReplayGui.instance, "The replay is Paused");
  }

  /**
   * Removed "Game is paused" dialog, resumes game 
   */
  public static void resumeGame() {
    System.out.println("The game has resumed");
    paused = false;
    timer = new ReplayTimer(timer);
    JOptionPane.getRootFrame().dispose();
  }

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
    loadTimer();
  }

  /**
   * Creates the timer for a level
   */
  private static void loadTimer() {
    System.out.println("BREAKPOINT: Ping timer is loaded.");
    timer.cancel();
    timer = new ReplayTimer(currentLevel);
  }

  /**
   * Go to the next move
   */
  public static void nextMove() {
    if(index<moves.size()-1){
      currentGame.moveActors();
      move = moves.get(index);
      currentGame.movePlayer(move);
      System.out.println("Tick number: "+index+" Move: "+move.name());
      index++;
      displayTime -= 200;
    }else {
      System.out.println("The replay is over!");
      paused = true;
      timer.cancel();
      MainRecorder.gui.endOfReplay();
    }; 
    if (MainRecorder.gui != null) {
      MainRecorder.gui.panel.revalidate();
      MainRecorder.gui.panel.repaint();
    }   
  }


  public static void setAutoPlay(){
    isAutoPlay = true;
    if (MainRecorder.gui != null) {
      MainRecorder.gui.panel.revalidate();
      MainRecorder.gui.panel.repaint();
    }
  }

  public static void setStepByStep(){
    isAutoPlay = false;
    if (MainRecorder.gui != null) {
      MainRecorder.gui.panel.revalidate();
      MainRecorder.gui.panel.repaint();
    }
  }

  public static void changeTimerSpeed(int speed){
    timer.changeSpeed(speed);
  }

}
