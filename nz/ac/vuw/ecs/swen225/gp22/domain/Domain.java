package nz.ac.vuw.ecs.swen225.gp22.domain;

import java.util.Arrays;

public class Domain {
    private Tile[][] gameState;
    private Player   player;
    private int      requiredTreasureCount;

    public Domain(Tile[][] gameState) {
        this.player = new Player(this);
        this.gameState = gameState;
        countTreasures();
    }

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
     * Returns the number of treasures on the map.
     * @return
     */
    public int requiredTreasureCount() {
        return requiredTreasureCount;
    }

    /**
     * Sets the player position.
     * @param pos
     */
    public void setPlayerPosition(Point pos) {
        this.player.setPosition(pos);
    }

    /**
     * Boolean for if player is on
     * @param direction
     */
    public boolean playerOn(Point pos) {
        return pos.equals(player.getPosition());
    }

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
     * TODO: Hook up to davids stuff.
     */
    public void nextLevel() {
        
    }

    /**
     * TODO: Fix interface of tile.
     * @return
     */
    public Tile[][] getInnerState() {
        return Arrays.stream(gameState).map(Tile[]::clone).toArray(Tile[][]::new);
    }
    
    public static void main(String[] args) {
        Domain d = new DomainBuilder()
        .make();

        System.out.println(d);
    }
}
