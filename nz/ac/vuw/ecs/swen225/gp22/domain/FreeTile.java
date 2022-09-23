package nz.ac.vuw.ecs.swen225.gp22.domain;

/**
 * A Free tile is a tile that can store something.
 * That 'something', is referred to as it's 'state'.
 * List of possible states:
 *      - Empty
 *      - Key
 *      -
 */
public class FreeTile implements Tile {
    private FreeTileState state;

    /**
     * Can only use factory methods.
     */
    private FreeTile(FreeTileState state) {
        this.state = state;
    }

    @Override
    public boolean isWall() {
        return state.isWall();
    }

    @Override
    public void acceptPlayer(Player p) {
        state.performAction(p, this);
    }

    public void changeState(FreeTileState s) {
        this.state = s;
    }

    /**
     * Factory method for empty free tile.
     * @return Empty free tile.
     */
    public static FreeTile empty() {
        return new FreeTile(new EmptyState());
    }

    @Override
    public String name() {
        return state.name();
    }

    @Override
    public String toString() {
        return state.toString();
    }
}
