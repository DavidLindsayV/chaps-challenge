package nz.ac.vuw.ecs.swen225.gp22.domain;

import java.util.Set;

public class Player {
    private Domain domain;
    private int treasureCount;
    private Set<AuthenticationColour> keyWallet;

    /**
     * Creates a player linked to a domain.
     * @param d
     */
    public Player(Domain d) {
        this.domain = d;
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

    public boolean hasAllTreasures() {
        return treasureCount == domain.requiredTreasureCount();
    }
}
