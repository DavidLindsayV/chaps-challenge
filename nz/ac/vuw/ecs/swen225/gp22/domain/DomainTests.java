package Tests;
import static org.junit.jupiter.api.Assertions.assertAll;

import org.junit.*;

import nz.ac.vuw.ecs.swen225.gp22.domain.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
//import java.util.stream.*;

interface FailTest{
	public void execute();
}

interface DomainBuilderFuzzTest{
	public void play(DomainBuilder d);
}

/**
 * Generator for building a domain
 * @author deleomaxi
 */
class BuilderInputGenerator{
	private static final int BOARDSIZE = 250;
	private static final List<DomainBuilderFuzzTest> inputTypes = List.of(
		(d)->{d.empty(rint(BOARDSIZE), rint(BOARDSIZE));},
		(d)->{d.wall(rint(BOARDSIZE), rint(BOARDSIZE));},
		(d)->{d.treasure(rint(BOARDSIZE), rint(BOARDSIZE));},
		(d)->{d.key(rint(BOARDSIZE), rint(BOARDSIZE), AuthenticationColour.PINK);},
		(d)->{d.door(rint(BOARDSIZE), rint(BOARDSIZE), AuthenticationColour.PINK);},
		(d)->{d.key(rint(BOARDSIZE), rint(BOARDSIZE), AuthenticationColour.BLUE);},
		(d)->{d.door(rint(BOARDSIZE), rint(BOARDSIZE), AuthenticationColour.BLUE);},
		(d)->{d.key(rint(BOARDSIZE), rint(BOARDSIZE), AuthenticationColour.YELLOW);},
		(d)->{d.door(rint(BOARDSIZE), rint(BOARDSIZE), AuthenticationColour.YELLOW);},
		(d)->{d.key(rint(BOARDSIZE), rint(BOARDSIZE), AuthenticationColour.GREEN);},
		(d)->{d.door(rint(BOARDSIZE), rint(BOARDSIZE), AuthenticationColour.GREEN);}
	);
	List<DomainBuilderFuzzTest> inputs = new ArrayList<>();

	/**
	 * return random integer
	 * @param n
	 * @return
	 */
	private static int rint(int n) {
		return new Random().nextInt(n);
	}

	/**
	 * generate n amount of inputs
	 * @param n
	 */
	public void generateRandom(int n) {
		for (int i=0;i<n;i++) {inputs.add(inputTypes.get(rint(inputTypes.size())));}
	}


	public void playAll(DomainBuilder db) {
		inputs.stream().forEach(i->i.play(db));
	}
}

public class DomainTests {

	// ####################################################
	// 						BASIC TESTS
	// ####################################################

	@Test
	public void basicTest01() {
		String ideal = 	"|P|_|_|\n" +
						"|_|_|_|\n" +
						"|_|_|E|\n";

		// testing empty
		Domain d = new DomainBuilder().empty(1, 1).player(0, 0).exit(2, 2).make();
		testCompare(ideal, d.toString());
	}

	@Test
	public void basicTest02() {
		String ideal = 	"|P|_|_|\n" +
						"|_|_|_|\n" +
						"|_|_|E|\n" +
						"|_|#|_|\n";

		// testing wall and empty
		Domain d = new DomainBuilder().empty(1, 1).wall(3, 1).player(0, 0).exit(2, 2).make();
		testCompare(ideal, d.toString());
	}

	@Test
	public void basicTest03() {
		String ideal = 	"|P|_|_|\n" +
						"|_|_|_|\n" +
						"|_|_|E|\n" +
						"|_|#|_|\n";

		// duplication test
		Domain d = new DomainBuilder().empty(1, 1).wall(3, 1).wall(3, 1).player(0, 0).exit(2, 2).make();
		testCompare(ideal, d.toString());
	}

	@Test
	public void basicTest04() {
		String ideal = 	"|P|_|_|\n" +
						"|_|_|_|\n" +
						"|_|_|E|\n";

		// duplication test
		Domain d = new DomainBuilder().empty(1, 1).empty(1, 1).player(0, 0).exit(2, 2).make();
		testCompare(ideal, d.toString());
	}

