package nz.ac.vuw.ecs.swen225.gp22.domain;

/**
 * A common class for retrieving important data about a tile.
 * Inherit this so it can be printed/saved.
 * 
 * @author Brandon Ru 300562436
 */
public interface Printable {
    /**
     * The English name of the tile.
     * 
     * @return Name of the tile.
     */
    public String name();

    /**
     * A one character representation of the tile.
     * 
     * @return Length of one string.
     */
    public String toString();

    /**
     * The English name of the tile color.
     * 
     * @return Name
     */
    default public String colour() {
        return AuthenticationColour.NULL.name();
    }
}
