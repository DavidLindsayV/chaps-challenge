package nz.ac.vuw.ecs.swen225.gp22.domain;

public class WallTile implements Tile {
    @Override
    public boolean isWall() {
        return true;
    }

    @Override
    public void acceptPlayer(Player player) {
        return;
    }
}
