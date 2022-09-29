package nz.ac.vuw.ecs.swen225.gp22.fuzz;

import java.util.Random;
import java.util.List;

/**
 * Lambda-able interface for easy stuff
 */
interface ArtificialInput {
    public void play(/*App app*/);
}

/**
 * Class for getting random artificial inputs
 */
public class BasicInput {
    private static List<ArtificialInput> inputTypes = List.of(
        ()->{System.out.println("Moved Up");/* moveup */},
        ()->{System.out.println("Moved Down");/* movedown */},
        ()->{System.out.println("Moved Left");/* moveleft */},
        ()->{System.out.println("Moved Right");/* moveright */},
        ()->{System.out.println("Paused");/* pause */},
        ()->{System.out.println("Unpaused");/* unpause */},
        ()->{System.out.println("Other Input");/* more types of input (mouse click?) */}
    );

    /**
     * Returns a random artificialinput
     * @return
     */
    public static ArtificialInput getRandom(){
        return inputTypes.get(new Random().nextInt());
    }
}
