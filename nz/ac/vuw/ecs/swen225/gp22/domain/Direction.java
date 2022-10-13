package nz.ac.vuw.ecs.swen225.gp22.domain;

/**
 * Direction represents the direction a player can move.
 * 
 * @author Brandon Ru 300562436
 */
public enum Direction {
    UP(-1, 0),
    DOWN(1, 0),
    LEFT(0, -1),
    RIGHT(0, 1),
    NONE(0, 0);
    
    public final int dr;
    public final int dc;
    
    /**
     * Direction constructor.
     * 
     * @param dr Change in row.
     * @param dc Change in column.
     */
    Direction(int dr, int dc) {
        this.dr = dr;
        this.dc = dc;
    }
}
