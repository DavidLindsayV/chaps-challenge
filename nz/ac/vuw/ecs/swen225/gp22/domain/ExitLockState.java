package nz.ac.vuw.ecs.swen225.gp22.domain;

/**
 * Represents an exit lock, which acts as a wall.
 * If the player carrys ALL TREASURES,
 * then it becomes an empty tile.
 * 
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