	@Test
	public void basicTest05() {
		String ideal = 	"|P|_|_|\n" +
						"|_|#|_|\n" +
						"|_|_|E|\n";

		// overwriting test
		Domain d = new DomainBuilder().empty(1, 1).wall(1, 1).player(0, 0).exit(2, 2).make();
		testCompare(ideal, d.toString());
	}

	@Test
	public void basicTest06() {
		String ideal = 	"|P|_|_|\n" +
						"|_|_|_|\n" +
						"|_|_|E|\n";

		// overwriting test
		Domain d = new DomainBuilder().empty(1, 1).wall(1, 1).empty(1, 1).player(0, 0).exit(2, 2).make();
		testCompare(ideal, d.toString());
	}

	@Test
	public void basicTest07() {
		String ideal = 	"|P|_|_|\n" +
						"|_|_|_|\n" +
						"|_|_|E|\n";

		// test reset
		DomainBuilder db = new DomainBuilder().empty(1, 1);
		db.reset();
		Domain d = db.empty(1, 1).player(0, 0).exit(2, 2).make();
		testCompare(ideal, d.toString());
	}

	// ####################################################
	// 					PLAYERMOVE TESTS
	// ####################################################

	@Test
	public void moveTest01() {
		String ideal = 	"|P|_|_|\n" +
						"|_|_|_|\n" +
						"|_|_|E|\n";

		// test player cant move outside of the domain
		Domain d = new DomainBuilder().empty(1, 1).player(0, 0).exit(2, 2).make();
		d.movePlayer(Direction.LEFT);

		testCompare(ideal, d.toString());
	}

	@Test
	public void moveTest02() {
		String ideal = 	"|P|_|_|\n" +
						"|_|_|_|\n" +
						"|_|_|E|\n";

		// test player cant move outside of the domain
		Domain d = new DomainBuilder().empty(1, 1).player(0, 0).exit(2, 2).make();
		d.movePlayer(Direction.UP);

		testCompare(ideal, d.toString());
	}

	@Test
	public void moveTest03() {
		String ideal = 	"|_|_|_|\n" +
						"|_|_|_|\n" +
						"|P|_|E|\n";

		// test player cant move outside of the domain
		Domain d = new DomainBuilder().empty(1, 1).player(0, 0).exit(2, 2).make();
		d.movePlayer(Direction.DOWN);
		d.movePlayer(Direction.DOWN);
		d.movePlayer(Direction.DOWN);

		testCompare(ideal, d.toString());
	}

	@Test
	public void moveTest04() {
		String ideal = 	"|_|_|P|\n" +
						"|_|_|_|\n" +
						"|_|_|E|\n";

		// test player cant move outside of the domain
		Domain d = new DomainBuilder().empty(1, 1).player(0, 0).exit(2, 2).make();
		d.movePlayer(Direction.RIGHT);
		d.movePlayer(Direction.RIGHT);
		d.movePlayer(Direction.RIGHT);
		testCompare(ideal, d.toString());
	}

	// ####################################################
	// 					WALL TESTS
	// ####################################################

	@Test
	public void wallTest01() {
		String ideal = 	"|P|#|_|\n" +
						"|_|_|_|\n" +
						"|_|_|E|\n";

		// test player cant move into wall
		Domain d = new DomainBuilder().empty(1, 1).wall(0, 1).player(0, 0).exit(2, 2).make();
		d.movePlayer(Direction.RIGHT);

		testCompare(ideal, d.toString());
	}

	@Test
	public void wallTest02() {
		String ideal = 	"|P|#|_|\n" +
						"|#|_|_|\n" +
						"|_|_|E|\n";

		// test player cant move into wall
		Domain d = new DomainBuilder().empty(1, 1).wall(0, 1).wall(1, 0).player(0, 0).exit(2, 2).make();
		d.movePlayer(Direction.RIGHT);
		d.movePlayer(Direction.DOWN);

		testCompare(ideal, d.toString());
	}

