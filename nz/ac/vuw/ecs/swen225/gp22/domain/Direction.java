package nz.ac.vuw.ecs.swen225.gp22.domain;

/**
 * The direction in which a player can move.
 * Note: dr and dc are with respect to (0, 0) in the TOP LEFT - not cartesian.
 * @param dr Change in row.
 * @param dc Change in column.
 */
public enum Direction {
    UP(-1, 0),
    DOWN(1, 0),
    LEFT(0, -1),
    RIGHT(0, 1),
    NONE(0, 0);

    // Access the change in row and column when moving.
    // by calling .dr or .dc
    // E.g Direction.UP.dr;
    public final int dr;
    public final int dc;

    /**
     * Base Constructor
     * @param dr Change in row
     * @param dc Change in column
     */
    Direction(int dr, int dc) {
        this.dr = dr;
        this.dc = dc;
    }
}
