package nz.ac.vuw.ecs.swen225.gp22.domain;

/**
 * The quintessential empty tile, which does nothing but is still a state of 
 * a free tile.
 * 
 * If the existence of this class makes no sense, think of this as the empty set
 * object (in mathematics), and the other classes as a set with 1 object exactly.
 * 
 * The empty set is still a state.
 */
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
