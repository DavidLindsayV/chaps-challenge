package nz.ac.vuw.ecs.swen225.gp22.fuzz;

import java.awt.Robot;

/**
 * Lambda-able interface for easy stuff
 *
 * @author Maximus De Leon deleomaxi 300566351
 *
 */
interface ArtificialInput {
	/**
	 * Plays an input through the robot.
	 *
	 * @param takes a robot of b
	 */
    public void play(Robot b);
}


