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
        ()->{/* moveup */},
        ()->{/* movedown */},
        ()->{/* moveleft */},
        ()->{/* moveright */},
        ()->{/* more types of input (mouse click?) */}
    );

    /**
     * Returns a random artificialinput
     * @return
     */
    public static ArtificialInput getRandom(){
        return inputTypes.get(new Random().nextInt());
    }
}
