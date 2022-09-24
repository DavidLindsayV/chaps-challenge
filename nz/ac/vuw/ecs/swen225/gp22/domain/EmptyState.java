package nz.ac.vuw.ecs.swen225.gp22.domain;

public class EmptyState implements FreeTileState {
    @Override
    public boolean isWall() {
        return false;
    }

    @Override
    public void performAction(Player p, FreeTile tile) {
        return;        
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
