package nz.ac.vuw.ecs.swen225.gp22.domain;

// TODO: Make this work (when you pass over it)
public class InformationState implements FreeTileState {
    @Override
    public boolean isWall() {
        return false;
    }

    @Override
    public void performAction(Player p, FreeTile tile) {
        // Show the information tool tip.
        return;        
    }

    @Override
    public String name() {
        return "info";
    }

    @Override
    public String toString() {
        return "i";
    }
}
