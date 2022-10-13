package nz.ac.vuw.ecs.swen225.gp22.domain;

import nz.ac.vuw.ecs.swen225.gp22.renderer.Sounds.SoundEffects;

/**
 * Represents a locked door.
 * The locked door is a wall, but becomes non-wall iff
 * The player holds the correct COLOUR key.
 * 
 * @author Brandon Ru 300562436
 *  
 */
public class LockedDoorState implements FreeTileState {
    /**
     * The color of key (required to open this lock)
     */
    AuthenticationColour colour;

    /**
     * Creates a key state with a desired color.
     * 
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
            tile.changeState(EmptyState.of());
            p.removeKey(this.colour);
            SoundEffects.playSound("Door");
        }
    }

    @Override
    public String name() {
        return "door";
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
