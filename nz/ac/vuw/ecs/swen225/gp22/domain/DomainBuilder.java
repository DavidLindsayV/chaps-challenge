package nz.ac.vuw.ecs.swen225.gp22.domain;

import java.util.Arrays;

/**
 * Fluent Builder API for creating a Domain object.
 * Every domain must have
 *  - Exactly one player.
 *  - At least one exit.
 * 
 * Example usage:
 * 
 * DomainBuilder db = new DomainBuilder();
 * Domain d = db
 *  .wall(1, 2)
 *  .wall(1, 3)
 *  .wall(1, 4)
 *  .wall(2, 4)
 *  .player(0, 1)
 *  .exit(5, 5)
 *  .make()
 */
public class DomainBuilder {
    /**
     * ----------------------INTERNAL VARIABLES (IGNORE)-----------------------
     */
    private static final int MAX_WIDTH  = 1000;
    private static final int MAX_HEIGHT = 1000;

    private Tile[][] domainContent;
    private Integer  domainHeight;
    private Integer  domainWidth;
    private Point    domainPlayerPosition;
    private Point    domainExitLocation;

    public DomainBuilder() {
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
        domainExitLocation      = null;
        domainPlayerPosition    = null;
        domainContent           = new Tile[MAX_WIDTH][MAX_HEIGHT];    
        domainHeight            = -1;
        domainWidth             = -1;
        
        for (Tile[] domainContentRow : domainContent) {
            Arrays.fill(domainContentRow, FreeTile.empty());
        }
    }
    
    /**
     * Creates an player given row and column.
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
     * Creates an empty tile at given row and column.
     * @param row Row of the tile.
     * @param col Column of the tile.
     * @return Domain builder object.
     */
    public DomainBuilder empty(int row, int col) {
        checkWithinAbsoluteLimits(row, col);
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
        domainContent[row][col] = FreeTile.treasure();
        detectBoundaries(row, col);
        return this;
    }

    /**
     * Creates a key tile at the given location.
     * 
     * @param row Row of the key.
     * @param col Column of the key.
     * @return Domain builder object.
     */
    public DomainBuilder key(int row, int col, String colour) {
        checkWithinAbsoluteLimits(row, col);
        domainContent[row][col] = FreeTile.key(
            AuthenticationColour.valueOf(colour)
        );
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
    public DomainBuilder door(int row, int col, String colour) {
        checkWithinAbsoluteLimits(row, col);
        domainContent[row][col] = FreeTile.door(
            AuthenticationColour.valueOf(colour)
        );
        detectBoundaries(row, col);
        return this;
    }

    /**
     * Creates a info tile at the given location.
     * 
     * @param row Row of the treasure.
     * @param col Column of the treasure.
     * @return Domain builder object.
     */
    public DomainBuilder info(int row, int col) {
        checkWithinAbsoluteLimits(row, col);
        domainContent[row][col] = FreeTile.info();
        detectBoundaries(row, col);
        return this;
    }

    /**
     * Creates a lock tile at the given location.
     * 
     * @param row Row of the lock.
     * @param col Column of the treasure.
     * @return Domain builder object.
     */
    public DomainBuilder lock(int row, int col) {
        checkWithinAbsoluteLimits(row, col);
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
        for (int y=0; y<domainHeight; ++y) {
            for (int x=0; x<domainWidth; ++x) {
                selectedDomainContent[y][x] = domainContent[y][x];
            }
        }

        // Creates the player, linking it to the domain.
        Domain d = new Domain(selectedDomainContent);
        d.setPlayerPosition(domainPlayerPosition);
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
        domainWidth  = Math.max(domainWidth, col + 1);
    }

    /**
     * Checks if the (row, col) is within the maximum bounds.
     * @param row
     * @param col
     * @return 
     */
    private void checkWithinAbsoluteLimits(int row, int col) {
        if (row < 0) { throw new IllegalArgumentException("Row cannot be less than 0."); }
        if (col < 0) { throw new IllegalArgumentException("Col cannot be less than 0."); }
        if (row >= MAX_HEIGHT) { throw new IllegalArgumentException("Row cannot be greater than 999."); }
        if (col >= MAX_WIDTH) { throw new IllegalArgumentException("Col cannot be greater than 999."); }
    }
}
