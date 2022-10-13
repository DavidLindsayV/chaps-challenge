package nz.ac.vuw.ecs.swen225.gp22.domain;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents the player on the game.
 * 
 * The player keeps track of - How many treasures it has. - What keys it has on
 * it's wallet. - It's current position
 * 
 * @author Brandon Ru 300562436
 */
public class Player implements Printable {
    private Point position;
    private Domain domain;
    private int treasureCount;
    private Map<AuthenticationColour, Integer> keyWallet;
    private Map<AuthenticationColour, Integer> keyHistory;
    private int totalKeysCollected;

    /**
     * Creates a player linked to a domain, at position (0, 0)
     * 
     * @param d Domain the player is linked to.
     */
    public Player(Domain d) {
        this.position = new Point(0, 0);
        this.domain = d;
        this.keyWallet = new HashMap<AuthenticationColour, Integer>();
        this.keyHistory = new HashMap<AuthenticationColour, Integer>();
        this.totalKeysCollected = 0;
        this.keyHistory = new HashMap<AuthenticationColour, Integer>();
    }

    /**
     * Sets the position of the player.
     * 
     * @param pos Position to move player.
     */
    public void setPosition(Point pos) {
        this.position = pos;
    }

    /**
     * Returns the current position of the player.
     * 
     * @return
     */
    public Point getPosition() {
        return this.position;
    }

    /**
     * Returns the current graphical position of the player.
     * 
     * @return
     */
    public Point getGraphicalPosition() {
        return this.position.translate(Domain.GRAPHICAL_PADDING, Domain.GRAPHICAL_PADDING);
    }

    /**
     * Returns the current key wallet
     * 
     * @param key
     */
    public Map<AuthenticationColour, Integer> getKeysCollected() {
        return keyWallet;
    }

    /**
     * Adds a key to the wallet
     * 
     * @param key The colour of the key.
     */
    public void addKey(AuthenticationColour key) {
        if (!keyWallet.containsKey(key)) {
            keyWallet.put(key, 0);
            keyHistory.put(key, 0);
        }
        keyWallet.put(key, keyWallet.get(key) + 1);
        keyHistory.put(key, keyHistory.get(key) + 1);
        totalKeysCollected++;
    }

    /**
     * Removes a key to the wallet
     * 
     * @param key The colour of the key.
     */
    public void removeKey(AuthenticationColour key) {
        if (keyWallet.containsKey(key)) {
            keyWallet.put(key, keyWallet.get(key) - 1);
            if (keyWallet.get(key) <= 0) {
                keyWallet.remove(key);
            }
        }
    }

    /**
     * Does the wallet contain this colour key? If so: return TRUE Else: return
     * False
     * 
     * This handles multiple keys, as if there are no keys, removeKey removes the
     * entry.
     * 
     * @param colour The colour of the lock.
     * @return The privileges of the user.
     */
    public boolean hasKey(AuthenticationColour colour) {
        return keyWallet.containsKey(colour);
    }

    /**
     * Picks up a treasure.
     */
    public void pickUpTreasure() {
        treasureCount++;
    }

    /**
     * Returns the number of treasures collected
     */
    public int getTreasureCount() {
        return treasureCount;
    }

    /**
     * Get total keys collected over time. <<<<<<< HEAD
     * 
     * @return =======
     * @return Keys collected >>>>>>> origin/main
     */
    public int getTotalKeysCollected() {
        return totalKeysCollected;
    }

    /**
     * Get key history
     * 
     * @return Keys collected but their specifics
     */
    public Map<AuthenticationColour, Integer> getTotalKeyHistory() {
        return keyHistory;
    }

    /**
     * Checks if the player has all treasures.
     * 
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
