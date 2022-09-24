package nz.ac.vuw.ecs.swen225.gp22.domain;

public class Domain {
    public static enum Direction {
        UP(-1, 0),
        DOWN(1, 0),
        LEFT(0, -1),
        RIGHT(0, 1),
        NONE(0, 0);

        public final int dr;
        public final int dc;
        Direction(int dr, int dc) {
            this.dr = dr;
            this.dc = dc;
        }
    }

    private Tile[][] gameState;
    private Player   player;
    private int      requiredTreasureCount;

    public Domain(Tile[][] gameState) {
        this.player = new Player(this);
        this.gameState = gameState;
        countTreasures();
    }

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

    public void movePlayer(Domain.Direction direction) {
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

    public void nextLevel() {
        
    }

    public static void main(String[] args) {
        Domain d = new DomainBuilder()
        .make();

        System.out.println(d);
    }
}