	@Test
	public void wallTest03() {
		String ideal = 	"|_|#|_|\n" +
						"|#|P|_|\n" +
						"|_|_|E|\n";

		// test player cant move into wall
		Domain d = new DomainBuilder().empty(1, 1).wall(0, 1).wall(1, 0).player(1, 1).exit(2, 2).make();
		d.movePlayer(Direction.LEFT);
		d.movePlayer(Direction.UP);

		testCompare(ideal, d.toString());
	}

	// ####################################################
	// 					TREASURE TESTS
	// ####################################################

	@Test
	public void treasureTest01() {
		String ideal = 	"|_|P|_|\n" +
						"|_|_|_|\n" +
						"|_|_|E|\n";

		// test player can take treasure
		Domain d = new DomainBuilder().empty(1, 1).treasure(0, 1).player(0, 0).exit(2, 2).make();
		d.movePlayer(Direction.RIGHT);

		testCompare(ideal, d.toString());
	}

	@Test
	public void treasureTest02() {
		String ideal = 	"|_|_|_|\n" +
						"|*|P|_|\n" +
						"|_|_|E|\n";

		// test taken treasure becomes free tile after being taken
		Domain d = new DomainBuilder().empty(1, 1).treasure(0, 1).treasure(1, 1).treasure(1, 0).player(0, 0).exit(2, 2).make();
		d.movePlayer(Direction.RIGHT);
		d.movePlayer(Direction.DOWN);

		testCompare(ideal, d.toString());
	}

	// ####################################################
	// 						KEY TESTS
	// ####################################################

	@Test
	public void keyTest01() {
		String ideal = 	"|_|P|_|\n" +
						"|_|_|_|\n" +
						"|_|_|E|\n";

		// test player can take key
		Domain d = new DomainBuilder().empty(1, 1).key(0, 1, AuthenticationColour.BLUE).player(0, 0).exit(2, 2).make();
		d.movePlayer(Direction.RIGHT);

		testCompare(ideal, d.toString());
	}

	@Test
	public void keyTest02() {
		String ideal = 	"|_|_|_|\n" +
						"|_|P|_|\n" +
						"|_|_|E|\n";

		// test player can have more than one key of different colours
		Domain d = new DomainBuilder().empty(1, 1).key(0, 1, AuthenticationColour.BLUE).key(1, 1, AuthenticationColour.GREEN).player(0, 0).exit(2, 2).make();
		d.movePlayer(Direction.RIGHT);
		d.movePlayer(Direction.DOWN);
		testCompare(ideal, d.toString());
	}

	@Test
	public void keyTest03() {
		String ideal = 	"|_|_|_|\n" +
						"|P|_|_|\n" +
						"|_|_|E|\n";

		// test player can have more than one key of the same colour
		Domain d = new DomainBuilder().empty(1, 1).key(0, 1, AuthenticationColour.BLUE).key(1, 1, AuthenticationColour.BLUE).player(0, 0).exit(2, 2).make();
		d.movePlayer(Direction.RIGHT);
		d.movePlayer(Direction.DOWN);
		d.movePlayer(Direction.LEFT);
		testCompare(ideal, d.toString());
	}

	@Test
	public void keyTest04() {
		String ideal = 	"|_|_|_|\n" +
						"|k|P|_|\n" +
						"|_|_|E|\n";

		// test key can exists
		Domain d = new DomainBuilder().empty(1, 1).key(0, 1, AuthenticationColour.BLUE)
				.key(1, 1, AuthenticationColour.BLUE).key(1, 0, AuthenticationColour.GREEN).player(0, 0).exit(2, 2).make();
		d.movePlayer(Direction.RIGHT);
		d.movePlayer(Direction.DOWN);
		testCompare(ideal, d.toString());
	}

	// ####################################################
	// 						DOOR TESTS
	// ####################################################

	@Test
	public void doorTest01() {
		String ideal = 	"|_|_|P|\n" +
						"|_|_|_|\n" +
						"|_|_|E|\n";

		// test blue door w/ blue key
		Domain d = new DomainBuilder().empty(1, 2).key(0, 1, AuthenticationColour.PINK).door(0, 2, AuthenticationColour.PINK).player(0, 0).exit(2, 2).make();
		d.movePlayer(Direction.RIGHT);
		d.movePlayer(Direction.RIGHT);
		testCompare(ideal, d.toString());
	}

