package nz.ac.vuw.ecs.swen225.gp22.fuzz;

import java.awt.event.KeyEvent;
import java.util.List;

/**
 * Class for getting random artificial inputs
 *
 * @author Maximus De Leon deleomaxi 300566351
 *
 */
public class BasicInput {
	// list of inputs only contain up, down, left and right
    private static List<ArtificialInput> inputTypes = List.of(
        (b)->{b.keyPress(KeyEvent.VK_UP); b.keyRelease(KeyEvent.VK_UP);},
        (b)->{b.keyPress(KeyEvent.VK_DOWN); b.keyRelease(KeyEvent.VK_DOWN);},
        (b)->{b.keyPress(KeyEvent.VK_LEFT); b.keyRelease(KeyEvent.VK_LEFT);},
        (b)->{b.keyPress(KeyEvent.VK_RIGHT); b.keyRelease(KeyEvent.VK_RIGHT);}
    );

    /**
     * Returns the list of artificial inputs
     * @return
     */
    public static List<ArtificialInput> getTypes(){
        return inputTypes;
    }
}
