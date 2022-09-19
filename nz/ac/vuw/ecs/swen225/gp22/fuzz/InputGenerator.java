package nz.ac.vuw.ecs.swen225.gp22.fuzz;


import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

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
    public boolean playNext(/*App app*/){
        //inputs.get(index).play(app);
        return (++index != inputs.size());
    }
    
    public void playAll() {} // do this instead ?

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
