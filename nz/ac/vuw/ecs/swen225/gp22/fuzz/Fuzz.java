package nz.ac.vuw.ecs.swen225.gp22.fuzz;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

//import static org.junit.jupiter.api.Assertions.*;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.Timeout;
//import static org.junit.jupiter.api.Assertions.assertTimeout;

public class Fuzz{
    
    /**
     * Generates movement input for tests
     * 
     * @return
     */
    private InputGenerator generateRandomInput(){
        InputGenerator ig = new InputGenerator();
        ig.generateRandom(100);
        return ig;
    }

    /**
     * Performs a 1 minute random fuzz test (for level 1), takes the
     * application as a parameter
     * 
     * @param app
     */

    //@Test
    public void test1Random(){
        // open app, load in level 1

        // until level is complete or time runs out keep going (times out and declares success after 60 seconds)
        // assertTimeout(Duration.ofSeconds(60), playGame(/*...*/));
    }

    /**
     * Performs a 1 minute random fuzz test (for level 2), takes the
     * application as a parameter
     * 
     * @param app
     */

    //@Test
    public void test2Random(){
        // open app, load in level 2

        // until level is complete or time runs out keep going (times out and declares success after 60 seconds)
        // assertTimeout(Duration.ofSeconds(60), playGame(/*...*/));
    }

    /**
     * Plays the game when given an app (that is currently in a level)
     */
    public void playGame(/*App app*/){
        InputGenerator ig = generateRandomInput();
        while (ig.playNext(/*app*/)) {
            // do stuff ?
        }
    }
}
