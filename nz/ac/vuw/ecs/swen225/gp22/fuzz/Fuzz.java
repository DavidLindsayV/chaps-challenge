package nz.ac.vuw.ecs.swen225.gp22.fuzz;

import java.time.Duration;

import javax.swing.JOptionPane;

import java.awt.Robot;
import java.awt.event.KeyEvent;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.opentest4j.AssertionFailedError;
import nz.ac.vuw.ecs.swen225.gp22.app.*;

public class Fuzz{
	private static final int ROBOT_DELAY = 40;

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
    	UserListener u = (UserListener) g.listener;

        // until level is complete or time runs out keep going
        playGame(KeyEvent.VK_1);
    }

    /**
     * Performs a 1 minute random fuzz test (for level 2)
     */
    @Test
    public void test2Random(){
        // create new app, open app, load in level 2
    	GUI g = new GUI();
        UserListener u = (UserListener) g.listener;

    	// until level is complete or time runs out keep going
        playGame(KeyEvent.VK_2);
    }

    /**
     * Plays the game when given an app (that is currently in a level)
     */
    public void playGame(int level){
        InputGenerator ig = generateRandomInput();

        try {
        	// timeout after 60 seconds
	        assertTimeoutPreemptively(Duration.ofSeconds(60), ()->{


	        	Robot bot = new Robot();

	        	// load the level with robot key presses
	        	/*bot.setAutoDelay(200);
	        	bot.keyPress(KeyEvent.VK_CONTROL);
	        	bot.keyPress(level);
	        	bot.keyRelease(level);
	        	bot.keyRelease(KeyEvent.VK_CONTROL);*/

	        	// java robot sucks and wont release the keys
	        	bot.keyPress(KeyEvent.VK_CONTROL);
	        	bot.keyPress(level);
	        	bot.delay(500);
	        	bot.keyRelease(level);
	        	bot.keyRelease(KeyEvent.VK_CONTROL);

	        	bot.setAutoDelay(ROBOT_DELAY);

	        	System.out.println("IT IS TIME");

	        	while (ig.playNext(bot)) {
	        		bot.keyPress(KeyEvent.VK_ENTER);
		        	bot.keyRelease(KeyEvent.VK_ENTER);
	        	}
	        });
        }

        // if assertion times out then stop running, no errors found, return success
        catch (AssertionFailedError e) {
        	System.out.println("STOPPED RUNNING, TIMEOUT!");
        	ig.finish();
        	GUI.instance.closeAll();
        	GUI.instance = null;
        	return;
        }

        // if exception is caught, stop running and print exception (and where it is?)
	    catch (Exception e) {
	    	System.out.println("EXCEPTION OCCURRED TIMEOUT! " + e);
	    	ig.finish();
	    	GUI.instance.closeAll();
	    	GUI.instance = null;
	    	assert false;
	    	return;
	    }


        System.out.println("ALL INPUTS CONSUMED");
    }
}
