package nz.ac.vuw.ecs.swen225.gp22.recorder;

import java.util.TimerTask;

import nz.ac.vuw.ecs.swen225.gp22.app.pingTimer;

public class ReplayTimer extends pingTimer{
    
    private int apPingRate = 200; // The autoplay ping rate

    //The timerTask that will run ping() each time the timer triggers
    private final TimerTask t = new TimerTask() {
        public void run() {
            ping();
        }
    };

    public ReplayTimer(String level){
        super(level);
        this.scheduleAtFixedRate(t, 0, (long) apPingRate);
    }
    public ReplayTimer(pingTimer p){
        super(p);
        this.scheduleAtFixedRate(t, 0, (long) apPingRate);
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