	@Test
	public void doorTest02() {
		String ideal = 	"|_|_|P|\n" +
						"|_|_|_|\n" +
						"|_|_|E|\n";

		// test blue door w/ blue key
		Domain d = new DomainBuilder().empty(1, 2).key(0, 1, AuthenticationColour.BLUE).door(0, 2, AuthenticationColour.BLUE).player(0, 0).exit(2, 2).make();
		d.movePlayer(Direction.RIGHT);
		d.movePlayer(Direction.RIGHT);
		testCompare(ideal, d.toString());
	}


	@Test
	public void doorTest03() {
		String ideal = 	"|_|_|P|\n" +
						"|_|_|_|\n" +
						"|_|_|E|\n";

		// test blue door w/ blue key
		Domain d = new DomainBuilder().empty(1, 2).key(0, 1, AuthenticationColour.YELLOW).door(0, 2, AuthenticationColour.YELLOW).player(0, 0).exit(2, 2).make();
		d.movePlayer(Direction.RIGHT);
		d.movePlayer(Direction.RIGHT);
		testCompare(ideal, d.toString());
	}


	@Test
	public void doorTest04() {
		String ideal = 	"|_|_|P|\n" +
						"|_|_|_|\n" +
						"|_|_|E|\n";

		// test blue door w/ blue key
		Domain d = new DomainBuilder().empty(1, 2).key(0, 1, AuthenticationColour.GREEN).door(0, 2, AuthenticationColour.GREEN).player(0, 0).exit(2, 2).make();
		d.movePlayer(Direction.RIGHT);
		d.movePlayer(Direction.RIGHT);
		testCompare(ideal, d.toString());
	}

	@Test
	public void doorTest05() {
		String ideal = 	"|_|_|@|\n" +
						"|_|P|_|\n" +
						"|_|_|E|\n";

		// test pink cant be used to open green door and then use on pink door
		Domain d = new DomainBuilder().empty(1, 2).key(0, 1, AuthenticationColour.PINK)
				.door(1, 1, AuthenticationColour.PINK).door(0, 2, AuthenticationColour.GREEN)
				.player(0, 0).exit(2, 2).make();
		d.movePlayer(Direction.RIGHT);
		d.movePlayer(Direction.RIGHT);
		d.movePlayer(Direction.DOWN);
		testCompare(ideal, d.toString());
	}

	@Test
	public void doorTest06() {
		String ideal = 	"|_|_|_|\n" +
						"|_|P|_|\n" +
						"|_|_|E|\n";

		// test player can pick up two green keys and then open two green doors
		Domain d = new DomainBuilder().empty(1, 2).key(0, 1, AuthenticationColour.GREEN)
				.key(0, 2, AuthenticationColour.GREEN)
				.door(1, 2, AuthenticationColour.GREEN)
				.door(1, 1, AuthenticationColour.GREEN)
				.player(0, 0).exit(2, 2).make();
		d.movePlayer(Direction.RIGHT);
		d.movePlayer(Direction.RIGHT);
		d.movePlayer(Direction.DOWN);
		d.movePlayer(Direction.LEFT);
		testCompare(ideal, d.toString());
	}

	@Test
	public void doorTest07() {
		String ideal = 	"|_|_|P|\n" +
						"|_|_|@|\n" +
						"|_|_|E|\n";

		// test player can pick up one green key but ONLY one green door
		Domain d = new DomainBuilder().empty(1, 2).key(0, 1, AuthenticationColour.GREEN)
				.door(0, 2, AuthenticationColour.GREEN)
				.door(1, 2, AuthenticationColour.GREEN)
				.player(0, 0).exit(2, 2).make();
		d.movePlayer(Direction.RIGHT);
		d.movePlayer(Direction.RIGHT);
		d.movePlayer(Direction.DOWN);
		testCompare(ideal, d.toString());
	}

