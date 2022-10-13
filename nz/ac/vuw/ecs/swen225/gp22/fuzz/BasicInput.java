package nz.ac.vuw.ecs.swen225.gp22.fuzz;

import java.util.Random;

import nz.ac.vuw.ecs.swen225.gp22.app.*;

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.util.List;

/**
 * Lambda-able interface for easy stuff
 */
interface ArtificialInput {
    public void play(Robot b);
}

/**
 * Class for getting random artificial inputs
 */
public class BasicInput {
    private static List<ArtificialInput> inputTypes = List.of(
        (b)->{b.keyPress(KeyEvent.VK_UP); b.keyRelease(KeyEvent.VK_UP); System.out.println("UP");/* moveup */},
        (b)->{b.keyPress(KeyEvent.VK_DOWN); b.keyRelease(KeyEvent.VK_DOWN); System.out.println("DOWN");/* movedown */},
        (b)->{b.keyPress(KeyEvent.VK_LEFT); b.keyRelease(KeyEvent.VK_LEFT); System.out.println("LEFT");/* moveleft */},
        (b)->{b.keyPress(KeyEvent.VK_RIGHT); b.keyRelease(KeyEvent.VK_RIGHT); System.out.println("RIGHT");/* moveright */}
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
