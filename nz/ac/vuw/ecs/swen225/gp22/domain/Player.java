package nz.ac.vuw.ecs.swen225.gp22.domain;

import java.util.HashSet;
import java.util.Set;

public class Player implements Printable {
    private Point position;
    private Domain domain;
    private int treasureCount;
    private Set<AuthenticationColour> keyWallet;

    /**
     * Creates a player linked to a domain, at position p
     * @param d
     */
    public Player(Domain d) {
        this.position = new Point(0, 0);
        this.domain = d;
        this.keyWallet = new HashSet<AuthenticationColour>();
    }

    /**
     * Sets the position of the player.
     * @param pos
     */
    public void setPosition(Point pos) {
        this.position = pos;
    }

    /**
     * Returns the position of the player (immutable)
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
     * Does the wallet contain this colour
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

    public void nextLevel() {
        domain.nextLevel();
    }
}
