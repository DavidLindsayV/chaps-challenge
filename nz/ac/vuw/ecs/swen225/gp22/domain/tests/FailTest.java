
package nz.ac.vuw.ecs.swen225.gp22.domain.tests;

import org.junit.*;

import nz.ac.vuw.ecs.swen225.gp22.domain.AuthenticationColour;
import nz.ac.vuw.ecs.swen225.gp22.domain.Direction;
import nz.ac.vuw.ecs.swen225.gp22.domain.Domain;
import nz.ac.vuw.ecs.swen225.gp22.domain.DomainBuilder;
import nz.ac.vuw.ecs.swen225.gp22.domain.Player;
import nz.ac.vuw.ecs.swen225.gp22.domain.Point;
import nz.ac.vuw.ecs.swen225.gp22.domain.Tile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

interface FailTest{
	public void execute();
}

interface DomainBuilderFuzzTest{
	public void play(DomainBuilder d);
}

/**
 * Generator for building a domain
 * @author deleomaxi & rubran
 */
class BuilderInputGenerator{
	private static final int BOARDSIZE = 250;
	private static final Random RAND = new Random();

	private static final List<DomainBuilderFuzzTest> inputTypes = List.of(
		(d)->{d.empty(rint(BOARDSIZE), rint(BOARDSIZE));},
		(d)->{d.wall(rint(BOARDSIZE), rint(BOARDSIZE));},
		(d)->{d.treasure(rint(BOARDSIZE), rint(BOARDSIZE));},
		(d)->{d.key(rint(BOARDSIZE), rint(BOARDSIZE), "PINK");},
		(d)->{d.door(rint(BOARDSIZE), rint(BOARDSIZE), "PINK");},
		(d)->{d.key(rint(BOARDSIZE), rint(BOARDSIZE), "BLUE");},
		(d)->{d.door(rint(BOARDSIZE), rint(BOARDSIZE), "BLUE");},
		(d)->{d.key(rint(BOARDSIZE), rint(BOARDSIZE), "YELLOW");},
		(d)->{d.door(rint(BOARDSIZE), rint(BOARDSIZE), "YELLOW");},
		(d)->{d.key(rint(BOARDSIZE), rint(BOARDSIZE), "GREEN");},
		(d)->{d.door(rint(BOARDSIZE), rint(BOARDSIZE), "GREEN");}
	);
	List<DomainBuilderFuzzTest> inputs = new ArrayList<>();

	/**
	 * return random integer
	 * @param n
	 * @return
	 */
	private static int rint(int n) {
		return RAND.nextInt(n);
	}

	/**
	 * generate n amount of inputs
	 * @param n
	 */
	public void generateRandom(int n) {
		for (int i=0;i<n;i++) {inputs.add(inputTypes.get(rint(inputTypes.size())));}
	}


	/**
	 * build the domain using the given inputs
	 * @param db
	 */
	public void playAll(DomainBuilder db) {
		inputs.stream().forEach(i->i.play(db));
	}
}



