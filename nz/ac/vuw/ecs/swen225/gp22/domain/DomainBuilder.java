package nz.ac.vuw.ecs.swen225.gp22.domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Fluent Builder API for creating a Domain object.
 * Initial height and width does not need to be provided
 * It will AUTODETECT this.
 * Remember to call .make() to crystalise the object.
 *
 * Every domain must have
 * - Exactly one player.
 * - At least one exit.
 *
 * Example usage:
 *
 * DomainBuilder db = new DomainBuilder();
 * Domain d = db
 * .wall(1, 2)
 * .wall(1, 3)
 * .wall(1, 4)
 * .wall(2, 4)
 * .player(0, 1)
 * .exit(5, 5)
 * .make()
 *
 * toString() -->
 *
 * |_|P|_|_|_|_|
 * |_|_|#|#|#|_|
 * |_|_|_|_|#|_|
 * |_|_|_|_|_|_|
 * |_|_|_|_|_|E|
 */
public class DomainBuilder {
    /**
     * ----------------------INTERNAL VARIABLES (IGNORE)-----------------------
     */
    private static final int MAX_WIDTH = 1000;
    private static final int MAX_HEIGHT = 1000;

    private Tile[][] domainContent;
    private List<Enemy> domainEnemies;
    private Integer domainHeight;
    private Integer domainWidth;
    private Point domainPlayerPosition;
    private Point domainExitLocation;

    public DomainBuilder() {
        System.out.println("BREAKPOINT: Domainbuilder initiated.");
        this.reset();
    }

    /**
     * ----------------------PUBLIC METHODS API-------------------------------
     */

    /**
     * Resets the state of the builder
     * All tiles are free tiles by default.
     */
    public void reset() {
        domainExitLocation = null;
        domainPlayerPosition = null;
        domainContent = new Tile[MAX_WIDTH][MAX_HEIGHT];
        domainEnemies = new ArrayList<Enemy>();
        domainHeight = -1;
        domainWidth = -1;

        for (Tile[] domainContentRow : domainContent) {
            Arrays.fill(domainContentRow, FreeTile.empty());
        }
    }

    /**
     * Creates an player given row and column.
     *
     * @param row Row of the tile.
     * @param col Column of the tile.
     * @return Domain builder object.
     */
    public DomainBuilder player(int row, int col) {
        checkWithinAbsoluteLimits(row, col);
        if (domainPlayerPosition != null) {
            throw new IllegalStateException("You cannot have more than one player.");
        }
        if (!domainContent[row][col].name().equals("empty")) {
            throw new IllegalStateException("You cannot spawn on a occupied tile.");
        }
        domainPlayerPosition = new Point(row, col);
        detectBoundaries(row, col);
        return this;
    }

    /**
     * Creates an enemy given a row and column, and it's path, set
     *
     * @param row
     * @param col
     * @return
     */
    public DomainBuilder enemy(int row, int col, List<Point> path) {
        if (path == null) {
            throw new IllegalArgumentException("Null path disallowed.");
        }
        if (path.isEmpty()) {
            throw new IllegalArgumentException("Empty path disallowed.");
        }
        if (path.stream().anyMatch(p -> p == null)) {
            throw new IllegalArgumentException("Cannot have null points in path.");
        }

        checkWithinAbsoluteLimits(row, col);
        checkNoPlayerHere(row, col);
        path.stream().forEach(p -> {
            checkWithinAbsoluteLimits(p.row(), p.col());
            checkWithinRelativeLimits(p.row(), p.col());
        });

        domainEnemies.add(new Enemy(path));
        detectBoundaries(row, col);
        return this;
    }

    /**
     * Creates an enemy given a row and column, and it's path, set
     * 
     * @param row
     * @param col
     * @return
     */
    public DomainBuilder enemy(Enemy e) {
        int row = e.getPosition().row();
        int col = e.getPosition().col();
        checkWithinAbsoluteLimits(row, col);
        checkNoPlayerHere(row, col);
        domainEnemies.add(e);
        detectBoundaries(row, col);
        return this;
    }

    /**
     * Creates an empty tile at given row and column.
     *
     * @param row Row of the tile.
     * @param col Column of the tile.
     * @return Domain builder object.
     */
    public DomainBuilder empty(int row, int col) {
        checkWithinAbsoluteLimits(row, col);
        checkNoPlayerHere(row, col);
        domainContent[row][col] = FreeTile.empty();
        detectBoundaries(row, col);
        return this;
    }

    /**
     * Creates a wall tile at the given location.
     * !! NOTE, this really shouldn't be used often.
     * As all default tiles are rows.
     *
     * @param row Row of the wall tile.
     * @param col Column of the wall tile.
     * @return Domain builder object.
     */
    public DomainBuilder wall(int row, int col) {
        checkWithinAbsoluteLimits(row, col);
        checkNoPlayerHere(row, col);
        domainContent[row][col] = WallTile.of();
        detectBoundaries(row, col);
        return this;
    }

    /**
     * Creates a exit tile at the given location.
     *
     * @param row Row of the exit tile.
     * @param col Column of the exit tile.
     * @return Domain builder object.
     */
    public DomainBuilder exit(int row, int col) {
        checkWithinAbsoluteLimits(row, col);
        checkNoPlayerHere(row, col);
        domainContent[row][col] = new ExitTile();
        domainExitLocation = new Point(row, col);
        detectBoundaries(row, col);
        return this;
    }

