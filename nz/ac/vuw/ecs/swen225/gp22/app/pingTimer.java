package nz.ac.vuw.ecs.swen225.gp22.app;

import java.util.Timer;
import java.util.TimerTask;

/**
 * This class creates a timer that calls ping() every 200ms
 * And resets the level if the player runs out of time on the current level
 */
public class pingTimer extends Timer {

  static int timeLeftToPlay = 0; //time left to play current level in milliseconds
  final int pingRate = 200; //the timer will refresh every 200 ms
  final TimerTask t = new TimerTask() {
    public void run() {
      ping();
    }
  };

  public pingTimer() {
    super();
    timeLeftToPlay = 60 * 1000 * UserListener.currentLevel;
    this.scheduleAtFixedRate(t, 0, (long) pingRate); //this timer will trigger every half second
  }

  /**Function that runs whenever the timer triggers */
  public void ping() {
    timeLeftToPlay -= pingRate;
    if (timeLeftToPlay == 0) {
      UserListener.loadLevel();
    }
    mockRecorder.tick(UserListener.move);
    UserListener.move = moveType.None;
  }
}
