package nz.ac.vuw.ecs.swen225.gp22.domain;

import java.util.Arrays;


public class Domain {
    private Tile[][] gameState;
    private Player   player;
    private int      requiredTreasureCount;

    /**
     * Raw constructor.
     * Use of the DomainBuilder is higly advised.
     * @param gameState
     */
    public Domain(Tile[][] gameState) {
        this.player = new Player(this);
        this.gameState = gameState;
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
     * Sets the player position.
     * Used within the domain builder.
     * @param pos The desired position of the player.
     */
    public void setPlayerPosition(Point pos) {
        if (!withinDomain(pos)) { 
            throw new IllegalArgumentException("Cannot set player position in domain out of bounds."); 
        }
        
        this.player.setPosition(pos);
    }

    /**
     * Move player in a given direction.
     * TODO: Integrate with David's key calls.
     * @param direction Direction enum (UP, LEFT, RIGHT, DOWN)
     */
    public void movePlayer(Direction direction) {
        Point pos = player.getPosition();
        pos = pos.translate(direction.dr, direction.dc);
        
        // If this doesn't move the player out of the domain.
        if (withinDomain(pos)) {
            // Interact with the tile.
            Tile target = gameState[(int)pos.row()][(int)pos.col()];
            target.acceptPlayer(player);

            // Then move it in, iff it's not a wall.
            if (!target.isWall()) {
                player.setPosition(pos);
            }
        }
    }

    /**
     * For use in testing. Similar to SWEN221 chess toString.
     */
    public String toString() {
        String board = "";
        for (int y=0; y<gameState.length; ++y){
            for (int x=0; x<gameState[y].length; ++x) {
                if (playerOn(new Point(y, x))) {
                    board+="|"+player.toString();    
                } else {
                    board+="|"+gameState[y][x].toString();
                }
            }
            board+="|\n";
        }
        return board;
    }

    /**
     * This function will be called when the level is completed.
     * Use observer pattern.
     * TODO: Hook up to David Lindsay's stuff.
     */
    public void nextLevel() {
        
    }

    /**
     * Returns a 2D clone of the internal view of the domain.
     * TODO: Use this Georgia.
     * TODO: Fix interface of tile.
     */
    public Tile[][] getInnerState() {
        return Arrays.stream(gameState).map(Tile[]::clone).toArray(Tile[][]::new);
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
        return pos.row() >= 0 
            && pos.row() < gameState.length
            && pos.col() >= 0
            && pos.col() < gameState[0].length;
    }

    /**
     * Counts the number of treasures to finish the level.
     * Should only be called within the constructor.
     */
    private void countTreasures() {
        for (int y=0; y<gameState.length; ++y)  {
            for (int x=0; x<gameState[y].length; ++x) {
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
}
