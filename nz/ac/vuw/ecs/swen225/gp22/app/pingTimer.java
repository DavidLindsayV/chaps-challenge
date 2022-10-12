package nz.ac.vuw.ecs.swen225.gp22.app;

import java.util.Timer;
import java.util.TimerTask;
import nz.ac.vuw.ecs.swen225.gp22.domain.Direction;
import nz.ac.vuw.ecs.swen225.gp22.recorder.Recorder;

/**
 * This class creates a timer that calls ping() every 200ms
 * And resets the level if the player runs out of time on the current level
 * @author David Lindsay 300584648
 */
public class pingTimer extends Timer {

  private int timeLeftToPlay = 0; //time left to play current level in milliseconds
  private final int pingRate = 200; //the timer will refresh every 200 ms

  //The timerTask that will run ping() each time the timer triggers
  private final TimerTask t = new TimerTask() {
    public void run() {
      ping();
    }
  };

  /** Creates a new pingTimer
   * @param level
   */
  public pingTimer(String level) {
    super();
    timeLeftToPlay = 60 * 1000 * getLevelNum(level);
    this.scheduleAtFixedRate(t, 0, (long) pingRate); //this timer will trigger every half second
  }

  /** Creates a new pingTimer with timeLeftToPlay copied from other pingTimer
   * @param p
   */
  public pingTimer(pingTimer p) {
    super();
    this.timeLeftToPlay = p.timeLeftToPlay;
    this.scheduleAtFixedRate(t, 0, (long) pingRate); //this timer will trigger every half second
  }

  /** Extracts the level number from the level name
   * @param level
   * @return the number of the level (1 or 2)
   */
  public static int getLevelNum(String level) {
    try {
      return Integer.parseInt(
        Character.toString(level.charAt(level.indexOf("level") + 5))
      );
    } catch (NumberFormatException e) {
      System.out.println("Invalid file name format");
    }
    return 1;
  }

  /**
   * Function that runs whenever the timer triggers
   * Redraws the GUI, moves the player, and updates the time left to play
   */
  protected void ping() {
    //Advance the timer
    timeLeftToPlay -= pingRate;
    //If out of time, reload level
    if (timeLeftToPlay == 0) {
      redrawJFrame();
      UserListener.timeOutLevel();
    }
    //Record the move with the Recorder
    Recorder.tick(UserListener.move());
    if (UserListener.currentGame != null) {
      //Move the enemies in Domain
      UserListener.currentGame.moveActors();
      //Move the player
      UserListener.currentGame.movePlayer(UserListener.move());
    }
    UserListener.setMove(Direction.NONE);
    //Repaint the GUI
    redrawJFrame();
  }

  /**
   * Redraws the JFrame of GUI
   */
  private void redrawJFrame() {
    if (GUI.instance != null) {
      GUI.instance.panel.revalidate();
      GUI.instance.panel.repaint();
    }
  }

  /**Getter for timeLeftToPlay
   * @return timeLeftToPlay
   */
  public int timeLeftToPlay() {
    return this.timeLeftToPlay;
  }
}
