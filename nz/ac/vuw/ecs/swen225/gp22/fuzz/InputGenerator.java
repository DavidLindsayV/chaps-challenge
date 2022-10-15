package nz.ac.vuw.ecs.swen225.gp22.fuzz;


import java.util.List;
import java.util.Random;
import java.awt.Robot;
import java.util.ArrayList;

/**
 * Class for generating input and playing the input
 *
 * @author Maximus De Leon deleomaxi 300566351
 *
 */
public class InputGenerator {
	private final int LEAST_INPUT_CHECK = 10; 				// how many inputs before a smart input is created
	private final Random rand = new Random();
    private final List<Integer> count = new ArrayList<>();
    private int currentIndex = 0;
    private boolean running = true;

    // add a count for each input
    public InputGenerator(){
    	for (int i=0; i<BasicInput.getTypes().size(); i++){count.add(0);}
    }

    /**
     * Performs a random or smart move
     *
     * @param b takes a robot as a parameter to perform inputs
     * @return returns boolean of whether or not to continue performing inputs
     */
    public boolean playNext(Robot b){
    	currentIndex = (currentIndex + 1)%100;		// keep current index within 100

    	// every nth input will generate a smart input
        if (currentIndex%LEAST_INPUT_CHECK == 0) {
        	int lc = getLeastCommonInput();
        	count.set(lc, count.get(lc) + 1);		// ++ count of smart input
        	BasicInput.getTypes().get(lc).play(b);	// play smart input
        }

        // random
        else {
        	// play a random input and add to count
	    	int r = rand.nextInt(BasicInput.getTypes().size());
	        BasicInput.getTypes().get(r).play(b);
	        count.set(r, count.get(r) + 1); 		// ++ the current count of the played input
        }

        return running;
    }

    /**
     * Returns the index of the least common input of the artificial interface
     * @return int index
     */
    private int getLeastCommonInput() {
    	int index = 0;
    	int least = Integer.MAX_VALUE;
    	for (int i = 0; i < count.size(); i++) {
    		if (count.get(i) < least) {index = i; least = count.get(i);}
    	}
    	return index;
    }

    /**
     * set index to the end
     */
    public void finish(){this.running = false;}
}
