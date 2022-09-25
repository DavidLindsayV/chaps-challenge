package nz.ac.vuw.ecs.swen225.gp22.domain;

/**
 * A common class for retrieving important data about a tile.
 * Inherit this so it can be printed/saved.
 * 
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

    /**
     * The English name of the tile colour.
     * @return Name
     */
    default public String colour() {
        return AuthenticationColour.NULL.name();
    }
}
