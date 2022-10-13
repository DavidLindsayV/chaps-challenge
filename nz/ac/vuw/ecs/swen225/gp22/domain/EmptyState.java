package nz.ac.vuw.ecs.swen225.gp22.domain;

/**
 * The empty tile, which does nothing.
 * 
 * If the existence of this class makes no sense, think of this as the empty set
 * object (in mathematics), and the other classes as a set with 1 object exactly.
 * 
 * The empty set is still a state.
 * 
 * Implemented with a SINGLETON, to save memory.
 * 
 * @author Brandon Ru 300562436
 */
public class EmptyState implements FreeTileState {
    private static final EmptyState singleton = new EmptyState();

    /**
     * If you want to use empty state, call EmptyState.of()
     */
    public static EmptyState of() {
        return singleton;
    }

    /**
     * Private constructor - prevent new EmptyState().
     */
    private EmptyState() {

    }

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