	@Test
	public void doorTest08() {
		String ideal = 	"|_|_|P|\n" +
						"|_|_|@|\n" +
						"|_|_|E|\n";

		// ?
		Domain d = new DomainBuilder().empty(1, 2).key(0, 1, AuthenticationColour.GREEN)
				.door(0, 2, AuthenticationColour.GREEN)
				.door(1, 2, AuthenticationColour.GREEN)
				.player(0, 0).exit(2, 2).make();
		d.movePlayer(Direction.RIGHT);
		d.movePlayer(Direction.RIGHT);
		d.movePlayer(Direction.DOWN);
		testCompare(ideal, d.toString());
	}

	// ####################################################
	// 					EXIT TESTS
	// ####################################################

	@Test
	public void exitTest01() {
		String ideal = 	"|_|_|_|\n" +
						"|_|_|_|\n" +
						"|_|_|P|\n";

		// player with no more treasures to collect can exit
		Domain d = new DomainBuilder()
				.player(0, 0).exit(2, 2).make();
		d.movePlayer(Direction.RIGHT);
		d.movePlayer(Direction.RIGHT);
		d.movePlayer(Direction.DOWN);
		d.movePlayer(Direction.DOWN);
		testCompare(ideal, d.toString());
	}

	@Test
	public void exitTest02() {
		String ideal = 	"|_|_|_|\n" +
						"|_|_|_|\n" +
						"|_|_|P|\n";

		// player cannot move when they have already exited
		Domain d = new DomainBuilder()
				.player(0, 0).exit(2, 2).make();
		d.movePlayer(Direction.RIGHT);
		d.movePlayer(Direction.RIGHT);
		d.movePlayer(Direction.DOWN);
		d.movePlayer(Direction.DOWN);
		d.movePlayer(Direction.LEFT);
		testCompare(ideal, d.toString());
	}

	@Test
	public void exitTest03() {
		String ideal = 	"|_|_|_|\n" +
						"|_|_|_|\n" +
						"|_|_|P|\n";

		// player can exit after collecting all the treasures
		Domain d = new DomainBuilder().treasure(0, 2)
				.player(0, 0).exit(2, 2).make();
		d.movePlayer(Direction.RIGHT);
		d.movePlayer(Direction.RIGHT);
		d.movePlayer(Direction.DOWN);
		d.movePlayer(Direction.DOWN);
		testCompare(ideal, d.toString());
	}

	@Test
	public void exitTest04() {
		String ideal = 	"|_|_|_|\n" +
						"|_|_|_|\n" +
						"|_|_|P|\n";

		// player can exit after collecting all the treasures
		Domain d = new DomainBuilder().treasure(0, 2)
				.player(0, 0).exit(2, 2).make();
		d.movePlayer(Direction.RIGHT);
		d.movePlayer(Direction.RIGHT);
		d.movePlayer(Direction.DOWN);
		d.movePlayer(Direction.DOWN);
		testCompare(ideal, d.toString());
	}

	@Test
	public void exitTest05() {
		String ideal = 	"|_|_|_|\n" +
						"|_|_|_|\n" +
						"|_|_|P|\n";

		// player can open the lock after collecting all the treasures
		Domain d = new DomainBuilder().treasure(0, 2).lock(1, 2)
				.player(0, 0).exit(2, 2).make();
		d.movePlayer(Direction.RIGHT);
		d.movePlayer(Direction.RIGHT);
		d.movePlayer(Direction.DOWN);
		d.movePlayer(Direction.DOWN);
		testCompare(ideal, d.toString());
	}

	@Test
	public void exitTest06() {
		String ideal = 	"|_|_|_|\n" +
						"|_|_|_|\n" +
						"|_|_|P|\n";

		// player can open the lock after collecting all the treasures
		Domain d = new DomainBuilder().treasure(0, 2).lock(1, 2)
				.player(0, 0).exit(2, 2).make();
		d.movePlayer(Direction.RIGHT);
		d.movePlayer(Direction.RIGHT);
		d.movePlayer(Direction.DOWN);
		d.movePlayer(Direction.DOWN);
		testCompare(ideal, d.toString());
	}

