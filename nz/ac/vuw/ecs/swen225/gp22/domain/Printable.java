package nz.ac.vuw.ecs.swen225.gp22.domain;

/**
 * Inherit this so it can be printed/saved.
 */
public interface Printable {
    /**
     * The English name of the tile.
     * @return Name
     */
    public String name();

    /**
     * A one character representation of the tile.
     * @return Character (as string)
     */
    public String toString();
}
