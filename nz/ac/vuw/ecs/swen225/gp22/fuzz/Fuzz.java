package nz.ac.vuw.ecs.swen225.gp22.fuzz;

import java.rmi.RemoteException;
import java.time.Duration;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import javax.swing.SwingUtilities;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.opentest4j.AssertionFailedError;
import nz.ac.vuw.ecs.swen225.gp22.app.*;

import static org.junit.jupiter.api.Assertions.assertTimeout;

public class Fuzz{

    /**
     * Generates movement input for tests
     *
     * @return
     */
    private InputGenerator generateRandomInput(){
        InputGenerator ig = new InputGenerator();
        ig.generateRandom(100000);
        return ig;
    }

    /**
     * Performs a 1 minute random fuzz test (for level 1)
     */
    @Test
    public void test1Random(){
        // create new app, open app, load in level 1
    	GUI g = new GUI();
    	Main.gui = g;
    	UserListener u = (UserListener) g.listener;

        // until level is complete or time runs out keep going
        playGame(g, u);
    }

    /**
     * Performs a 1 minute random fuzz test (for level 2)
     */
    //@Test
    public void test2Random(){
        // create new app, open app, load in level 2
    	GUI g = new GUI();
    	Main.gui = g;
        UserListener u = (UserListener) g.listener;
        u.nextLevel();

    	// until level is complete or time runs out keep going
        playGame(g, u);
    }

    /**
     * Plays the game when given an app (that is currently in a level)
     */
    public void playGame(GUI g, UserListener u){
        InputGenerator ig = generateRandomInput();

        try {
        	// timeout after 60 seconds
	        assertTimeoutPreemptively(Duration.ofSeconds(60), ()->{
	        	while (ig.playNext(u)) {}
	        });
        }

        // if assertion times out then stop running, no errors found, return success
        catch (AssertionFailedError e) {
        	System.out.println("STOPPED RUNNING, TIMEOUT!");
        	ig.finish();
        	return;
        }

        // if exception is caught, stop running and print exception (and where it is?)
	    catch (Exception e) {
	    	System.out.println("EXCEPTION OCCURRED TIMEOUT! " + e);
	    	ig.finish();
	    	assert false;
	    }

        System.out.println("ALL INPUTS CONSUMED");
    }
}
