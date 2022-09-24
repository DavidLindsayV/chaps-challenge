package nz.ac.vuw.ecs.swen225.gp22.domain;

/**
 * The direction in which a player can move.
 * @param dr
 * @param dc
 */
public enum Direction {
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
