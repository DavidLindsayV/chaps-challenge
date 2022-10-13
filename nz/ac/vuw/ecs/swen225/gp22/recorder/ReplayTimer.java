package nz.ac.vuw.ecs.swen225.gp22.recorder;

import java.util.Timer;
import java.util.TimerTask;

/**
* The replay timer class, this extends the Timer class. 
* We can change the speed the timer ticks.
*
* @author Kalani Sheridan - ID: 300527652
*/
public class ReplayTimer extends Timer{
    
    /**
     * Field(s)
     */
    private final int pingRate; // The autoplay ping rate
    private boolean firstPing;

    //The timerTask that will run ping() each time the timer triggers
    private final TimerTask t = new TimerTask() {
        public void run() {
            ping();
        }
    };

    /**
     * The timer construcor.
     * 
     * @param speed - The speed we are setting.
     */
    public ReplayTimer(int speed){
        super();
        firstPing = true;
        pingRate = speed;
        this.scheduleAtFixedRate(t, 0, (long) pingRate);
    }


    /**
     * The timer constructor when working off another timer.
     * 
     * @param timer - The previous timer.
     */
    public ReplayTimer(ReplayTimer timer){
        super();
        firstPing = true;
        pingRate = timer.pingRate;
        this.scheduleAtFixedRate(t, 0, (long) pingRate);
    }

    /**
     * Function that runs whenever the timer triggers 
     */
    protected void ping(){
        if (ReplayListener.isAutoPlay 
            && ReplayListener.currentGame != null
            && !firstPing) {
            //Move the enemies in Domain
            ReplayListener.currentGame.moveActors();
            //Move the player
            ReplayListener.nextMove();
        }
        if(firstPing){
            this.firstPing = false;
        }
    }
}
