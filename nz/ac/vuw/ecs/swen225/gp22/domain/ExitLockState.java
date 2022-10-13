package nz.ac.vuw.ecs.swen225.gp22.domain;

import nz.ac.vuw.ecs.swen225.gp22.renderer.Sounds.SoundEffects;

/**
 * Represents an exit lock, which acts as a wall.
 * If the player carries ALL TREASURES,
 * then it becomes an empty tile.
 * 
 * @author Brandon Ru 300562436
 */
public class ExitLockState implements FreeTileState {
    @Override
    public boolean isWall() {
        return true;
    }

    @Override
    public void performAction(Player p, FreeTile tile) {
        // If the player has all the treasures, let him pass.
        if (p.hasAllTreasures()) {
            tile.changeState(EmptyState.of());
            SoundEffects.playSound("Lock");
        }
    }

    @Override
    public String name() {
        return "exitLock";
    }

    @Override
    public String toString() {
        return "L";
    }
}
