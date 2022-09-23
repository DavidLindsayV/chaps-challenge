package nz.ac.vuw.ecs.swen225.gp22.domain;

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
}
