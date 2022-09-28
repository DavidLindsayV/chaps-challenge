package nz.ac.vuw.ecs.swen225.gp22.app;

import java.util.Timer;
import java.util.TimerTask;
import nz.ac.vuw.ecs.swen225.gp22.domain.Direction;
import nz.ac.vuw.ecs.swen225.gp22.domain.Direction;
import nz.ac.vuw.ecs.swen225.gp22.recorder.Recorder;

/**
 * This class creates a timer that calls ping() every 200ms
 * And resets the level if the player runs out of time on the current level
 */
public class pingTimer extends Timer {

  public static int timeLeftToPlay = 0; //time left to play current level in milliseconds
  final int pingRate = 200; //the timer will refresh every 200 ms
  final TimerTask t = new TimerTask() {
    public void run() {
      ping();
    }
  };

  public pingTimer() {
    super();
    timeLeftToPlay = 60 * 1000 * getLevelNum(UserListener.currentLevel);
    this.scheduleAtFixedRate(t, 0, (long) pingRate); //this timer will trigger every half second
  }

  public pingTimer(pingTimer p) {
    super();
    this.scheduleAtFixedRate(t, 0, (long) pingRate); //this timer will trigger every half second
  }

  private int getLevelNum(String level) {
    try {
      return Integer.parseInt(
        Character.toString(level.charAt(level.indexOf("level") + 5))
      );
    } catch (NumberFormatException e) {
      System.out.println("Invalid file name format");
    }
    return 1;
  }

  /**Function that runs whenever the timer triggers */
  public void ping() {
    //Advance the timer
    timeLeftToPlay -= pingRate;
    //If out of time, reload level
    if (timeLeftToPlay == 0) {
      UserListener.loadLevel();
    }
    //Record the move with the Recorder
    //Recorder.tick(UserListener.move);
    //Move the enemies in Domain
    UserListener.currentGame.moveActors();
    //Move the player
    UserListener.currentGame.movePlayer(UserListener.move);
    UserListener.move = Direction.NONE;
    //Repaint the GUI
    if (Main.gui != null) {
      Main.gui.panel.revalidate();
      Main.gui.panel.repaint();
    }
  }
}
