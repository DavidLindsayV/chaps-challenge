package nz.ac.vuw.ecs.swen225.gp22.fuzz;


import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.ArrayList;
import java.util.Collections;
import nz.ac.vuw.ecs.swen225.gp22.app.*;

/**
 * Class for generating input and playing the input
 */
public class InputGenerator {
    private final List<ArtificialInput> inputs = new ArrayList<ArtificialInput>();
    private int index = 0; // do i need this

    /**
     * Generates n number of inputs for this generator
     * @param n
     */
    public void generateRandom(int n){
        for (int i = 0; i < n; i++) {inputs.add(BasicInput.getRandom());}
    }

    /**
     * performs the next move in the sequence of inputs,
     * returns false if the end of the list is reached
     * @param app
     * @return
     */
    public boolean playNext(UserListener ul){
        inputs.get(index).play(ul);

        // wait for the input to be registered
        try {TimeUnit.MILLISECONDS.sleep(200);}catch(Exception e) {}
        //if (index == 3) { throw new IllegalArgumentException();}				// comment this out to test exceptions
        return (++index < inputs.size());
    }

    /**
     * set index to the end
     */
    public void finish(){this.index = inputs.size();}

    /**
     * set current index to certain bit
     * @param n
     */
    public void setIndex(int n){this.index = n;}

    /**
     * get current index to certain bit
     * @return
     */
    public int getIndex(){return this.index;}

    /**
     * get list of inputs
     * @return
     */
    public List<ArtificialInput> getInputsList(){return Collections.unmodifiableList(inputs);}


}
