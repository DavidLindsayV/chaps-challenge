package nz.ac.vuw.ecs.swen225.gp22.domain;

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

    /**
     * Factory method for empty free tile.
     * @return Empty free tile.
     */
    public static FreeTile empty() {
        return new FreeTile(new EmptyState());
    }
}
