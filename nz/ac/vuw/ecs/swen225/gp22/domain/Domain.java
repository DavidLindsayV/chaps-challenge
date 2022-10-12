package nz.ac.vuw.ecs.swen225.gp22.domain;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import nz.ac.vuw.ecs.swen225.gp22.app.UserListener;

public class Domain {

  private Tile[][] gameState;
  private Player player;
  private List<Enemy> enemies;
  private int requiredTreasureCount;
  private boolean playing;
  
  public static final int GRAPHICAL_PADDING = 1; // Viewport padding.
  public static final String TOOLTIP_STRING = "play the game.";

  /**
   * Raw constructor.
   * Use of the DomainBuilder is highly advised.
   * @param gameState
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
   * Adam: Use this for getting player position.
   * @param pos The position of the player.
   */
  public Point getPlayerPosition() {
    return player.getPosition();
  }

  /**
   * Get the player position
   * Adam: Use this for getting player position.
   * @param pos The position of the player.
   */
  public Point getPlayerGraphicalPosition() {
    return player.getGraphicalPosition();
  }

  /**
   * Get the enemies
   * @param direction
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

    // Special case to check if the player is exiting a tool tip
    Tile initialTile = gameState[(int) pos.row()][(int) pos.col()];
    if (initialTile.name().equals("info")) { Domain.hideToolTip(); }

    pos = pos.translate(direction.dr, direction.dc);
    // If this doesn't move the player out of the domain.
    if (withinDomain(pos)) {
      // Interact with the tile.
      Tile target = gameState[(int) pos.row()][(int) pos.col()];
      target.acceptPlayer(player);
      // Then move it in, iff it's not a wall.
      if (!target.isWall()) {
        player.setPosition(pos);
      }
    }

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
    System.out.println("PlayingSound");
    this.playing = false;
    UserListener.loseLevel();
  }

  /**
   * This function will be called when the player steps on the info tile.
   */
  public static void showToolTip() {
    // GUI.showToolTip(TOOLTIP_STRING);
    System.out.println("showing tooltip");
  }

  public static void hideToolTip() {
    // GUI.hideToolTip();
    System.out.println("hide tool tip");
  }

  /**
   * Returns a 2D clone of the internal view of the domain.
   * Use this Georgia.
   */
  public Tile[][] getInnerState() {
    return Arrays.stream(gameState).map(Tile[]::clone).toArray(Tile[][]::new);
  }

  /**
   * Returns a padded 2D clone of the internal view of the domain.
   * Use this Adam.
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
   */
  private void checkIfPlayerKilledByEnemies() {
    boolean playerDead = enemies
      .stream()
      .anyMatch(e -> e.collidesWith(player.getPosition()));
    if (playerDead) {
      loseLevel();
    }
  }
}
