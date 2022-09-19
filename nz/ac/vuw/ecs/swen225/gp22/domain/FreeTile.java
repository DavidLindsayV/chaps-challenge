package nz.ac.vuw.ecs.swen225.gp22.domain;

public class FreeTile implements Tile {
    private FreeTileState state;

    @Override
    public boolean isWall() {
        return state.isWall();
    }

    @Override
    public void acceptPlayer(Player p) {
        state.performAction(p, this);
    }
    
}