	@Test
	public void exitTest07() {
		String ideal = 	"|_|_|P|\n" +
						"|_|*|L|\n" +
						"|_|_|E|\n";

		// player CANT open the lock, not all treasures collected
		Domain d = new DomainBuilder().treasure(1, 1).lock(1, 2)
				.player(0, 0).exit(2, 2).make();
		d.movePlayer(Direction.RIGHT);
		d.movePlayer(Direction.RIGHT);
		d.movePlayer(Direction.DOWN);
		d.movePlayer(Direction.DOWN);
		testCompare(ideal, d.toString());
	}

	// ####################################################
	// 						INFO TESTS
	// ####################################################

	@Test
	public void infoTest01() {
		String ideal = 	"|_|i|_|\n" +
						"|_|_|_|\n" +
						"|_|_|P|\n";

		// test info placement, player moves on info
		Domain d = new DomainBuilder().info(0, 1)
				.player(0, 0).exit(2, 2).make();
		d.movePlayer(Direction.RIGHT);
		d.movePlayer(Direction.RIGHT);
		d.movePlayer(Direction.DOWN);
		d.movePlayer(Direction.DOWN);
		testCompare(ideal, d.toString());
	}

	// ####################################################
	// 						FAILING TESTS
	// ####################################################

	@Test
	public void failingTest01() {
		// test for lower bounds
		checkFailed(()->{Domain d = new DomainBuilder().empty(-1, 1).empty(1, 1).player(0, 0).exit(0,1).make();});
	}

	@Test
	public void failingTest02() {
		// test for out of upper bounds
		checkFailed(()->{Domain d = new DomainBuilder().empty(1000, 1).empty(1, 1).player(0, 0).exit(0,1).make();});
	}

	@Test
	public void failingTest03() {
		// test for lower bounds
		checkFailed(()->{Domain d = new DomainBuilder().empty(1, -1).empty(1, 1).player(0, 0).exit(0,1).make();});
	}

	@Test
	public void failingTest04() {
		// test for out of upper bounds
		checkFailed(()->{Domain d = new DomainBuilder().empty(1, 1000).empty(1, 1).player(0, 0).exit(0,1).make();});
	}

	@Test
	public void failingTest05() {
		// test for null
		checkFailed(()->{Domain d = new DomainBuilder().empty(new Integer(null), 1).empty(1, 1).player(0, 0).exit(0, 1).make();});
	}

	@Test
	public void failingTest06() {
		// test for player spawn in a wall
		checkFailed(()->{Domain d = new DomainBuilder().wall(0, 0).empty(1, 1).player(0, 0).exit(0, 1).make();});
	}

	@Test
	public void failingTest07() {
		// test for spawn two players
		checkFailed(()->{Domain d = new DomainBuilder().empty(1, 1).player(0, 0).player(0, 0).exit(0, 1).make();});
	}

	@Test
	public void failingTest08() {
		// test for empty domain make
		checkFailed(()->{Domain d = new DomainBuilder().make();});
	}

	@Test
	public void failingTest09() {
		// test for domain make with no player
		checkFailed(()->{Domain d = new DomainBuilder().empty(0, 0).make();});
	}

	@Test
	public void failingTest10() {
		// test for domain make with no exit
		checkFailed(()->{Domain d = new DomainBuilder().empty(0, 0).player(0, 0).make();});
	}

	@Test
	public void failingTest11() {
		// test for set player outside of domain
		checkFailed(()->{
			Domain d = new DomainBuilder().empty(5, 5).player(0, 0).exit(2, 2).make();
			d.setPlayerPosition(new Point(-1, 0));
		});
	}

	@Test
	public void failingTest12() {
		// test for boundary check, test player position
		checkFailed(()->{
			Domain d = new DomainBuilder().empty(5, 5).player(0, 0).exit(2, 2).make();
			d.setPlayerPosition(new Point(0, 6));
			d.setPlayerPosition(new Point(6, 0));
		});
	}

	// ####################################################
	// 						OTHER TESTS
	// ####################################################

	@Test
	public void otherTest01() {
		// test for domain the same as get innerstate
		Domain d = new DomainBuilder().empty(5, 5).player(0, 0).exit(2, 2).make();
		Tile[][] innerstate = d.getInnerState();

		String board = "";
        for (int y=0; y<innerstate.length; ++y){
            for (int x=0; x<innerstate[y].length; ++x) {
                if (x == 0 && y == 0) {
                    board+="|P";
                } else {
                    board+="|"+innerstate[y][x].toString();
                }
            }
            board+="|\n";
        }

		testCompare(d.toString(), board);
	}

