package nz.ac.vuw.ecs.swen225.gp22.domain;

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
        return false;
    }

    @Override
    public void performAction(Player p, FreeTile tile) {
        // Check for having key
        if (p.hasKey(this.colour)) {
            tile.changeState(new EmptyState());
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
}
