package nz.ac.vuw.ecs.swen225.gp22.domain;

/**
 * Represents the key object on the board.
 * The key is a specific colour, and will be added to the players wallet
 * If they step on it.
 * It is NOT a wall.
 */
public class KeyState implements FreeTileState {
    /**
     * The colour of key (opens corresponding lock)
     */
    AuthenticationColour colour;

    /**
     * Creates a key state with a desired colour.
     * @param c
     */
    public KeyState(AuthenticationColour c) {
        this.colour = c;
    }

    @Override
    public boolean isWall() {
        return false;
    }

    @Override
    public void performAction(Player p, FreeTile tile) {
        // Pick up key for the player.
        p.addKey(this.colour);

        // Switch tile state to empty state.
        tile.changeState(new EmptyState());
    }

    @Override
    public String name() {
        return "key";
    }

    @Override 
    public String toString() {
        return "k";
    }

    @Override
    public String colour() {
        return this.colour.name();
    }
    
}
