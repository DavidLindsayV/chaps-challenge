package nz.ac.vuw.ecs.swen225.gp22.fuzz;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import java.awt.Robot;
import java.awt.event.KeyEvent;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.opentest4j.AssertionFailedError;
import nz.ac.vuw.ecs.swen225.gp22.app.*;

/**
 * Fuzz tests using randomly generated input
 *
 * @author Maximus De Leon deleomaxi 300566351
 *
 */
public class Fuzz{
	private static final int ROBOT_DELAY = 10; // delay between robots inputs

    /**
     * Performs a 1 minute random fuzz test (for level 1)
     */
    @Test
    public void test1Random(){
        // create new app, open app, load in level 1
    	new GUI();

        // until level is complete or time runs out keep going
        playGame(KeyEvent.VK_1);
    }

    /**
     * Performs a 1 minute random fuzz test (for level 2)
     */
    @Test
    public void test2Random(){
        // create new app, open app, load in level 2
    	new GUI();

    	// until level is complete or time runs out keep going
        playGame(KeyEvent.VK_2);
    }

    /**
     * Plays the game when given an app, takes a level keycode
     * the game is accessed through the keycode commands given by app
     *
     * @param takes keycode of the level letter (due to app command to swap levels)
     */
    public void playGame(int level){
        InputGenerator ig = new InputGenerator();;
        try {
        	TimeUnit.SECONDS.sleep(2); 	// allow robot and gui to load

        	// timeout after 60 seconds, test succeeds if timeout is successful
	        assertTimeoutPreemptively(Duration.ofSeconds(60), ()->{
	        	Robot bot = new Robot();

	        	// load the level with robot key presses
	        	bot.setAutoDelay(ROBOT_DELAY);
	        	bot.keyPress(KeyEvent.VK_CONTROL);
	        	bot.keyPress(level);
	        	bot.keyRelease(level);
	        	bot.keyRelease(KeyEvent.VK_CONTROL);

	        	// while playing the next move in the sequence
	        	// press enter to get rid of message dialog
	        	while (ig.playNext(bot)) {
	        		bot.keyPress(KeyEvent.VK_ENTER);
		        	bot.keyRelease(KeyEvent.VK_ENTER);
	        	}
	        });
        }

        // if assertion times out then stop running, no errors found, return success
        catch (AssertionFailedError e) {
        	System.out.println("STOPPED RUNNING, TIMEOUT! " + e);

        	// shut everything down so that tests dont overlap
        	ig.finish();
        	GUI.instance.listener.timer().cancel();
        	GUI.instance.closeAll();
        	GUI.instance = null;
        	return;
        }

        // if exception is caught, stop running and print exception (and where it is?)
	    catch (Exception e) {
	    	System.out.println("EXCEPTION OCCURRED TIMEOUT! " + e);

	    	// shut everything down so that tests dont overlap
	    	ig.finish();
	    	GUI.instance.listener.timer().cancel();
	    	GUI.instance.closeAll();
	    	GUI.instance = null;
	    	assert false;
	    	return;
	    }
    }
}
