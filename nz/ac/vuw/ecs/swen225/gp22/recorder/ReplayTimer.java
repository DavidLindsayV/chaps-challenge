package nz.ac.vuw.ecs.swen225.gp22.recorder;

import java.util.TimerTask;

import nz.ac.vuw.ecs.swen225.gp22.app.pingTimer;

/**
* The replay timer class, this extends the ping timer class as it mostly works the same, 
* but there is a difference, we can change the speed the timer ticks
*
* @author Kalani Sheridan - ID: 300527652
*/
public class ReplayTimer extends pingTimer{
    
    /**
     * Field(s)
     */
    private final int  defaultPingRate = 200; // The autoplay ping rate

    //The timerTask that will run ping() each time the timer triggers
    private final TimerTask t = new TimerTask() {
        public void run() {
            ping();
        }
    };


    /**
     * The timer construcor.
     * 
     * @param level - The level xml we are recording.
     */
    public ReplayTimer(String level){
        super(level);
        this.scheduleAtFixedRate(t, 0, (long) defaultPingRate);
    }


    /**
     * The timer constructor when working off another timer.
     * 
     * @param p - The previous timer.
     */
    public ReplayTimer(pingTimer p){
        super(p);
        this.scheduleAtFixedRate(t, 0, (long) defaultPingRate);
    }


    /**
     * Change the tick rate.
     * 
     * @param speed - The speed we are changing too.
     */
    public void changeSpeed(int speed){
        this.scheduleAtFixedRate(t, 0, (long) speed);
    }

    /**
     * Function that runs whenever the timer triggers 
    */
    @Override 
    protected void ping(){
        if (ReplayListener.isAutoPlay && ReplayListener.currentGame != null) {
            //Move the enemies in Domain
            ReplayListener.currentGame.moveActors();
            //Move the player
            ReplayListener.nextMove();
        }
    }
}
