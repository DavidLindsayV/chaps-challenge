package nz.ac.vuw.ecs.swen225.gp22.domain;

import nz.ac.vuw.ecs.swen225.gp22.renderer.Sounds.SoundEffects;

/**
 * TreasureState represents treasure.
 * Player can pass through.
 * When the player passes through, it acquires +1 treasures.
 * The parent tile becomes empty. 
 * 
 * @author Brandon Ru 300562436
 *
 */
public class TreasureState implements FreeTileState {
    @Override
    public boolean isWall() {
        return false;
    }

    @Override
    public void performAction(Player p, FreeTile tile) {
        p.pickUpTreasure();
        SoundEffects.playSound("Treasure");
        tile.changeState(EmptyState.of());
    }

    @Override
    public String name() {
        return "treasure";
    }

    @Override
    public String toString() {
        return "*";
    }
}
