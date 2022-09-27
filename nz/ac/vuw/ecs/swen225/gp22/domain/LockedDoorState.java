package nz.ac.vuw.ecs.swen225.gp22.domain;

/**
 * Represents a locked door.
 * The locked door is a wall, but is dismantled iff
 *  : the player carrys the correct COLOUR key.
 */
public class LockedDoorState implements FreeTileState {
    /**
     * The colour of key (required to open this lock)
     */
    AuthenticationColour colour;

    /**
     * Creates a key state with a desired colour.
     * @param c
     */
    public LockedDoorState(AuthenticationColour c) {
        this.colour = c;
    }

    @Override
    public boolean isWall() {
        return true;
    }

    @Override
    public void performAction(Player p, FreeTile tile) {
        // If the correct key is carried - then you move pass.
        if (p.hasKey(this.colour)) {
            tile.changeState(new EmptyState());
            // remove key
        }
    }

    @Override
    public String name() {
        return "locked_door";
    }

    @Override
    public String toString() {
        return "@";
    }

    @Override
    public String colour() {
        return this.colour.name();
    }
}
