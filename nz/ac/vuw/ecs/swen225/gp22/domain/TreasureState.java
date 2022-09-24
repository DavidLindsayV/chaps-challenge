package nz.ac.vuw.ecs.swen225.gp22.domain;

/**
 * Represents treasure.
 * Can pass through.
 * No special traits, just a chip.
 */
public class TreasureState implements FreeTileState {
    @Override
    public boolean isWall() {
        return false;
    }

    @Override
    public void performAction(Player p, FreeTile tile) {
        p.pickUpTreasure();
        tile.changeState(new EmptyState());
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
