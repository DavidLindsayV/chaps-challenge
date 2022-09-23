package nz.ac.vuw.ecs.swen225.gp22.domain;

import java.util.Set;

public class Player {
    private int treasureCount;
    private Set<AuthenticationColour> keyWallet;
    
    /**
     * Adds a key to the wallet 
     * @param key
     */
    public void addKey(AuthenticationColour key) {
        keyWallet.add(key);
    }
}