    /**
     * Creates a treasure tile at the given location.
     *
     * @param row Row of the treasure.
     * @param col Column of the treasure.
     * @return Domain builder object.
     */
    public DomainBuilder treasure(int row, int col) {
        checkWithinAbsoluteLimits(row, col);
        checkNoPlayerHere(row, col);
        domainContent[row][col] = FreeTile.treasure();
        detectBoundaries(row, col);
        return this;
    }

    /**
     * Creates a key tile at the given location.
     *
     * @param row    Row of the key.
     * @param col    Column of the key.
     * @param colour String representation in ALL CAPS of the colour .. PINK e.g
     * @return Domain builder object.
     */
    public DomainBuilder key(int row, int col, String colour) {
        checkWithinAbsoluteLimits(row, col);
        checkNoPlayerHere(row, col);
        domainContent[row][col] = FreeTile.key(
                AuthenticationColour.valueOf(colour));
        detectBoundaries(row, col);
        return this;
    }

    /**
     * Creates a door tile at the given location, with a specific colour.
     *
     * @param row Row of the door.
     * @param col Column of the door.
     * @return Domain builder object.
     */
    public DomainBuilder door(int row, int col, String colour) {
        checkWithinAbsoluteLimits(row, col);
        checkNoPlayerHere(row, col);
        domainContent[row][col] = FreeTile.door(
                AuthenticationColour.valueOf(colour));
        detectBoundaries(row, col);
        return this;
    }

    /**
     * Creates a info tile at the given location.
     *
     * @param row Row of the info.
     * @param col Column of the info.
     * @return Domain builder object.
     */
    public DomainBuilder info(int row, int col) {
        checkWithinAbsoluteLimits(row, col);
        checkNoPlayerHere(row, col);
        domainContent[row][col] = FreeTile.info();
        detectBoundaries(row, col);
        return this;
    }

    /**
     * Creates an exit lock tile at the given location.
     *
     * @param row Row of the lock.
     * @param col Column of the lock.
     * @return Domain builder object.
     */
    public DomainBuilder lock(int row, int col) {
        checkWithinAbsoluteLimits(row, col);
        checkNoPlayerHere(row, col);
        domainContent[row][col] = FreeTile.lock();
        detectBoundaries(row, col);
        return this;
    }

    /**
     * Returns the constructed domain object.
     * with a nested player (that is linked to the domain).
     *
     * @return Built domain object.
     */
    public Domain make() {
        // Check for correct state
        if (domainHeight == -1 || domainWidth == -1) {
            throw new IllegalStateException("You haven't set any tiles.");
        }
        if (domainPlayerPosition == null) {
            throw new IllegalStateException("You haven't set the players position.");
        }

        if (domainExitLocation == null) {
            throw new IllegalStateException("You haven't set an exit position.");
        }

        // Copies over the selected region.
        Tile[][] selectedDomainContent = new Tile[domainHeight][domainWidth];
        for (int y = 0; y < domainHeight; ++y) {
            for (int x = 0; x < domainWidth; ++x) {
                selectedDomainContent[y][x] = domainContent[y][x];
            }
        }

        // Creates the player, linking it to the domain.
        Domain d = new Domain(selectedDomainContent, domainEnemies);
        d.setPlayerPosition(domainPlayerPosition);

        System.out.println("BREAKPOINT: Domainbuilder preconditions passed - domain returned.");
        return d;
    }

    /**
     * -------------------------PRIVATE HELPER FUNCTIONS-----------------------
     */

    /**
     * Auto detects the boundaries of the board.
     * and updates them.
     *
     *
     * @param row
     * @param col
     * @return
     */
    private void detectBoundaries(int row, int col) {
        domainHeight = Math.max(domainHeight, row + 1);
        domainWidth = Math.max(domainWidth, col + 1);
    }

    /**
     * Checks if the (row, col) is within the maximum bounds.
     *
     * @param row
     * @param col
     * @return
     */
    private void checkWithinAbsoluteLimits(int row, int col) {
        if (row < 0) {
            throw new IllegalArgumentException("Row cannot be less than 0.");
        }
        if (col < 0) {
            throw new IllegalArgumentException("Col cannot be less than 0.");
        }
        if (row >= MAX_HEIGHT) {
            throw new IllegalArgumentException("Row cannot be greater than 999.");
        }
        if (col >= MAX_WIDTH) {
            throw new IllegalArgumentException("Col cannot be greater than 999.");
        }
    }

    /**
     * Checks if the (row, col) is within the relative bounds.
     * This is called when paths of enemies move outside of the map.
     *
     * @param row
     * @param col
     * @return
     */
    private void checkWithinRelativeLimits(int row, int col) {
        if (row < 0) {
            throw new IllegalArgumentException("Row cannot be less than 0.");
        }
        if (col < 0) {
            throw new IllegalArgumentException("Col cannot be less than 0.");
        }
        if (row >= domainHeight) {
            throw new IllegalArgumentException("Row cannot be greater than " + domainHeight);
        }
        if (col >= domainWidth) {
            throw new IllegalArgumentException("Col cannot be greater than " + domainWidth);
        }
    }

    /**
     * Ensures there is no player at the position.
     *
     * @param row
     * @param col
     * @return
     */
    private void checkNoPlayerHere(int row, int col) {
        if (domainPlayerPosition != null && domainPlayerPosition.equals(new Point(row, col))) {
            throw new IllegalStateException("You cannot place a tile on top of a player.");
        }
    }
}
