package nz.ac.vuw.ecs.swen225.gp22.fuzz;

import java.util.Random;

import nz.ac.vuw.ecs.swen225.gp22.app.*;

import java.util.List;

/**
 * Lambda-able interface for easy stuff
 */
interface ArtificialInput {
    public void play(UserListener u);
}

/**
 * Class for getting random artificial inputs
 */
public class BasicInput {
    private static List<ArtificialInput> inputTypes = List.of(
        // (u)->{u.up(); System.out.println("UP");/* moveup */},
        // (u)->{u.down(); System.out.println("DOWN");/* movedown */},
        // (u)->{u.left(); System.out.println("LEFT");/* moveleft */},
        // (u)->{u.right(); System.out.println("RIGHT");/* moveright */}
        //(u)->{u.pauseGame(); System.out.println("PAUSE");/* pause */},
        //(u)->{u.resumeGame(); System.out.println("RESUME");/* unpause */}
        //()->{System.out.println("Other Input");/* more types of input (mouse click?) */}
    );

    /**
     * Returns a random artificialinput
     * @return
     */
    public static ArtificialInput getRandom(){
        return inputTypes.get(new Random().nextInt(inputTypes.size()));
    }
}
