package nz.ac.vuw.ecs.swen225.gp22.domain;

import java.util.HashSet;
import java.util.Set;

/**
 * Represents the player on the game.
 * The player keeps track of
 *  - How many treasures it has.
 *  - What keys it has on it's wallet.
 *  - It's current position
 */
public class Player implements Printable {
    private Point position;
    private Domain domain;
    private int treasureCount;
    private Set<AuthenticationColour> keyWallet;

    /**
     * Creates a player linked to a domain, at position (0, 0)
     * @param d
     */
    public Player(Domain d) {
        this.position = new Point(0, 0);
        this.domain = d;
        this.keyWallet = new HashSet<AuthenticationColour>();
    }

    /**
     * Sets the position of the player.
     * @param pos Position to move player.
     */
    public void setPosition(Point pos) {
        this.position = pos;
    }

    /**
     * Returns the position of the player (immutable, due to record)
     */
    public Point getPosition() {
        return this.position;
    }

    /**
     * Adds a key to the wallet 
     * @param key The colour of the key.
     */
    public void addKey(AuthenticationColour key) {
        keyWallet.add(key);
    }

    /**
     * Does the wallet contain this colour key? 
     * If so: return TRUE
     * Else:  return False
     * 
     * @param colour The colour of the lock.
     * @return The privileges of the user.
     */
    public boolean hasKey(AuthenticationColour colour) {
        return keyWallet.contains(colour);
    }

    /**
     * Picks up a treasure.
     */
    public void pickUpTreasure() {
        treasureCount++;
    }

    /**
     * Checks if the player has all treasures.
     * @return Boolean
     */
    public boolean hasAllTreasures() {
        return treasureCount == domain.requiredTreasureCount();
    }

    @Override
    public String name() {
        return "player";
    }

    @Override
    public String toString() {
        return "P";
    }

    /**
     * Called by exit tile, which allows the player to move to next lvl.
     */
    public void nextLevel() {
        domain.nextLevel();
    }
}
