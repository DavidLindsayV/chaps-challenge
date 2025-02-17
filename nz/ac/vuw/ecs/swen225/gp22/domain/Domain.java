package nz.ac.vuw.ecs.swen225.gp22.domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import nz.ac.vuw.ecs.swen225.gp22.app.GUI;
import nz.ac.vuw.ecs.swen225.gp22.app.UserListener;

/**
 * The domain represents the internal state of the game.
 * It handles all the logic between tiles, players, and actors.
 *
 * @author Brandon Ru 300562436
 *
 */
public class Domain {

  private Tile[][] gameState;
  private Player player;
  private List<Enemy> enemies;
  private int requiredTreasureCount;
  private boolean playing;

  public static final int GRAPHICAL_PADDING = 1; // Viewport padding.
  public static final String TOOLTIP_STRING = "1. CTRL-X - exit the game, the current game state will be lost, the next time the game is\n"
	+ "started, it will resume from the last unfinished level\n"
	+ "2. CTRL-S - exit the game, saves the game state, game will resume next time the\n"
	+ "application will be started\n"
	+ "3. CTRL-R - resume a saved game -- this will pop up a file selector to select a saved game\n"
	+ "to be loaded\n"
	+ "4. CTRL-1 - start a new game at level 1\n"
	+ "5. CTRL-2 - start a new game at level 2\n"
	+ "6. SPACE - pause the game and display a “game is paused” dialog\n"
	+ "7. ESC - close the “game is paused” dialog and resume the game\n"
	+ "8. UP, DOWN, LEFT, RIGHT ARROWS -- move Chap within the maze";

  /**
   * Raw constructor.
   * Do not use. Use the builder please.
   * @param gameState 2D array of tiles.
   * @param enemies List of enemies.
   */
  public Domain(Tile[][] gameState, List<Enemy> enemies) {
    this.player = new Player(this);
    this.enemies = enemies;
    this.gameState = gameState;
    this.playing = true;
    countTreasures();
  }

  /**
   * ------------------ PUBLIC API ------------------------------------------
   */

  /**
   * Returns the number of treasures on the map.
   * Used within player to check if all treasures obtained.
   * @return Number of treasures on the map.
   */
  public int requiredTreasureCount() {
    return requiredTreasureCount;
  }

  /**
   * Returns the number of treasures left to collect
   * @returns Number of treasures left
   */
  public int treasuresLeft() {
    return requiredTreasureCount - player.getTreasureCount();
  }

  /**
   * Returns the total keys
   * @returns The number of keys left
   */
  public int keysCollected() {
    return player.getTotalKeysCollected();
  }

  /**
   * Returns the total keys, and their colors
   * @returns The number of keys left in a hash map
   */
  public Map<AuthenticationColour, Integer> keysHistory() {
    return player.getTotalKeyHistory();
  }

  /**
   * Exposes the player outside of the domain.
   * @return Player
   */
  public Player getPlayer() {
    return player;
  }


  /**
   * Sets the player position.
   * Used within the domain builder.
   * @param pos The desired position of the player.
   */
  public void setPlayerPosition(Point pos) {
    if (!withinDomain(pos)) {
      throw new IllegalArgumentException(
        "Cannot set player position in domain out of bounds."
      );
    }
    this.player.setPosition(pos);
  }

  /**
   * Get the player position
   * @param pos The position of the player.
   */
  public Point getPlayerPosition() {
    return player.getPosition();
  }

  /**
   * Get the player position
   * @param pos The position of the player.
   */
  public Point getPlayerGraphicalPosition() {
    return player.getGraphicalPosition();
  }

  /**
   * Returns the enemies.
   * @return List of enemies.
   */
  public List<Enemy> getEnemies() {
    return Collections.unmodifiableList(enemies);
  }

  /**
   * Move player in a given direction.
   * @param direction Direction enum (UP, LEFT, RIGHT, DOWN)
   */
  public void movePlayer(Direction direction) {
    if (playing == false || direction == Direction.NONE) {
      return;
    }

    Point pos = player.getPosition();
    pos = pos.translate(direction.dr, direction.dc);

    // PRECONDITION CHECK - If this doesn't move the player out of the domain.
    if (withinDomain(pos)) {
      // Interact with the tile.
      Tile target = gameState[(int) pos.row()][(int) pos.col()];

      target.acceptPlayer(player);
      // PRECONDITION CHECK - Then move it in, iff it's not a wall.
      if (!target.isWall()) {
        player.setPosition(pos);
      }
    }

    // Global post condition check
    // checkIfValidTreasureCounts();

    // Check if the player's position collides with any enemies.
    checkIfPlayerKilledByEnemies();
  }


/**
   * This function will be called when the enemies need to move.
   * Checks if any enemies collided with enemies.
   * Use observer pattern.
   */
  public void moveActors() {
    enemies.stream().forEach(e -> e.move());
    checkIfPlayerKilledByEnemies();
  }

