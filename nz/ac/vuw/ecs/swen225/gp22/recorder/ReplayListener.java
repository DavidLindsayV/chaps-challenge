package nz.ac.vuw.ecs.swen225.gp22.recorder;

import java.awt.event.*;
import java.util.List;

import javax.swing.JOptionPane;

import nz.ac.vuw.ecs.swen225.gp22.domain.Direction;
import nz.ac.vuw.ecs.swen225.gp22.domain.Domain;
import nz.ac.vuw.ecs.swen225.gp22.persistency.Parser;
import org.dom4j.DocumentException;

import nz.ac.vuw.ecs.swen225.gp22.app.GUI;
import nz.ac.vuw.ecs.swen225.gp22.app.pingTimer;

/**
 * This class listens to player input for the replay and does actions based on
 * that. This class is based on the structure of APPS UserListener as it does
 * similar things to it, and the other classes are made based around its
 * structure.
 * 
 * @author Kalani Sheridan - ID: 300527652
 */
public class ReplayListener implements KeyListener {

  /**
   * Public fields.
   */
  public static Domain currentGame;
  public static Direction move;
  public static boolean paused = false;
  public static String currentLevel;
  public static boolean isAutoPlay = false;
  public static int displayTime;

  /**
   * Private fields.
   */
  private static ReplayTimer timer;
  private static int index;
  private static List<Direction> moves;

  /**
   * The contructor of a new ReplayListener.
   */
  public ReplayListener() {
    isAutoPlay = false;
    moves = Recorder.load();

    if (moves.isEmpty()){
      moves = List.of(Direction.NONE);
    }  

    index = 0;
    move = moves.get(index);
    currentLevel = Recorder.getLevel();

    displayTime = 60 * 1000 * pingTimer.getLevelNum(currentLevel);

    timer = new ReplayTimer(200);
    loadLevel();
  }

  /**
   * @see java.awt.event.KeyListener#keyTyped(java.awt.event.KeyEvent)
   */
  @Override
  public void keyTyped(KeyEvent e) {
  }

  /**
   * @see java.awt.event.KeyListener#keyPressed(java.awt.event.KeyEvent)
   */
  @Override
  public void keyPressed(KeyEvent e) {
  }

  /**
   * @see java.awt.event.KeyListener#keyReleased(java.awt.event.KeyEvent)
   */
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
   * Pauses game.
   */
  public static void pauseGame() {
    if (isAutoPlay) {
      paused = true;
      timer.cancel();
      JOptionPane.showMessageDialog(ReplayGui.instance, "The replay is Paused");
    }
  }

  /**
   * Public resumes game for ReplayGui.
   */
  public static void resumeGame(int speed) {
    if (isAutoPlay) {
      paused = false;
      timer = new ReplayTimer(speed);
      JOptionPane.getRootFrame().dispose();
    }
  }

  /**
   * Private resumes game for the key listener.
   */
  private static void resumeGame() {
    paused = false;
    timer = new ReplayTimer(timer);
    JOptionPane.getRootFrame().dispose();
  }

  /**
   * Starts the level of the game based on currentLevel string.
   */
  public static void loadLevel() {
    try {
      currentGame = Parser.loadLevel(currentLevel);
    } catch (DocumentException e) {
      System.out.println("Exception loading a level");
      ReplayGui.closeReplay();
    }
    loadTimer();
  }

  /**
   * Creates the Replay timer for a level
   */
  private static void loadTimer() {
    timer.cancel();
    timer = new ReplayTimer(timer);
  }

  /**
   * Do the next step in the game.
   */
  public static void nextMove() {
    if (GUI.replayGui() != null) {
      if (index < moves.size() - 1) {
        currentGame.moveActors();
        move = moves.get(index);
        currentGame.movePlayer(move);
        index++;
        displayTime -= 200;
        GUI.replayGui().panel.revalidate();
        GUI.replayGui().panel.repaint();
      } else {
        paused = true;
        timer.cancel();
        GUI.replayGui().endOfReplay();
      }
      ;
    }
  }

  /**
   * Sets the ReplayListener to do autoplay.
   */
  public static void setAutoPlay() {
    isAutoPlay = true;
    if (GUI.replayGui() != null) {
      GUI.replayGui().panel.revalidate();
      GUI.replayGui().panel.repaint();
    }
  }

  /**
   * Sets the ReplayListener to do Step-By-Step.
   */
  public static void setStepByStep() {
    isAutoPlay = false;
    if (GUI.replayGui() != null) {
      GUI.replayGui().panel.revalidate();
      GUI.replayGui().panel.repaint();
    }
  }

  /**
   * Change the speed for the timer.
   * 
   * @param speed - The speed of the timer we are changing too.
   */
  public static void changeTimerSpeed(int speed) {
    if (!paused) {
      timer.cancel();
      timer = new ReplayTimer(speed);
    }
  }

  /**
   * Stops the timer for the game.
   */
  public static void stopTimer() {
    timer.cancel();
  }
  
}
