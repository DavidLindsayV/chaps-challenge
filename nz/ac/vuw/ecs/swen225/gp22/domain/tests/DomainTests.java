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
		Domain d = new DomainBuilder().empty(1, 1).key(0, 1, "BLUE").player(0, 0).exit(2, 2).make();
		d.movePlayer(Direction.RIGHT);

		testCompare(ideal, d.toString());
	}

	@Test
	public void keyTest02() {
		String ideal = 	"|_|_|_|\n" +
						"|_|P|_|\n" +
						"|_|_|E|\n";

		// test player can have more than one key of different colours
		Domain d = new DomainBuilder().empty(1, 1).key(0, 1, "BLUE").key(1, 1, "GREEN").player(0, 0).exit(2, 2).make();
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
		Domain d = new DomainBuilder().empty(1, 1).key(0, 1, "BLUE").key(1, 1, "BLUE").player(0, 0).exit(2, 2).make();
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
		Domain d = new DomainBuilder().empty(1, 1).key(0, 1, "BLUE")
				.key(1, 1, "BLUE").key(1, 0, "GREEN").player(0, 0).exit(2, 2).make();
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
		Domain d = new DomainBuilder().empty(1, 2).key(0, 1, "PINK").door(0, 2, "PINK").player(0, 0).exit(2, 2).make();
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
		Domain d = new DomainBuilder().empty(1, 2).key(0, 1, "BLUE").door(0, 2, "BLUE").player(0, 0).exit(2, 2).make();
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
		Domain d = new DomainBuilder().empty(1, 2).key(0, 1, "YELLOW").door(0, 2, "YELLOW").player(0, 0).exit(2, 2).make();
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
		Domain d = new DomainBuilder().empty(1, 2).key(0, 1, "GREEN").door(0, 2, "GREEN").player(0, 0).exit(2, 2).make();
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
		Domain d = new DomainBuilder().empty(1, 2).key(0, 1, "PINK")
				.door(1, 1, "PINK").door(0, 2, "GREEN")
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
		Domain d = new DomainBuilder().empty(1, 2).key(0, 1, "GREEN")
				.key(0, 2, "GREEN")
				.door(1, 2, "GREEN")
				.door(1, 1, "GREEN")
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
		Domain d = new DomainBuilder().empty(1, 2).key(0, 1, "GREEN")
				.door(0, 2, "GREEN")
				.door(1, 2, "GREEN")
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
		Domain d = new DomainBuilder().empty(1, 2).key(0, 1, "GREEN")
				.door(0, 2, "GREEN")
				.door(1, 2, "GREEN")
				.player(0, 0).exit(2, 2).make();
		d.movePlayer(Direction.RIGHT);
		d.movePlayer(Direction.RIGHT);
		d.movePlayer(Direction.DOWN);
		testCompare(ideal, d.toString());
	}

	// ####################################################
	// 					ENEMY TESTS
	// ####################################################

	@Test
	public void enemyTest01() {
		String ideal = 	"|P|_|_|_|\n" +
						"|_|4|_|_|\n" +
						"|_|_|_|_|\n" +
						"|_|_|_|E|\n";

		// test enemy exists
		Domain d = new DomainBuilder().empty(1, 1).player(0, 0).exit(3, 3)
				.enemy(1, 1, List.of(new Point(1, 1), new Point(1, 2), new Point(1, 3))).make();
		testCompare(ideal, d.toString());
	}

	@Test
	public void enemyTest02() {
		String ideal = 	"|P|_|_|_|\n" +
						"|_|_|_|4|\n" +
						"|_|_|_|_|\n" +
						"|_|_|_|E|\n";

		// test enemy can move
		Domain d = new DomainBuilder().empty(1, 1).player(0, 0).exit(3, 3)
				.enemy(1, 1, List.of(new Point(1, 1), new Point(1, 2), new Point(1, 3))).make();
		d.moveActors();
		d.moveActors();
		testCompare(ideal, d.toString());
	}

	@Test
	public void enemyTest03() {
		String ideal = 	"|P|_|_|_|\n" +
						"|_|4|_|_|\n" +
						"|_|_|_|_|\n" +
						"|_|_|_|E|\n";

		// test enemy loops around when path is finished
		Domain d = new DomainBuilder().empty(1, 1).player(0, 0).exit(3, 3)
				.enemy(1, 1, List.of(new Point(1, 1), new Point(1, 2), new Point(1, 3))).make();
		d.moveActors();
		d.moveActors();
		d.moveActors();

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
						"|_|_|P|\n" +
						"|_|_|E|\n";

		// test info placement, player moves on info
		Domain d = new DomainBuilder().info(0, 1)
				.player(0, 0).exit(2, 2).make();
		d.movePlayer(Direction.RIGHT);
		d.movePlayer(Direction.RIGHT);
		d.movePlayer(Direction.DOWN);
		testCompare(ideal, d.toString());
	}

	// ####################################################
	// 						FAILING TESTS
	// ####################################################

	@Test
	public void failingTest01() {
		// test for lower bounds
		checkFailed(()->{new DomainBuilder().empty(-1, 1).empty(1, 1).player(0, 0).exit(0,1).make();});
	}

	@Test
	public void failingTest02() {
		// test for out of upper bounds
		checkFailed(()->{new DomainBuilder().empty(1000, 1).empty(1, 1).player(0, 0).exit(0,1).make();});
	}

	@Test
	public void failingTest03() {
		// test for lower bounds
		checkFailed(()->{new DomainBuilder().empty(1, -1).empty(1, 1).player(0, 0).exit(0,1).make();});
	}

	@Test
	public void failingTest04() {
		// test for out of upper bounds
		checkFailed(()->{new DomainBuilder().empty(1, 1000).empty(1, 1).player(0, 0).exit(0,1).make();});
	}

	@Test
	public void failingTest05() {
		// test for null
		checkFailed(()->{new DomainBuilder().empty(new Integer(null), 1).empty(1, 1).player(0, 0).exit(0, 1).make();});
	}

	@Test
	public void failingTest06() {
		// test for player spawn in a wall
		checkFailed(()->{new DomainBuilder().wall(0, 0).empty(1, 1).player(0, 0).exit(0, 1).make();});
	}

	@Test
	public void failingTest07() {
		// test for spawn two players
		checkFailed(()->{new DomainBuilder().empty(1, 1).player(0, 0).player(0, 0).exit(0, 1).make();});
	}

	@Test
	public void failingTest08() {
		// test for empty domain make
		checkFailed(()->{new DomainBuilder().make();});
	}

	@Test
	public void failingTest09() {
		// test for domain make with no player
		checkFailed(()->{new DomainBuilder().empty(0, 0).make();});
	}

	@Test
	public void failingTest10() {
		// test for domain make with no exit
		checkFailed(()->{new DomainBuilder().empty(0, 0).player(0, 0).make();});
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

	@Test
	public void failingTest13() {
		// test enemy cant function with empty list
		checkFailed(()->{
			new DomainBuilder().empty(1, 1).player(0, 0).exit(3, 3)
					.enemy(1, 1, List.of()).make();
		});
	}

	@Test
	public void failingTest14() {
		// test enemy cant function with null list
		checkFailed(()->{
			new DomainBuilder().empty(1, 1).player(0, 0).exit(3, 3)
					.enemy(1, 1, (List<Point>) null).make();
		});
	}

	@Test
	public void failingTest15() {
		// test enemy cant function with list of null points
		checkFailed(()->{
			new DomainBuilder().empty(1, 1).player(0, 0).exit(3, 3)
					.enemy(1, 1, Arrays.stream(new Point[] {null, null}).collect(Collectors.toList())).make();
		});
	}

	@Test
	public void failingTest16() {
		// test enemy cant move outside of domain (row boundary)
		checkFailed(()->{
			new DomainBuilder().empty(1, 1).player(0, 0).exit(3, 3)
					.enemy(1, 1, List.of(new Point(-1, 0))).make();
		});
	}

	@Test
	public void failingTest17() {
		// check column boundary
		checkFailed(()->{
			new DomainBuilder().empty(1, 1).player(0, 0).exit(3, 3)
					.enemy(1, 1, List.of(new Point(0, -1))).make();
		});

	}

	@Test
	public void failingTest18() {
		// check both boundary
		checkFailed(()->{
			new DomainBuilder().empty(1, 1).player(0, 0).exit(3, 3)
					.enemy(1, 1, List.of(new Point(-1, -1))).make();
		});
	}

	@Test
	public void failingTest19() {
		// check cannot put a wall on a player
		checkFailed(()->{
			new DomainBuilder().empty(1, 1).player(0, 0).wall(0, 0).exit(1, 1).make();
		});
	}

	@Test
	public void failingTest20() {
		// check cannot put a key on a player
		checkFailed(()->{
			new DomainBuilder().empty(1, 1).player(0, 0).key(0, 0, "PINK").exit(1, 1).make();
		});
	}

	@Test
	public void failingTest21() {
		// check cannot put an exit lock on a player
		checkFailed(()->{
			new DomainBuilder().empty(1, 1).player(0, 0).lock(0, 0).exit(1, 1).make();
		});
	}

	@Test
	public void failingTest22() {
		// check cannot put an exit on a player
		checkFailed(()->{
			new DomainBuilder().empty(1, 1).player(0, 0).exit(0, 0).make();
		});
	}

	@Test
	public void failingTest23() {
		// check cannot put an door on a player
		checkFailed(()->{
			new DomainBuilder().empty(1, 1).player(0, 0).door(0, 0, "PINK").exit(1, 1).make();
		});
	}

	@Test
	public void failingTest24() {
		// check cannot put treasure on a player
		checkFailed(()->{
			new DomainBuilder().empty(1, 1).player(0, 0).treasure(0, 0).exit(1, 1).make();
		});
	}

	@Test
	public void failingTest25() {
		// check cannot put an empty tile on a player
		checkFailed(()->{
			new DomainBuilder().empty(1, 1).player(0, 0).empty(0, 0).exit(1, 1).make();
		});
	}

	@Test
	public void failingTest26() {
		// check cannot put an enemy on a player
		checkFailed(()->{
			new DomainBuilder().empty(1, 1).player(0, 0).enemy(0, 0, List.of(new Point(0,0))).exit(1, 1).make();
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
				.key(1, 0, "BLUE")
				.key(1, 1, "GREEN")
				.key(1, 2, "PINK")
				.key(1, 3, "YELLOW")
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
				.door(1, 0, "BLUE")
				.door(1, 1, "GREEN")
				.door(1, 2, "PINK")
				.door(1, 3, "YELLOW")
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
		assert !p2.equals(new Object()) : "Can't equal an object";
	}

	@Test
	public void otherTest07() {
		// test player position matching with actual position
		Domain d = new DomainBuilder().empty(5, 5).player(0, 0).exit(2, 2).make();
		assert d.getPlayerPosition().equals(new Point(0, 0)) : "Player position not matching";

		// test player position is still working after moving
		d.movePlayer(Direction.DOWN);
		d.movePlayer(Direction.DOWN);
		d.movePlayer(Direction.DOWN);
		assert d.getPlayerPosition().equals(new Point(3, 0)) : "Player position not matching";
	}

	@Test
	public void otherTest08() {
		// test domain can get the enemies
		Domain d = new DomainBuilder().empty(2, 2)
				.enemy(0, 0, List.of(new Point(0, 0)))
				.enemy(0, 0, List.of(new Point(0, 1)))
				.enemy(0, 0, List.of(new Point(1, 0))).player(1, 1).exit(2, 2).make();
		assert d.getEnemies().size() == 3;
	}

	@Test
	public void otherTest09() {
		// test for player branch coverage
		Player p = new Player(new DomainBuilder().empty(0, 0).player(0, 0).exit(2, 2).make());
		p.removeKey(AuthenticationColour.BLUE);
	}

	@Test
	public void fuzzTest() {
		DomainBuilder db = new DomainBuilder();
		BuilderInputGenerator big = new BuilderInputGenerator();

		big.generateRandom(1000000);
		big.playAll(db);
	}

	public void testCompare(String ideal, String actual) {
		assert ideal.equals(actual) : "Should be:\n" + ideal + "\n\nGot:\n" + actual;
	}

	public void checkFailed(FailTest f) {
		try {
			f.execute();
		}
		catch (IllegalArgumentException | IllegalStateException e) {return;}
		catch (Exception e) {assert false : "Exception e was: " + e;}
		assert false : "Test should've thrown an exception";
	}
}