	@Test
	public void otherTest02() {
		// test colour function for free tile
		Domain d = new DomainBuilder().empty(5, 5).player(0, 0).exit(2, 2).make();
		Tile[][] innerstate = d.getInnerState();
		assert innerstate[0][0].colour() == "NULL";
	}

	@Test
	public void otherTest03() {
		// test for key state
		Domain d = new DomainBuilder().empty(5, 5)
				.key(1, 0, AuthenticationColour.BLUE)
				.key(1, 1, AuthenticationColour.GREEN)
				.key(1, 2, AuthenticationColour.PINK)
				.key(1, 3, AuthenticationColour.YELLOW)
				.player(0, 0).exit(2, 2).make();
		Tile[][] innerstate = d.getInnerState();
		assert innerstate[1][0].colour().equals("BLUE") : "COLOUR SHOULD BE BLUE";
		assert innerstate[1][1].colour().equals("GREEN") : "COLOUR SHOULD BE GREEN";
		assert innerstate[1][2].colour().equals("PINK") : "COLOUR SHOULD BE PINK";
		assert innerstate[1][3].colour().equals("YELLOW") : "COLOUR SHOULD BE YELLOW";
		assert !innerstate[1][0].isWall() : "SHOULDN'T BE A WALL";
	}

	@Test
	public void otherTest04() {
		// test for door state
		Domain d = new DomainBuilder().empty(5, 5)
				.door(1, 0, AuthenticationColour.BLUE)
				.door(1, 1, AuthenticationColour.GREEN)
				.door(1, 2, AuthenticationColour.PINK)
				.door(1, 3, AuthenticationColour.YELLOW)
				.player(0, 0).exit(2, 2).make();
		Tile[][] innerstate = d.getInnerState();
		assert innerstate[1][0].colour().equals("BLUE") : "COLOUR SHOULD BE BLUE";
		assert innerstate[1][1].colour().equals("GREEN") : "COLOUR SHOULD BE GREEN";
		assert innerstate[1][2].colour().equals("PINK") : "COLOUR SHOULD BE PINK";
		assert innerstate[1][3].colour().equals("YELLOW") : "COLOUR SHOULD BE YELLOW";
		assert innerstate[1][0].isWall() : "SHOULD BE A WALL";
	}

	@Test
	public void otherTest05() {
		// test for treasure state
		Domain d = new DomainBuilder().empty(5, 5)
				.treasure(1, 0)
				.player(0, 0).exit(2, 2).make();
		Tile[][] innerstate = d.getInnerState();
		assert !innerstate[1][0].isWall() : "SHOULD BE A WALL";
	}

	@Test
	public void otherTest06() {
		// test for point equals function
		Point p = new Point(0, 0);
		Point p2 = new Point(0, 0);
		Point p3 = new Point(0, 1);
		assert p.equals(p) : "Equals itself";
		assert p.equals(p2) : "Equals another point with different values";
		assert !p.equals(p3) : "Has different values, not equal";
		assert !p2.equals(p3) : "Has different values, not equals (2)";
		assert !p2.equals(null) : "Can't equal null";
		assert !p2.equals(new Integer(1)) : "Can't equal an integer";
	}

	@Test
	public void fuzzTest() {
		DomainBuilder db = new DomainBuilder();
		BuilderInputGenerator big = new BuilderInputGenerator();

		big.generateRandom(1000000);
		big.playAll(db);
		//System.out.println(db.make().toString());	// printing takes a long as time bruh
	}

	public void testCompare(String ideal, String actual) {
		assert ideal.equals(actual) : "Should be:\n" + ideal + "\n\nGot:\n" + actual;
	}

	public void checkFailed(FailTest f) {
		try {
			f.execute();
		}
		catch (IllegalArgumentException | IllegalStateException b ) {return;}
		catch (Exception e) {assert false : "Exception e was: " + e;}
		assert false : "Test should've thrown an exception";
	}
}