  /**
   * For use in testing. Similar to SWEN221 chess toString.
   */
  public String toString() {
    String board = "";
    for (int y = 0; y < gameState.length; ++y) {
      for (int x = 0; x < gameState[y].length; ++x) {
        Point currentPosition = new Point(y, x);
        if (playerOn(currentPosition)) {
          board += "|" + player.toString();
        } else if (
          enemies.stream().anyMatch(e -> e.collidesWith(currentPosition))
        ) {
          board += "|" + enemies.get(0).toString();
        } else {
          board += "|" + gameState[y][x].toString();
        }
      }
      board += "|\n";
    }
    return board;
  }

  /**
   * This function will be called when the level is completed.
   * Use observer pattern.
   */
  public void nextLevel() {
    this.playing = false;
    UserListener.nextLevel();
  }

  /**
   * This function will be called when the level is lost.
   */
  public void loseLevel() {
    this.playing = false;
    UserListener.loseLevel();
  }

  /**
   * This function will be called when the player steps on the info tile.
   */
  public static void showToolTip() {
    GUI.showToolTip(TOOLTIP_STRING);
  }

  /**
   * Returns a 2D clone of the internal view of the domain.
   */
  public Tile[][] getInnerState() {
    return Arrays.stream(gameState).map(Tile[]::clone).toArray(Tile[][]::new);
  }

  /**
   * Returns a padded 2D clone of the internal view of the domain.
   */
  public Tile[][] getGraphicalState() {
    int domainHeight = gameState.length;
    int domainWidth = gameState[0].length;
    int graphicalHeight = domainHeight + GRAPHICAL_PADDING * 2;
    int graphicalWidth = domainWidth + GRAPHICAL_PADDING * 2;

    Tile[][] graphicalGameState = new Tile[graphicalHeight][graphicalWidth];
    for (Tile[] gameRow : graphicalGameState) {
      Arrays.fill(gameRow, FreeTile.empty());
    }

    for (int y = 0; y < domainHeight; ++y) {
      for (int x = 0; x < domainWidth; ++x) {
        graphicalGameState[y + GRAPHICAL_PADDING][x + GRAPHICAL_PADDING] =
          gameState[y][x];
      }
    }

    return graphicalGameState;
  }

  /**
   * Only use for persistency, no where else please.
   * This will set the initial treasureCount
   */
  public void overrideInitialTreasureCount(int treasureCount) {
    this.requiredTreasureCount = treasureCount;
  }

  /** ---------------------PRIVATE FUNCTIONS------------------------------- */

  /**
   * Is this position within the domain.
   * For example, moving up at (0, 0) --> false.
   * Moving down --> true.
   * @param pos
   * @return
   */
  private boolean withinDomain(Point pos) {
    return (
      pos.row() >= 0 &&
      pos.row() < gameState.length &&
      pos.col() >= 0 &&
      pos.col() < gameState[0].length
    );
  }

  /**
   * Counts the number of treasures to finish the level.
   * Should only be called within the constructor.
   */
  private void countTreasures() {
    for (int y = 0; y < gameState.length; ++y) {
      for (int x = 0; x < gameState[y].length; ++x) {
        if (gameState[y][x].name() == "treasure") {
          requiredTreasureCount++;
        }
      }
    }
  }

  /**
   * Check if the player is on this coordinate.
   * @param pos Point of interest.
   */
  private boolean playerOn(Point pos) {
    return pos.equals(player.getPosition());
  }

  /**
   * Loses the level, iff the player is hit by enemies.
   * Copies the enemies to prevent concurrent modification error.
   */
  private void checkIfPlayerKilledByEnemies() {
    boolean playerDead = new ArrayList<Enemy>(enemies)
      .stream()
      .anyMatch(e -> e.collidesWith(player.getPosition()));
    if (playerDead) {
      loseLevel();
    }
  }

  //TODO: THIS IS PROBABLY NESSESARY

  /**
   * Check if the number of treasures remaining is correct in both player and domain.
   */
  private void checkIfValidTreasureCounts() {
    int realTreasureCount = 0;
	for (int y=0; y<gameState.length; ++y) {
		for (int x=0; x<gameState[y].length; ++x) {
			Tile t = gameState[y][x];
			if (t.name().equals("treasure")) {
				realTreasureCount++;
			}
		}
	}

	if (realTreasureCount != treasuresLeft()) {
		throw new IllegalStateException("Treasures left in domain does not match treasures left in player.");
	}
  }

}
