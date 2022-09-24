package nz.ac.vuw.ecs.swen225.gp22.domain;

public class ExitLockState implements FreeTileState {
    @Override
    public boolean isWall() {
        return true;
    }

    @Override
    public void performAction(Player p, FreeTile tile) {
        // If the player has all the treasures, let him pass.
        if (p.hasAllTreasures()) {
            tile.changeState(new EmptyState());
        }
    }

    @Override
    public String name() {
        return "empty";
    }

    @Override
    public String toString() {
        return "_";
    }
}
